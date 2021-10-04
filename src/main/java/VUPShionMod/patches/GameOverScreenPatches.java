package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
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
        public static SpireReturn<Void> Insert(VictoryScreen _instance, ArrayList<GameOverStat> ___stats) {
            if (VUPShionMod.fightSpecialBoss) {
                ___stats.add(new GameOverStat(specialBossStatString.TEXT[0], specialBossStatString.TEXT[1], Integer.toString(1000)));
                VUPShionMod.fightSpecialBoss = false;
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = GameOverScreen.class,
            method = "checkScoreBonus"
    )
    public static class PatchGameOverScreen {
        @SpireInsertPatch(rloc = 91)
        public static SpireReturn<Void> Insert(GameOverScreen _instance, boolean victory, int ___points) {
            if (VUPShionMod.fightSpecialBoss) {
                ___points += 1000;
            }
            return SpireReturn.Continue();
        }
    }
}
