package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import VUPShionMod.msic.AchievementShionItem;
import VUPShionMod.util.SaveHelper;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.AchievementStrings;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.screens.stats.AchievementItem;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;

import java.util.ArrayList;

public class AchievementPatches {
    private static final AchievementStrings achievementStrings = CardCrawlGame.languagePack.getAchievementString(VUPShionMod.makeID(AchievementPatches.class.getSimpleName()));
    public static final String[] NAMES = achievementStrings.NAMES;
    public static final String[] TEXT = achievementStrings.TEXT;


    @SpirePatch(
            clz = AchievementGrid.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class ShionSaveDataPatch {
        @SpirePostfixPatch
        public static void Postfix(AchievementGrid _instance) {
            ArrayList<AchievementItem> items = (ArrayList<AchievementItem>) ReflectionHacks.getPrivate(_instance, AchievementGrid.class, "items");
//            if(items != null)
//            items.add(new AchievementShionItem(NAMES[0], TEXT[0], "RitaShop", "RitaShop", false));
        }
    }

    @SpirePatch(
            clz = StatsScreen.class,
            method = "open"
    )
    public static class LoadAtlas {
        @SpirePrefixPatch
        public static void Prefix(StatsScreen _instance) {
            if (AchievementShionItem.achievemenAtlas == null) {
                AchievementShionItem.achievemenAtlas = new TextureAtlas(Gdx.files.internal("VUPShionMod/img/achievements/achievements.atlas"));
            }
        }
    }

    @SpirePatch(
            clz = StatsScreen.class,
            method = "hide"
    )
    public static class DisposeAtlas {
        @SpirePrefixPatch
        public static void Prefix(StatsScreen _instance) {
            if (AchievementShionItem.achievemenAtlas != null) {
                AchievementShionItem.achievemenAtlas.dispose();
                AchievementShionItem.achievemenAtlas = null;
            }
        }
    }

    public static void unlockAchievement(String key){
        if(VUPShionMod.isTestMod)
        if(!SaveHelper.getAchievement(key)){
            SaveHelper.unlockAchievement(key);
        }
    }

    public static boolean getAchievement(String key){
       return SaveHelper.getAchievement(key);
    }
}
