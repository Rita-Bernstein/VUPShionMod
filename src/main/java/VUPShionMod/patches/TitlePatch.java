package VUPShionMod.patches;


import VUPShionMod.util.SaveHelper;
import VUPShionMod.msic.ShionLoginBackground;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

public class TitlePatch {

    @SpirePatch(
            clz = MainMenuScreen.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {boolean.class}
    )
    public static class useCardPatch {
        @SpirePostfixPatch
        public static void Postfix(MainMenuScreen _instance, boolean playBgm) {
            if (!SaveHelper.notReplaceTitle)
                _instance.bg = new ShionLoginBackground();
        }
    }


}
