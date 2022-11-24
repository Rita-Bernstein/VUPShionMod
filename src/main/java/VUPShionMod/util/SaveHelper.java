package VUPShionMod.util;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.AchievementPatches;
import VUPShionMod.patches.CharacterSelectScreenPatches;
import VUPShionMod.patches.GameStatsPatch;
import VUPShionMod.skins.AbstractSkin;
import VUPShionMod.skins.AbstractSkinCharacter;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.skins.sk.Shion.BlueGiantShion;
import VUPShionMod.skins.sk.Shion.MinamiShion;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import javafx.scene.control.Skin;

public class SaveHelper {
    public static int gravityFinFunnelLevel = 1;
    public static int investigationFinFunnelLevel = 1;
    public static int pursuitFinFunnelLevel = 1;
    public static int dissectingFinFunnelLevel = 1;
    public static int matrixFinFunnelLevel = 1;
    public static String activeFinFunnel = "GravityFinFunnel";


    public static boolean useSimpleOrb = false;
    public static boolean notReplaceTitle = false;
    public static boolean safeCampfire = false;
    public static boolean safePortrait = false;

    public static boolean liyezhuRelic = false;
    public static boolean fightSpecialBoss = false;
    public static boolean fightSpecialBossWithout = false;
    public static boolean isHardMod = false;
    public static boolean isTrainingMod = false;
    public static boolean liyezhuVictory = false;


