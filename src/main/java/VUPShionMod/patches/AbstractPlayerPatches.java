package VUPShionMod.patches;

import VUPShionMod.actions.Shion.TurnTriggerAllFinFunnelAction;
import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.cards.ShionCard.anastasia.AttackOrderGamma;
import VUPShionMod.character.Shion;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.helpers.ChargeHelper;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Skeleton;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AbstractPlayerPatches {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = SpirePatch.CLASS
    )
    public static class AddFields {
        public static SpireField<ChargeHelper> chargeHelper = new SpireField<>(() -> new ChargeHelper());
        public static SpireField<FinFunnelManager> finFunnelManager = new SpireField<>(() -> new FinFunnelManager());
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnRelics"
    )
    public static class PatchApplyStartOfTurnRelics {
        public static void Postfix(AbstractPlayer player) {
            AddFields.finFunnelManager.get(player).atStartOfTurn();
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "update"
    )
    public static class PatchUpdate {
        public static void Postfix(AbstractPlayer player) {
            try {
                Field stateDataField = AbstractCreature.class.getDeclaredField("skeleton");
                stateDataField.setAccessible(true);
                Skeleton sk = ((Skeleton) stateDataField.get(player));
                if (sk != null) {
                    AddFields.finFunnelManager.get(player).update(sk);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (AddFields.chargeHelper.get(player).active) {
                AddFields.chargeHelper.get(player).update();
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "render"
    )
    public static class PatchRender {
        public static void Postfix(AbstractPlayer player, SpriteBatch sb) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                AddFields.finFunnelManager.get(player).render(sb);

                if (AddFields.chargeHelper.get(player).active) {
                    AddFields.chargeHelper.get(player).render(sb);
                }
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "preBattlePrep"
    )
    public static class PatchPreBattlePrep {
        public static void Postfix(AbstractPlayer player) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                AddFields.finFunnelManager.get(player).preBattlePrep();
            }
        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "onVictory"
    )
    public static class PatchOnVictory {
        public static void Postfix(AbstractPlayer player) {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                AddFields.finFunnelManager.get(player).onVictory();
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "draw",
            paramtypez = {
                    int.class
            }
    )
    public static class PatchDraw {
        @SpireInsertPatch(rloc = 13, localvars = {"c"})
        public static void Insert(AbstractPlayer p, int numCards, @ByRef(type = "cards.AbstractCard") Object[] _c) {
            AbstractCard c = (AbstractCard) _c[0];
            if (c.hasTag(CardTagsEnum.LOADED)) {
                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                    @Override
                    public void update() {
                        c.isInAutoplay = true;
                        AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.miscRng);
                        c.applyPowers();
                        p.useCard(c, m, 0);

                        AbstractDungeon.actionManager.cardsPlayedThisTurn.add(c);

                        for (AbstractPower power : AbstractDungeon.player.powers)
                            if (power instanceof AbstractShionPower)
                                ((AbstractShionPower) power).onTriggerLoaded();

                        for (AbstractCard card : p.hand.group) {
                            if (card instanceof AbstractVUPShionCard)
                                ((AbstractVUPShionCard) card).onTriggerLoaded();
                        }

                        for (AbstractCard card : p.discardPile.group) {
                            if (card instanceof AbstractVUPShionCard)
                                ((AbstractVUPShionCard) card).onTriggerLoaded();
                        }

                        for (AbstractCard card : p.drawPile.group) {
                            if (card instanceof AbstractVUPShionCard)
                                ((AbstractVUPShionCard) card).onTriggerLoaded();
                        }
                        isDone = true;
                    }
                });

            }
        }
    }


    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage"
    )
    public static class OnUnblockDamagePatch {
        @SpireInsertPatch(rloc = 61, localvars = {"damageAmount"})
        public static SpireReturn<Void> Insert(AbstractMonster _instance, DamageInfo info, int damageAmount) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof AbstractShionPower) {
                    ((AbstractShionPower) p).monsterAfterOnAttack(info, _instance, damageAmount);
                }
            }

            return SpireReturn.Continue();
        }
    }


}
