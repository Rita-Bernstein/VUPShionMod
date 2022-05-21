package VUPShionMod.finfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.DamageAndApplyPursuitAction;
import VUPShionMod.actions.Shion.DamageAndGainBlockAction;
import VUPShionMod.patches.CharacterSelectScreenPatches;
import VUPShionMod.powers.Shion.*;
import VUPShionMod.vfx.FinFunnelBeamEffect;
import VUPShionMod.vfx.FinFunnelSmallLaserEffect;
import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

public class PursuitFinFunnel extends AbstractFinFunnel {
    public static final String ID = PursuitFinFunnel.class.getSimpleName();
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID(PursuitFinFunnel.class.getSimpleName()) );

    public PursuitFinFunnel(int level) {
        super(ID);
        upgradeLevel(level);
        this.effect = 1;


        if (CharacterSelectScreenPatches.skinManager.skinCharacters.get(0).reskinCount == 0) {
            loadAnimation("VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon3.atlas",
                    "VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon3.json", 2.4f);
        }
        else {
            loadAnimation("VUPShionMod/img/ui/FinFunnel/Blue/YOFU3.atlas",
                    "VUPShionMod/img/ui/FinFunnel/Blue/YOFU3.json", 2.4f);
        }


        this.state.setAnimation(0, "weapon3_come_in", false);
        this.state.addAnimation(0, "weapon3_idle", true, 0.0f);

    }

    @Override
    public void preBattlePrep() {
        this.state.setAnimation(0, "weapon3_come_in", false);
        this.state.addAnimation(0, "weapon3_idle", true, 0.0f);
    }

    @Override
    public void upgradeLevel(int amount) {
        this.level += amount;
        VUPShionMod.pursuitFinFunnelLevel = level;
    }

    @Override
    public void loseLevel(int amount) {
        this.level -= amount;
        if (this.level < 0)
            this.level = 0;
        VUPShionMod.pursuitFinFunnelLevel = level;
    }

    @Override
    public int getFinalEffect() {
        return this.effect * (this.level - 1) / 2 + 1;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(orbStrings.DESCRIPTION[0], this.level, getFinalDamage(), getFinalEffect());
    }


    @Override
    public void onPursuitEnemy(AbstractCreature target, int loop) {
        if (this.level <= 0) return;
        if (!target.isDeadOrEscaped())
            if (target.hasPower(PursuitPower.POWER_ID)) {
                addToBot(new VFXAction(new FinFunnelSmallLaserEffect(this, target), 0.3F));
                addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
                addToBot(new DamageAndApplyPursuitAction(target, new DamageInfo(AbstractDungeon.player, target.getPower(PursuitPower.POWER_ID).amount,
                        DamageInfo.DamageType.THORNS), loop, false, getFinalEffect()));
            }

    }


    @Override
    public void powerToApply(AbstractCreature target) {
        if(target != null)
        addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new PursuitPower(target, getFinalEffect())));
    }

    @Override
    public void updatePosition(Skeleton skeleton) {

        body = this.skeleton.findBone("weapon3_bone");
        muzzle = this.skeleton.findBone("weapon3_muzzle");


        if (AbstractDungeon.player.flipHorizontal)
            this.cX = this.skeleton.getX() + body.getWorldX() - 48.0f * Settings.scale;
        else
            this.cX = this.skeleton.getX() + body.getWorldX() + 48.0f * Settings.scale;
        this.cY = this.skeleton.getY() + body.getWorldY();
        hb.move(cX, cY);
        this.muzzle_X = this.skeleton.getX() + muzzle.getWorldX();
        this.muzzle_Y = this.skeleton.getY() + muzzle.getWorldY();

    }

    @Override
    public void playFinFunnelAnimation(String id) {
        if(id.equals(this.id)){
            this.state.setAnimation(0, "weapon3_attack", false).setTimeScale(3.0f);
            this.state.addAnimation(0, "weapon3_idle", true, 0.0F);
        }
    }
}
