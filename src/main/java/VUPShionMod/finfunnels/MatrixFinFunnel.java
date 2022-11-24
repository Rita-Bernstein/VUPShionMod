package VUPShionMod.finfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainFinFunnelChargeAction;
import VUPShionMod.patches.CharacterSelectScreenPatches;
import VUPShionMod.powers.Shion.BleedingPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.util.SaveHelper;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

public class MatrixFinFunnel extends AbstractFinFunnel {
    public static final String ID = MatrixFinFunnel.class.getSimpleName();
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID(MatrixFinFunnel.class.getSimpleName()));


    public MatrixFinFunnel(int level) {
        this(level,-1);
    }

    public MatrixFinFunnel(int level, int index) {
        super(ID);
        upgradeLevel(level);
        this.effect = 1;


        switch (SkinManager.getSkinCharacter(0).reskinCount) {
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
    protected void initAnimation(int index){
        if(SkinManager.getSkinCharacter(0).reskinCount!=3) {
            this.state.setAnimation(0, "weapon4_come_in", false);
            this.state.addAnimation(0, "weapon4_idle", true, 0.0f);
        }else {
            super.initAnimation(index);
        }

    }


    @Override
    public void preBattlePrep() {
        initAnimation(this.index);
    }

    @Override
    public void upgradeLevel(int amount) {
        super.upgradeLevel(amount);
        SaveHelper.matrixFinFunnelLevel = level;
    }

    @Override
    public void loseLevel(int amount) {
        super.loseLevel(amount);
        SaveHelper.matrixFinFunnelLevel = level;
    }

    @Override
    public int getFinalEffect() {
        return Math.min(this.effect * (getLevel() - 1) / 3 + 1, 8);
    }


    @Override
    public void powerToApply(AbstractCreature target, float amountScale, boolean top) {
        if ((int) Math.floor(getFinalEffect() * amountScale) > 0) {
            if (top) {
                addToTop(new GainFinFunnelChargeAction((int) Math.floor(getFinalEffect() * amountScale)));
            } else
                addToBot(new GainFinFunnelChargeAction((int) Math.floor(getFinalEffect() * amountScale)));
        }

        super.powerToApply(target, amountScale, top);
    }

    @Override
    public void updatePosition(Skeleton skeleton) {

        if(SkinManager.getSkinCharacter(0).reskinCount!=3) {
            body = this.skeleton.findBone("weapon4_bone");
            muzzle = this.skeleton.findBone("weapon4_muzzle");
        }else {
            body = this.skeleton.findBone("weapon" + (index + 1) + "_bone");
            muzzle = this.skeleton.findBone("weapon" + (index + 1) + "_muzzle");
        }
        super.updatePosition(skeleton);

    }

    @Override
    public void playFinFunnelAnimation(String id) {
        if (id.equals(this.id)) {
            if(SkinManager.getSkinCharacter(0).reskinCount!=3) {
                this.state.setAnimation(0, "weapon4_attack", false).setTimeScale(3.0f);
                this.state.addAnimation(0, "weapon4_idle", true, 0.0F);
            }else {
                this.state.setAnimation(0, "weapon" + (index + 1) + "_attack", false).setTimeScale(3.0f);
                this.state.addAnimation(0, "weapon" + (index + 1) + "_idle", true, 0.0F);
            }
        }
    }
}
