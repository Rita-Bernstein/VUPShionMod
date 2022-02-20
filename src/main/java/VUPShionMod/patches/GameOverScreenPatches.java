package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import VUPShionMod.skins.AbstractSkinCharacter;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.GameOverStat;
import com.megacrit.cardcrawl.screens.VictoryScreen;

import java.util.ArrayList;

public class GameOverScreenPatches {
    public static UIStrings specialBossStatString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("VictoryScreen"));

    @SpirePatch(
            clz = VictoryScreen.class,
            method = "createGameOverStats"
    )
    public static class PatchVictoryScreen {
        @SpireInsertPatch(rloc = 197)
        public static SpireReturn<Void> Insert(VictoryScreen _instance) {
            ArrayList<GameOverStat> stats = (ArrayList<GameOverStat>) ReflectionHacks.getPrivate(_instance, GameOverScreen.class, "stats");
            if (VUPShionMod.fightSpecialBossWithout) {
                if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion)
                    stats.add(new GameOverStat(specialBossStatString.TEXT[0], specialBossStatString.TEXT[1], Integer.toString(1000)));
                if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan)
                    stats.add(new GameOverStat(specialBossStatString.TEXT[0], specialBossStatString.TEXT[4], Integer.toString(1000)));
            }

            if (VUPShionMod.fightSpecialBoss) {
                stats.add(new GameOverStat(specialBossStatString.TEXT[2], specialBossStatString.TEXT[3], Integer.toString(500)));
            }

            VUPShionMod.fightSpecialBossWithout = false;
            VUPShionMod.fightSpecialBoss = false;
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = GameOverScreen.class,
            method = "checkScoreBonus"
    )
    public static class PatchGameOverScreen {
        @SpireInsertPatch(rloc = 91, localvars = {"points"})
        public static SpireReturn<Void> Insert(boolean victory, @ByRef int[] points) {
            if (VUPShionMod.fightSpecialBossWithout) {
                points[0] += 1000;
            }

            if (VUPShionMod.fightSpecialBoss) {
                points[0] += 500;
            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch
            (clz = VictoryScreen.class,
                    method = "updateAscensionAndBetaArtProgress"
            )
    public static class ReskinUnlockPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(VictoryScreen _instance) {
            if (VUPShionMod.fightSpecialBossWithout || VUPShionMod.fightSpecialBoss)
                if (!Settings.seedSet && !Settings.isTrial) {
                    for (AbstractSkinCharacter c : CharacterSelectScreenPatches.characters) {
                        c.checkUnlock();
                    }
                    VUPShionMod.saveSettings();
                }
            return SpireReturn.Continue();
        }
    }
}
