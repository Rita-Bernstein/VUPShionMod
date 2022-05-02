package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import VUPShionMod.monsters.PlagaAMundo;
import VUPShionMod.monsters.PlagaAMundoMinion;
import VUPShionMod.skins.AbstractSkinCharacter;
import VUPShionMod.skins.sk.Shion.AquaShion;
import VUPShionMod.skins.sk.Shion.BlueGiantShion;
import VUPShionMod.skins.sk.WangChuan.AquaWangChuan;
import VUPShionMod.skins.sk.WangChuan.PurityWangChuan;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.DeathScreen;
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
// 布尔值复位放下面了

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


    @SpirePatch(
            clz = VictoryScreen.class,
            method = "updateAscensionAndBetaArtProgress"
    )
    public static class ReskinUnlockPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(VictoryScreen _instance) {
            if (VUPShionMod.fightSpecialBossWithout || VUPShionMod.fightSpecialBoss) {
                if (!Settings.seedSet && !Settings.isTrial) {
                    //深空战斗胜利
                    if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion)
                        CharacterSelectScreenPatches.skinManager.unlockSkin(BlueGiantShion.ID);

                    if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan)
                        CharacterSelectScreenPatches.skinManager.unlockSkin(AquaWangChuan.ID);
                    VUPShionMod.saveSettings();
                }
            }
//分数的复位放这里了
            VUPShionMod.fightSpecialBossWithout = false;
            VUPShionMod.fightSpecialBoss = false;
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = DeathScreen.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class DeathScreenPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(DeathScreen _instance, MonsterGroup monsters) {
            if (AbstractDungeon.actNum >= 3) {
                VUPShionMod.loadSkins();
                if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
                    GameStatsPatch.shionDeathCount++;

                    if (GameStatsPatch.shionDeathCount > 3)
                        CharacterSelectScreenPatches.skinManager.unlockSkin(AquaShion.ID);

                    VUPShionMod.saveSkins();

                }

                if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan) {

                    GameStatsPatch.wangchuanDeathCount++;

                    if (GameStatsPatch.shionDeathCount > 3)
                        CharacterSelectScreenPatches.skinManager.unlockSkin(PurityWangChuan.ID);
                    VUPShionMod.saveSkins();
                }
            }


            return SpireReturn.Continue();
        }
    }
}