    public static void saveSettings() {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "settings", VUPShionMod.VUPShionDefaults);
            config.setBool(CardCrawlGame.saveSlot + "useSimpleOrb", useSimpleOrb);
            config.setBool(CardCrawlGame.saveSlot + "notReplaceTitle", notReplaceTitle);
            config.setBool(CardCrawlGame.saveSlot + "safeCampfire", safeCampfire);
            config.setBool(CardCrawlGame.saveSlot + "safePortrait", safePortrait);
            config.setBool(CardCrawlGame.saveSlot + "liyezhuRelic", liyezhuRelic);
            config.setBool(CardCrawlGame.saveSlot + "isHardMod", isHardMod);
            config.setBool(CardCrawlGame.saveSlot + "isTrainingMod", isTrainingMod);


            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "settings", VUPShionMod.VUPShionDefaults);
            config.load();
            useSimpleOrb = config.getBool(CardCrawlGame.saveSlot + "useSimpleOrb");
            notReplaceTitle = config.getBool(CardCrawlGame.saveSlot + "notReplaceTitle");
            safeCampfire = config.getBool(CardCrawlGame.saveSlot + "safeCampfire");
            safePortrait = config.getBool(CardCrawlGame.saveSlot + "safePortrait");
            liyezhuRelic = config.getBool(CardCrawlGame.saveSlot + "liyezhuRelic");
            isHardMod = config.getBool(CardCrawlGame.saveSlot + "isHardMod");
            isTrainingMod = config.getBool(CardCrawlGame.saveSlot + "isTrainingMod");

        } catch (Exception e) {
            e.printStackTrace();
            clearSettings();
        }
    }

    public static void clearSettings() {
        saveSettings();
    }

    public static void saveSkins() {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "settings", VUPShionMod.VUPShionDefaults);
            for (AbstractSkinCharacter character : CharacterSelectScreenPatches.skinManager.skinCharacters) {
                config.setInt(CardCrawlGame.saveSlot + character.getClass().getSimpleName(), character.reskinCount);

                for (AbstractSkin skin : character.skins) {
                    config.setBool(CardCrawlGame.saveSlot + skin.getClass().getSimpleName(), skin.unlock);
                }


                config.setInt(CardCrawlGame.saveSlot + "shionDeathCount", GameStatsPatch.shionDeathCount);
                config.setInt(CardCrawlGame.saveSlot + "wangchuanDeathCount", GameStatsPatch.wangchuanDeathCount);

                config.setBool(CardCrawlGame.saveSlot + "liyezhuVictory", liyezhuVictory);
            }
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadSkins() {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "settings", VUPShionMod.VUPShionDefaults);
            config.load();

            for (AbstractSkinCharacter character : CharacterSelectScreenPatches.skinManager.skinCharacters) {
                character.reskinCount = config.getInt(CardCrawlGame.saveSlot + character.getClass().getSimpleName());

                for (AbstractSkin skin : character.skins) {
                    skin.unlock = config.getBool(CardCrawlGame.saveSlot + skin.getClass().getSimpleName());
                    skin.button.locked = !skin.unlock;
                }
            }

            GameStatsPatch.shionDeathCount = config.getInt(CardCrawlGame.saveSlot + "shionDeathCount");
            GameStatsPatch.wangchuanDeathCount = config.getInt(CardCrawlGame.saveSlot + "wangchuanDeathCount");

            if (config.getBool(CardCrawlGame.saveSlot + "ReskinUnlock" + 0)) {
                SkinManager.getSkin(0, 1).unlock = true;
                SkinManager.getSkin(0, 1).button.locked = false;
            }

            if (config.getBool(CardCrawlGame.saveSlot + "ReskinUnlock" + 1)) {
                SkinManager.getSkin(1, 2).unlock = true;
                SkinManager.getSkin(1, 2).button.locked = false;
            }

            liyezhuVictory = config.getBool(CardCrawlGame.saveSlot + "liyezhuVictory");

            if (SkinManager.getSkin(0, 2).unlock) {
                AchievementPatches.unlockAchievement("05");
            }

            if (SkinManager.getSkin(1, 1).unlock) {
                AchievementPatches.unlockAchievement("04");
            }

            if (SkinManager.getSkin(3, 0).unlock) {
                AchievementPatches.unlockAchievement("07");
                CharacterSelectScreenPatches.skinManager.unlockSkin(MinamiShion.ID);
            }


            if (SkinManager.getSkin(1, 3).unlock) {
                AchievementPatches.unlockAchievement("06");
            }

            if (liyezhuVictory) {
                AchievementPatches.unlockAchievement("08");
            }

            if (SkinManager.getSkin(0, 1).unlock || SkinManager.getSkin(1, 2).unlock) {
                AchievementPatches.unlockAchievement("03");
            }


        } catch (Exception e) {
            e.printStackTrace();
            clearSkins();
        }
    }

    public static void clearSkins() {
        saveSkins();
    }

    public static void saveFinFunnels() {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "settings", VUPShionMod.VUPShionDefaults);
            config.setInt(CardCrawlGame.saveSlot + "gravityFinFunnelLevel", gravityFinFunnelLevel);
            config.setInt(CardCrawlGame.saveSlot + "investigationFinFunnelLevel", investigationFinFunnelLevel);
            config.setInt(CardCrawlGame.saveSlot + "pursuitFinFunnelLevel", pursuitFinFunnelLevel);
            config.setInt(CardCrawlGame.saveSlot + "dissectingFinFunnelLevel", dissectingFinFunnelLevel);
            config.setInt(CardCrawlGame.saveSlot + "matrixFinFunnelLevel", matrixFinFunnelLevel);
            config.setString(CardCrawlGame.saveSlot + "activeFinFunnel", activeFinFunnel);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadFinFunnels() {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "settings", VUPShionMod.VUPShionDefaults);
            config.load();
            gravityFinFunnelLevel = config.getInt(CardCrawlGame.saveSlot + "gravityFinFunnelLevel");
            investigationFinFunnelLevel = config.getInt(CardCrawlGame.saveSlot + "investigationFinFunnelLevel");
            pursuitFinFunnelLevel = config.getInt(CardCrawlGame.saveSlot + "pursuitFinFunnelLevel");
            dissectingFinFunnelLevel = config.getInt(CardCrawlGame.saveSlot + "dissectingFinFunnelLevel");
            matrixFinFunnelLevel = config.getInt(CardCrawlGame.saveSlot + "matrixFinFunnelLevel");
            String tmp = config.getString(CardCrawlGame.saveSlot + "activeFinFunnel");

            if (tmp.length() > 3)
                activeFinFunnel = tmp;

        } catch (Exception e) {
            e.printStackTrace();
            clearFinFunnels();
        }
    }

    public static void clearFinFunnels() {
        saveFinFunnels();
    }


    public static void unlockAchievement(String key) {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "settings", VUPShionMod.VUPShionDefaults);
            config.setBool(CardCrawlGame.saveSlot + "Achievement" + key, true);
            config.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getAchievement(String key) {
        try {
            SpireConfig config = new SpireConfig("VUPShionMod", "settings", VUPShionMod.VUPShionDefaults);
            config.load();
            return config.getBool(CardCrawlGame.saveSlot + "Achievement" + key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
