package VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.DamageAndApplyPursuitAction;
import VUPShionMod.finfunnels.MatrixFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.powers.Shion.DefensiveOrderPower;
import VUPShionMod.powers.Shion.PursuitPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.vfx.Monster.Boss.BossFinFunnelSmallLaserEffect;
import VUPShionMod.vfx.Shion.FinFunnelSmallLaserEffect;
import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

public class BossPursuitFinFunnel extends AbstractBossFinFunnel {
    public static final String ID = PursuitFinFunnel.class.getSimpleName();
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID(PursuitFinFunnel.class.getSimpleName()));


    public BossPursuitFinFunnel(int level, AbstractCreature owner, int skinIndex) {
        super(owner, ID);
        upgradeLevel(level);
        this.effect = 1;

        if (skinIndex == 0) {
            loadAnimation("VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon3.atlas",
                    "VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon3.json", SkinManager.getSkin(0, 0).renderScale);
        } else {
            loadAnimation("VUPShionMod/img/ui/FinFunnel/Blue/YOFU3.atlas",
                    "VUPShionMod/img/ui/FinFunnel/Blue/YOFU3.json", SkinManager.getSkin(0, 1).renderScale);
        }


        this.state.setAnimation(0, "weapon3_come_in", false);
        this.state.addAnimation(0, "weapon3_idle", true, 0.0f);
    }


    @Override
    public int getFinalEffect() {
        return this.effect * (getLevel() - 1) / 3 + 1;
    }


    @Override
    public void onPursuitEnemy(AbstractCreature target, int loop) {
        if (getLevel() <= 0) return;
        if (!target.isDeadOrEscaped())
            if (target.hasPower(PursuitPower.POWER_ID)) {
                addToBot(new VFXAction(new BossFinFunnelSmallLaserEffect(this, target), 0.3F));
                addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
                addToBot(new DamageAndApplyPursuitAction(target, new DamageInfo(this.owner, target.getPower(PursuitPower.POWER_ID).amount,
                        DamageInfo.DamageType.THORNS), loop, false, getFinalEffect()));
            }

    }

    @Override
    public void preBattlePrep() {

        this.state.setAnimation(0, "weapon3_come_in", false);
        this.state.addAnimation(0, "weapon3_idle", true, 0.0f);
    }


    @Override
    public void powerToApply(AbstractCreature target, float amountScale, boolean top) {
        if (target != null && (int) Math.floor(getFinalEffect() * amountScale) > 0) {
            if (top)
                addToTop(new ApplyPowerAction(target, this.owner, new PursuitPower(target, this.owner,(int) Math.floor(getFinalEffect() * amountScale))));
            else
                addToBot(new ApplyPowerAction(target, this.owner, new PursuitPower(target, this.owner,(int) Math.floor(getFinalEffect() * amountScale))));
        }
        super.powerToApply(target, amountScale, top);
    }

    @Override
    public void updatePosition() {

        body = this.skeleton.findBone("weapon3_bone");
        muzzle = this.skeleton.findBone("weapon3_muzzle");

        if (this.owner.flipHorizontal)
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
        if (id.equals(this.id)) {
            this.state.setAnimation(0, "weapon3_attack", false).setTimeScale(3.0f);
            this.state.addAnimation(0, "weapon3_idle", true, 0.0F);
        }
    }

}
