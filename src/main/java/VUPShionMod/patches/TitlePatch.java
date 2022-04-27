package VUPShionMod.patches;


import VUPShionMod.VUPShionMod;
import VUPShionMod.util.ShionLoginBackground;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

public class TitlePatch {

    @SpirePatch(
            clz = MainMenuScreen.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = { boolean.class}
    )
    public static class useCardPatch {
        @SpirePostfixPatch
        public static SpireReturn<Void> Insert(MainMenuScreen _instance) {
            if(!VUPShionMod.notReplaceTitle)
            _instance.bg = new ShionLoginBackground();
            return SpireReturn.Continue();
        }
    }


}
