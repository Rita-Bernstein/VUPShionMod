package VUPShionMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)
public class BottledStasisPatch {
    public static SpireField<Boolean> inBottledAttackCircuit = new SpireField<>(() -> false);
    public static SpireField<Boolean> inBottledTotipotentCircuit = new SpireField<>(() -> false);


    @SpirePatch(
            clz = AbstractCard.class,
            method = "makeStatEquivalentCopy"
    )
    public static class MakeStatEquivalentCopy {
        public static AbstractCard Postfix(AbstractCard __result, AbstractCard __instance) {
            inBottledAttackCircuit.set(__result, inBottledAttackCircuit.get(__instance));
            inBottledTotipotentCircuit.set(__result, inBottledTotipotentCircuit.get(__instance));


            return __result;
        }
    }
}