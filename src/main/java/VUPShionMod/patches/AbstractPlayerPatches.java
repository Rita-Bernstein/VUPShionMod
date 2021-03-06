package VUPShionMod.patches;

import VUPShionMod.cards.anastasia.EnergyReserve;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Skeleton;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
        public static SpireField<List<AbstractFinFunnel>> finFunnelList = new SpireField<>(ArrayList::new);
        public static SpireField<AbstractFinFunnel> activatedFinFunnel = new SpireField<>(() -> null);
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnRelics"
    )
    public static class PatchApplyStartOfTurnRelics {
        public static void Postfix(AbstractPlayer player) {
            for (AbstractFinFunnel funnel : AddFields.finFunnelList.get(player)) {
                funnel.atTurnStart();
            }
            EnergyPanelPatches.energyUsedThisTurn = 0;
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
                    for (AbstractFinFunnel funnel : AddFields.finFunnelList.get(player)) {
                        funnel.updatePosition(sk);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (AbstractFinFunnel funnel : AddFields.finFunnelList.get(player)) {
                funnel.update();
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
                for (AbstractFinFunnel funnel : AddFields.finFunnelList.get(player)) {
                    funnel.render(sb);
                }
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
                if (c instanceof EnergyReserve && p.currentHealth >= c.magicNumber) {
                    AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(c));
                    if (!(AbstractDungeon.player.drawPile.group.size() == 0 && AbstractDungeon.player.discardPile.group.size() == 0)) {
                        boolean hasOther = false;
                        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                            if (!(card instanceof EnergyReserve))
                                hasOther = true;
                        }
                        for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                            if (!(card instanceof EnergyReserve))
                                hasOther = true;
                        }
                        if (hasOther)
                            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
                    }

                } else {
                    c.exhaustOnUseOnce = true;
                    c.isInAutoplay = true;
                    AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.miscRng);
                    c.applyPowers();
                    p.useCard(c, m, 0);
                    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
                    AbstractDungeon.actionManager.cardsPlayedThisTurn.add(c);
                }
            }
        }
    }
}
