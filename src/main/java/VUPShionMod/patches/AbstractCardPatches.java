package VUPShionMod.patches;

import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.powers.Common.FreeCardPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class AbstractCardPatches {
    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class PatchMakeStatEquivalentCopy {
        @SpireInsertPatch(rloc = 1, localvars = {"card"})
        public static void Insert(AbstractCard c, @ByRef(type = "cards.AbstractCard") Object[] _card) {
            AbstractCard card = (AbstractCard) _card[0];
            card.tags.clear();
            card.tags.addAll(c.tags);
            card.exhaust = c.exhaust;
            card.exhaustOnUseOnce = c.exhaustOnUseOnce;
            card.rawDescription = c.rawDescription;
            card.type = c.type;
            try {
                Field field = AbstractCard.class.getDeclaredField("isMultiDamage");
                field.setAccessible(true);
                field.set(card, field.get(c));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            card.initializeDescription();
        }
    }

    @SpirePatch(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class UseCardActionPatch {
        @SpireInsertPatch(locator = UseCardActionLocator.class, localvars = {"targetCard"})
        public static void Insert(UseCardAction _instance, @ByRef(type = "cards.AbstractCard") Object[] _card) {
            if (_card[0] instanceof AbstractVUPShionCard) {
                AbstractVUPShionCard c = ((AbstractVUPShionCard) _card[0]);
                c.postReturnToHand();
            }
        }
    }

    private static class UseCardActionLocator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "onCardDrawOrDiscard");
            return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "freeToPlay"
    )
    public static class FreeCardPowerPatch {
        @SpireInsertPatch(rloc = 2)
        public static SpireReturn<Boolean> Insert(AbstractCard _instance) {
            if (AbstractDungeon.player != null && AbstractDungeon.currMapNode != null
                    && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT
                    && AbstractDungeon.player.hasPower(FreeCardPower.POWER_ID))
                return SpireReturn.Return(true);


            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class ReturnToHandPatch {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(CardGroup.class.getName()) && m.getMethodName().equals("moveToDiscardPile")) {
                        m.replace("if (this.targetCard instanceof " + AbstractVUPShionCard.class.getName() + "){" +
                                AbstractCardPatches.class.getName() + ".returnToHandOnce(this.targetCard);}else{" +
                                "$_ = $proceed($$);" +
                                "}");
                    }
                }
            };
        }
    }

    public static void returnToHandOnce(AbstractCard card) {
        if (card instanceof AbstractVUPShionCard) {
            AbstractVUPShionCard c = ((AbstractVUPShionCard) card);
            if ((!GameStatsPatch.returnToHandList.contains(card.cardID) && c.returnToHand) || c.returnToHandOnce) {
                AbstractDungeon.player.hand.moveToHand(card);
                AbstractDungeon.player.onCardDrawOrDiscard();
                c.returnToHandOnce = false;
                if (!GameStatsPatch.returnToHandList.contains(card.cardID) && c.returnToHand)
                    GameStatsPatch.returnToHandList.add(card.cardID);
            } else {
                AbstractDungeon.player.hand.moveToDiscardPile(card);
            }

        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "useCard"
    )
    public static class UseCardPlayerPatch {
        @SpireInsertPatch(rloc = 23)
        public static void Insert(AbstractPlayer _instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractVUPShionCard) {
                    if (card != c)
                        ((AbstractVUPShionCard) card).triggerAfterOtherCardPlayed(c);
                }
            }

            for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractVUPShionCard) {
                    if (card != c)
                        ((AbstractVUPShionCard) card).triggerAfterOtherCardPlayed(c);
                }
            }

            for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractVUPShionCard) {
                    if (card != c)
                        ((AbstractVUPShionCard) card).triggerAfterOtherCardPlayed(c);
                }
            }

            for (AbstractCard card : AbstractDungeon.player.exhaustPile.group) {
                if (card instanceof AbstractVUPShionCard) {
                    if (card != c)
                        ((AbstractVUPShionCard) card).triggerAfterOtherCardPlayed(c);
                }
            }

            for (AbstractCard card : AbstractDungeon.player.limbo.group) {
                if (card instanceof AbstractVUPShionCard) {
                    if (card != c)
                        ((AbstractVUPShionCard) card).triggerAfterOtherCardPlayed(c);
                }
            }

        }
    }


}
