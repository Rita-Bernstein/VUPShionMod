package VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.InvestigationFinFunnel;
import VUPShionMod.powers.Shion.BleedingPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.vfx.Common.AbstractSpineEffect;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

import java.util.function.Consumer;

public class BossInvestigationFinFunnel extends AbstractBossFinFunnel {
    public static final String ID = InvestigationFinFunnel.class.getSimpleName();
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID(InvestigationFinFunnel.class.getSimpleName()));

    public BossInvestigationFinFunnel(int level, AbstractCreature owner, int skinIndex) {
        this(level, owner, -1, skinIndex);
    }

    public BossInvestigationFinFunnel(int level, AbstractCreature owner, int index, int skinIndex) {
        super(owner, ID, skinIndex);
        upgradeLevel(level);
        this.effect = 1;

        switch (skinIndex) {
            case 0:
                loadAnimation("VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon4.atlas",
                        "VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon4.json", SkinManager.getSkin(0).renderScale);
                break;
            case 3:
                loadAnimation("VUPShionMod/img/ui/FinFunnel/Minami/Stance_NXM_FUYO.atlas",
                        "VUPShionMod/img/ui/FinFunnel/Minami/Stance_NXM_FUYO.json", SkinManager.getSkin(0).renderScale);
                break;
            default:
                loadAnimation("VUPShionMod/img/ui/FinFunnel/Blue/YOFU4.atlas",
                        "VUPShionMod/img/ui/FinFunnel/Blue/YOFU4.json", SkinManager.getSkin(0).renderScale);
        }

        this.index = index;

        initAnimation(this.index);
    }

    @Override
    protected void initAnimation(int index) {
        if (this.index < 0) {
            this.state.setAnimation(0, "weapon4_come_in", false);
            this.state.addAnimation(0, "weapon4_idle", true, 0.0f);
        } else {
            super.initAnimation(index);
        }

    }


    @Override
    public int getFinalEffect() {
        return this.effect * (getLevel() - 1) / 3 + 2;
    }


    @Override
    public void preBattlePrep() {
        initAnimation(this.index);
    }


    @Override
    public void powerToApply(AbstractCreature target, float amountScale, boolean top) {
        if (target != null && (int) Math.floor(getFinalEffect() * amountScale) > 0) {
            if (top)
                addToTop(new ApplyPowerAction(target, this.owner, new BleedingPower(target, this.owner, (int) Math.floor(getFinalEffect() * amountScale))));
            else
                addToBot(new ApplyPowerAction(target, this.owner, new BleedingPower(target, this.owner, (int) Math.floor(getFinalEffect() * amountScale))));
        }
        super.powerToApply(target, amountScale, top);
    }

    @Override
    public void updatePosition(Skeleton skeleton) {
        if (this.index < 0) {
            body = this.skeleton.findBone("weapon4_bone");
            muzzle = this.skeleton.findBone("weapon4_muzzle");
        } else {
            body = this.skeleton.findBone("weapon" + (index + 1) + "_bone");
            muzzle = this.skeleton.findBone("weapon" + (index + 1) + "_muzzle");
        }

        super.updatePosition(skeleton);

    }

    @Override
    public void playFinFunnelAnimation(String id) {
        if (id.equals(this.id)) {
            if (this.index < 0) {
                this.state.setAnimation(0, "weapon4_attack", false).setTimeScale(3.0f);
                this.state.addAnimation(0, "weapon4_idle", true, 0.0F);
            } else {
                this.state.setAnimation(0, "weapon" + (index + 1) + "_attack", false).setTimeScale(1.9f);
                this.state.addAnimation(0, "weapon" + (index + 1) + "_idle", true, 0.0f).setTimeScale(0.5f);
            }
        }
    }
}
