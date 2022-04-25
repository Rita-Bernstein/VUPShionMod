package VUPShionMod.stances;

import VUPShionMod.VUPShionMod;
import VUPShionMod.character.Liyezhu;
import VUPShionMod.patches.AbstractPlayerEnum;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;

public class PrayerStance extends AbstractStance {
    public static final String STANCE_ID = "VUPShionMod:Prayer";
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);


    private static long sfxId = -1L;

    public PrayerStance() {
        this.ID = STANCE_ID;// 21
        this.name = stanceString.NAME;
        updateDescription();
    }

    @Override
    public void updateAnimation() {
        if (!(AbstractDungeon.player.chosenClass == AbstractPlayerEnum.Liyezhu)) {
//            if (!Settings.DISABLE_EFFECTS) {
//
//                this.particleTimer -= Gdx.graphics.getDeltaTime();
//                if (this.particleTimer < 0.0F) {
//                    this.particleTimer = 0.04F;
//                    AbstractDungeon.effectsQueue.add(new DefensiveModeStanceParticleEffect(new Color(1.0F, 0.9F, 0.7F, 0.0F)));
//                }
//            }
//
//
//            this.particleTimer2 -= Gdx.graphics.getDeltaTime();
//            if (this.particleTimer2 < 0.0F) {
//                this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
//                AbstractDungeon.effectsQueue.add(new StanceAuraEffect("DefensiveMode"));
//            }
        }
    }


    @Override
    public void onEnterStance() {
        if (sfxId != -1L) {
            stopIdleSfx();
        }

//        if (!(AbstractDungeon.player.chosenClass == AbstractPlayerEnum.Liyezhu)) {
//            sfxId = CardCrawlGame.sound.playAndLoop(GuardianMod.makeID("STANCE_LOOP_Defensive_Mode"));
//        }

//        AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player,
//                new IntenseZoomEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, false), 0.05F, true));
//        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.RED, true));


    }


    @Override
    public void onExitStance() {
        stopIdleSfx();

    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            sfxId = -1L;
        }
    }


    @Override
    public void onEndOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,
                new RegenPower(AbstractDungeon.player,1)));
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL){
            return damage * 0.75f;
        }

        return damage;
    }

    @Override
    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
    }
}
