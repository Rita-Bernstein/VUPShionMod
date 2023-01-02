package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import VUPShionMod.msic.AchievementShionItem;
import VUPShionMod.util.SaveHelper;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
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
            ArrayList<AchievementItem> items = ReflectionHacks.getPrivate(_instance, AchievementGrid.class, "items");
            if (items != null) {
                items.add(new AchievementShionItem(NAMES[0], TEXT[0], "00", "00", false));
                items.add(new AchievementShionItem(NAMES[3], TEXT[3], "03", "03", false));
                items.add(new AchievementShionItem(NAMES[4], TEXT[4], "04", "04", false));
                items.add(new AchievementShionItem(NAMES[5], TEXT[5], "05", "05", false));
                items.add(new AchievementShionItem(NAMES[7], TEXT[7], "07", "07", false));
                items.add(new AchievementShionItem(NAMES[6], TEXT[6], "06", "06", false));
                items.add(new AchievementShionItem(NAMES[8], TEXT[8], "08", "08", false));
                items.add(new AchievementShionItem(NAMES[10], TEXT[10], "10", "10", false));
            }
        }
    }


    @SpirePatch(
            clz = StatsScreen.class,
            method = "renderStatScreen"
    )
    public static class RenderStatScreenLine {
        @SpireInsertPatch(rloc = 9, localvars = {"renderY"})
        public static void Insert(StatsScreen _instance, SpriteBatch sb, @ByRef float[] renderY) {
            renderY[0] -= 1.0f * 200.0F * Settings.scale;
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

    public static void unlockAchievement(String key) {
        if (!SaveHelper.getAchievement(key)) {
            SaveHelper.unlockAchievement(key);
        }
    }

    public static boolean getAchievement(String key) {
        return SaveHelper.getAchievement(key);
    }
}
