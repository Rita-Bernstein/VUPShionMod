package VUPShionMod.patches;

import VUPShionMod.monsters.HardModeBoss.EisluRen.*;
import VUPShionMod.monsters.HardModeBoss.Liyezhu.*;
import VUPShionMod.monsters.HardModeBoss.Shion.*;
import VUPShionMod.monsters.HardModeBoss.WangChuan.*;
import VUPShionMod.util.SaveHelper;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.map.DungeonMap;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

public class TrainingModePatches {
    public static Color lightColor = Color.WHITE.cpy();
    private static float lightTimer = 0.0f;

    @SpirePatch(
            clz = TheEnding.class,
            method = "initializeBoss"
    )
    public static class TheEndingBossListPatch {
        @SpirePostfixPatch
        public static void Postfix(TheEnding _instance) {
            if (SaveHelper.isTrainingMod) {
                AbstractDungeon.bossList.clear();

                AbstractDungeon.bossList.add(AquaShionBoss.ID);
                AbstractDungeon.bossList.add(AquaShionBoss.ID);
                AbstractDungeon.bossList.add(AquaShionBoss.ID);
            }
        }
    }

    @SpirePatch(
            clz = Exordium.class,
            method = "initializeBoss"
    )
    public static class TheBottomBossListPatch {
        @SpirePostfixPatch
        public static void Postfix(Exordium _instance) {
            if (SaveHelper.isTrainingMod) {
                AbstractDungeon.bossList.clear();

                AbstractDungeon.bossList.add(OriLiyezhuBoss.ID);
                AbstractDungeon.bossList.add(OriLiyezhuBoss.ID);
                AbstractDungeon.bossList.add(OriLiyezhuBoss.ID);
            }
        }
    }


    @SpirePatch(
            clz = TheCity.class,
            method = "initializeBoss"
    )
    public static class TheCityBossListPatch {
        @SpirePostfixPatch
        public static void Postfix(TheCity _instance) {
            if (SaveHelper.isTrainingMod) {
                AbstractDungeon.bossList.clear();

                AbstractDungeon.bossList.add(OriEisluRenBoss.ID);
                AbstractDungeon.bossList.add(OriEisluRenBoss.ID);
                AbstractDungeon.bossList.add(OriEisluRenBoss.ID);
            }
        }
    }    @SpirePatch(


            clz = TheBeyond.class,
            method = "initializeBoss"
    )
    public static class TheBeyondBossListPatch {
        @SpirePostfixPatch
        public static void Postfix(TheBeyond _instance) {
            if (SaveHelper.isTrainingMod) {
                AbstractDungeon.bossList.clear();

                AbstractDungeon.bossList.add(ChinaWangChuanBoss.ID);
                AbstractDungeon.bossList.add(PurityWangChuanBoss.ID);
                AbstractDungeon.bossList.add(PurityWangChuanBoss.ID);
            }
        }
    }

    @SpirePatch(
            clz = DungeonMap.class,
            method = "renderBossIcon"
    )
    public static class BossIconColorPatch {
        @SpireInsertPatch(rloc = 10)
        public static void Insert(DungeonMap _instance, SpriteBatch sb) {
            if (SaveHelper.isTrainingMod) {
                sb.setColor(lightColor);
            }
        }
    }


    @SpirePatch(
            clz = DungeonMap.class,
            method = "renderBossIcon"
    )
    public static class BossIconColorPatch2 {
        @SpireInsertPatch(rloc = 24)
        public static void Insert(DungeonMap _instance, SpriteBatch sb) {
            if (SaveHelper.isTrainingMod) {
                sb.setColor(lightColor);
            }
        }
    }


    @SpirePatch(
            clz = DungeonMap.class,
            method = "update"
    )
    public static class BossIconColorUpdate {
        @SpireInsertPatch(rloc = 5)
        public static void Insert(DungeonMap _instance) {
            if (SaveHelper.isTrainingMod) {
                Color bossNodeColor = ReflectionHacks.getPrivate(_instance, DungeonMap.class, "bossNodeColor");

                if (_instance.bossHb.hovered) {
                    lightColor.a = 1.0f * bossNodeColor.a;
                } else {
                    lightTimer += Gdx.graphics.getDeltaTime() * 3.0f;
                    lightColor.a = ((float) Math.cos(lightTimer) + 1.0f) * 0.2f + 0.6f;
                    lightColor.a *= bossNodeColor.a;
                }
            }
        }
    }


}
