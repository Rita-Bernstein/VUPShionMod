package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
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
                stats.add(new GameOverStat(specialBossStatString.TEXT[0], specialBossStatString.TEXT[1], Integer.toString(1000)));
            }

            if (VUPShionMod.fightSpecialBoss) {
                stats.add(new GameOverStat(specialBossStatString.TEXT[2], specialBossStatString.TEXT[3], Integer.toString(1000)));
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
}
