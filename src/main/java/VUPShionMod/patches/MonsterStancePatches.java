package VUPShionMod.patches;

import VUPShionMod.monsters.AbstractVUPShionBoss;
import VUPShionMod.vfx.Monster.Boss.MonsterDivinityParticleEffect;
import VUPShionMod.vfx.Monster.Boss.MonsterStanceAuraEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.DivinityStance;

public class MonsterStancePatches {
    @SpirePatch(
            clz = AbstractMonster.class,
            method = "applyPowers"
    )
    public static class StanceDamageApplyPowers {
        @SpireInsertPatch(rloc = 12)
        public static SpireReturn<Void> Insert(AbstractMonster _instance) {
            if (_instance instanceof AbstractVUPShionBoss)
                for (DamageInfo info : _instance.damage) {
                    info.output = (int)((AbstractVUPShionBoss) _instance).stance.atDamageGive(info.output, DamageInfo.DamageType.NORMAL);
                }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = AbstractMonster.class,
            method = "calculateDamage"
    )
    public static class StanceDamageCalculateDamage {
        @SpireInsertPatch(rloc = 13, localvars = {"tmp"})
        public static SpireReturn<Void> Insert(AbstractMonster _instance, int dmg, @ByRef float[] tmp) {
            if (_instance instanceof AbstractVUPShionBoss)
                tmp[0] = ((AbstractVUPShionBoss) _instance).stance.atDamageGive(tmp[0], DamageInfo.DamageType.NORMAL);
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "update"
    )
    public static class StanceDamageUpdate {
        @SpirePostfixPatch
        public static void Postfix(AbstractMonster _instance) {
            if (_instance instanceof AbstractVUPShionBoss){
                AbstractVUPShionBoss boss = (AbstractVUPShionBoss)_instance;
                if (boss.stance.ID.equals(DivinityStance.STANCE_ID)) {
                    if (!Settings.DISABLE_EFFECTS) {
                        boss.particleTimer -= Gdx.graphics.getDeltaTime();
                        if (boss.particleTimer < 0.0F) {
                            boss.particleTimer = 0.2F;
                            AbstractDungeon.effectsQueue.add(new MonsterDivinityParticleEffect(boss));
                        }
                    }

                    boss.particleTimer2 -= Gdx.graphics.getDeltaTime();
                    if (boss.particleTimer2 < 0.0F) {
                        boss.particleTimer2 = MathUtils.random(0.45F, 0.55F);
                        AbstractDungeon.effectsQueue.add(new MonsterStanceAuraEffect(boss,"Divinity"));
                    }
                }

            }
        }
    }
}
