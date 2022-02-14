package VUPShionMod.patches;

import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.powers.FreeCardPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;

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
                SpireReturn.Continue();
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

}
