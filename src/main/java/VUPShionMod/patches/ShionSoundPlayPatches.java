package VUPShionMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSleepEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;


public class ShionSoundPlayPatches {
    @SpirePatch(
            clz = CampfireSmithEffect.class,
            method = "update"
    )
    public static class CampfireSmithEffectPatch {
        @SpirePostfixPatch
        public static void Postfix(CampfireSmithEffect _instance) {
            if (_instance.isDone)
                CardCrawlGame.sound.play("SHION_13");
        }
    }

    @SpirePatch(
            clz = CampfireSleepEffect.class,
            method = "update"
    )
    public static class CampfireSleepEffectPatch {
        @SpirePostfixPatch
        public static void Postfix(CampfireSleepEffect _instance) {
            if (_instance.isDone)
                CardCrawlGame.sound.play("SHION_8");
        }
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "die",
            paramtypez = {boolean.class}
    )
    public static class AbstractMonsterPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractMonster _instance,boolean triggerRelics) {
            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
                if (_instance.type == AbstractMonster.EnemyType.BOSS || _instance.type == AbstractMonster.EnemyType.ELITE)
                    CardCrawlGame.sound.play("SHION_8");
            }
        }

    }
}


