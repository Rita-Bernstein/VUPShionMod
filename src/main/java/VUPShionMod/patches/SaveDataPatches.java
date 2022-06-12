package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import VUPShionMod.skins.AbstractSkin;
import VUPShionMod.skins.AbstractSkinCharacter;
import VUPShionMod.util.ShionLoginBackground;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

public class SaveDataPatches {
    @SpirePatch(
            clz = SaveHelper.class,
            method = "deletePrefs"
    )
    public static class ShionSaveDataPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(int slot) {
            try {
                SpireConfig config = new SpireConfig("VUPShionMod", "settings", VUPShionMod.VUPShionDefaults);

                config.setBool(slot + "useSimpleOrb", false);
                config.setBool(slot + "notReplaceTitle", false);
                config.setBool(slot + "safeCampfire", false);
                config.setBool(slot + "liyezhuRelic", false);
                config.setBool(slot + "isHardMod", false);


                for (AbstractSkinCharacter character : CharacterSelectScreenPatches.skinManager.skinCharacters) {
                    config.setInt(slot + character.getClass().getSimpleName(), 0);

                    for (AbstractSkin skin : character.skins) {
                        config.setBool(slot + skin.getClass().getSimpleName(), false);
                    }

                    config.setInt(slot + "shionDeathCount", 0);
                    config.setInt(slot + "wangchuanDeathCount", 0);

                    config.setBool(slot + "liyezhuVictory", false);
                }


                config.setInt(slot + "gravityFinFunnelLevel", 1);
                config.setInt(slot + "investigationFinFunnelLevel", 1);
                config.setInt(slot + "pursuitFinFunnelLevel", 1);
                config.setInt(slot + "dissectingFinFunnelLevel", 1);
                config.setString(slot + "activeFinFunnel", "GravityFinFunnel");
                config.save();

                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return SpireReturn.Continue();
        }
    }
}
