package VUPShionMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

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
}
