package VUPShionMod.patches;

import VUPShionMod.actions.TurnTriggerAllFinFunnelAction;
import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.character.Shion;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.helpers.ChargeHelper;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Skeleton;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;
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
        public static SpireField<ChargeHelper> chargeHelper = new SpireField<>(() -> new ChargeHelper());
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnRelics"
    )
    public static class PatchApplyStartOfTurnRelics {
        public static void Postfix(AbstractPlayer player) {
//            for (AbstractFinFunnel funnel : AddFields.finFunnelList.get(player)) {
//                funnel.atTurnStart();
//            }
            if (AbstractDungeon.player instanceof Shion)
                AbstractDungeon.actionManager.addToBottom(new TurnTriggerAllFinFunnelAction(true));
            EnergyPanelPatches.energyUsedThisTurn = 1;
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
                for (AbstractFinFunnel funnel : AddFields.finFunnelList.get(player)) {
                    funnel.render(sb);
                }

                if (AddFields.chargeHelper.get(player).active) {
                    AddFields.chargeHelper.get(player).render(sb);
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
                        isDone=true;
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

//            for(AbstractCard card : AbstractDungeon.player.hand.group){
//                if(card instanceof AbstractVUPShionCard){
//                    ((AbstractVUPShionCard) card).monsterAfterOnAttack(info, _instance,damageAmount);
//                }
//            }
//
//            for(AbstractCard card : AbstractDungeon.player.discardPile.group){
//                if(card instanceof AbstractVUPShionCard){
//                    ((AbstractVUPShionCard) card).monsterAfterOnAttack(info, _instance,damageAmount);
//                }
//            }
//
//            for(AbstractCard card : AbstractDungeon.player.drawPile.group){
//                if(card instanceof AbstractVUPShionCard){
//                    ((AbstractVUPShionCard) card).monsterAfterOnAttack(info, _instance,damageAmount);
//                }
//            }
//
//            for(AbstractCard card : AbstractDungeon.player.exhaustPile.group){
//                if(card instanceof AbstractVUPShionCard){
//                    ((AbstractVUPShionCard) card).monsterAfterOnAttack(info, _instance,damageAmount);
//                }
//            }


            return SpireReturn.Continue();
        }
    }


//    @SpirePatch(
//            clz = AbstractDungeon.class,
//            method = "getEvent"
//    )
//    public static class NoGhostsPatch {
//        @SpireInsertPatch(rloc = 40)
//        public static SpireReturn<AbstractEvent> Insert(Random rng) {
//            ArrayList<String> tmp = ReflectionHacks.getPrivate(CardCrawlGame.dungeon, AbstractDungeon.class, "tmp");
//            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan) {
//                tmp.removeIf(e ->e.equals("Ghosts"));
//            }
//            return SpireReturn.Continue();
//        }
//    }
}
