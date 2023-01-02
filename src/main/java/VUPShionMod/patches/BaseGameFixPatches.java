package VUPShionMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;

public class BaseGameFixPatches {
    @SpirePatch(
            clz = HandCardSelectScreen.class,
            method = "refreshSelectedCards"
    )
    public static class HandCardSelectScreenPatch {
        @SpireInsertPatch(rloc = 99, localvars = {"anyNumber"})
        public static SpireReturn<Void> Insert(HandCardSelectScreen _instance, boolean anyNumber) {
            if (_instance.selectedCards.size() >= 1 && anyNumber && !_instance.canPickZero) {
                _instance.button.enable();
            }

            return SpireReturn.Continue();
        }
    }
}
