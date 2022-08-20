package VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.DissectingFinFunnel;
import VUPShionMod.powers.Shion.ReinsOfWarPower;
import VUPShionMod.powers.Shion.StructureDissectionPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.util.SaveHelper;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BossDissectingFinFunnel extends AbstractBossFinFunnel {
    public static final String ID = DissectingFinFunnel.class.getSimpleName();
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID(DissectingFinFunnel.class.getSimpleName()));


    public BossDissectingFinFunnel(int level, AbstractCreature owner, int skinIndex) {
        super(owner, ID);
        upgradeLevel(level);
        this.effect = 1;

        if (skinIndex == 0) {
            loadAnimation("VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon1.atlas",
                    "VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon1.json", SkinManager.getSkin(0, 2).renderScale);
        } else {
            loadAnimation("VUPShionMod/img/ui/FinFunnel/Blue/YOFU1.atlas",
                    "VUPShionMod/img/ui/FinFunnel/Blue/YOFU1.json", SkinManager.getSkin(0, 0).renderScale);
        }

        this.state.setAnimation(0, "weapon1_come_in", false);
        this.state.addAnimation(0, "weapon1_idle", true, 0.0f);

        this.state.setAnimation(1, "weapon1_idle_lightring", true);
    }


    @Override
    public int getFinalEffect() {
        return this.effect * (getLevel() - 1) / 3 + 1;
    }



      @Override
    public void preBattlePrep() {
//        AbstractPower p = new ReinsOfWarPower(AbstractDungeon.player);
//        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, p));
//        p.atStartOfTurnPostDraw();

        this.state.setAnimation(0, "weapon1_come_in", false);
        this.state.addAnimation(0, "weapon1_idle", true, 0.0f);
    }


    @Override
    public void powerToApply(AbstractCreature target, float amountScale, boolean top) {
        if (target != null && (int) Math.floor(getFinalEffect() * amountScale) > 0) {
            if (top) {
                addToTop(new ApplyPowerAction(target, this.owner, new StructureDissectionPower(target, (int) Math.floor(getFinalEffect() * amountScale))));
            } else {
                addToBot(new ApplyPowerAction(target, this.owner, new StructureDissectionPower(target, (int) Math.floor(getFinalEffect() * amountScale))));
            }
        }

        super.powerToApply(target, amountScale, top);
    }

    @Override
    public void updatePosition() {

        body = this.skeleton.findBone("weapon1_bone");
        muzzle = this.skeleton.findBone("weapon1_muzzle");


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
            this.state.setAnimation(0, "weapon1_attack", false).setTimeScale(3.0f);
            this.state.addAnimation(0, "weapon1_idle", true, 0.0F);
        }
    }

}
