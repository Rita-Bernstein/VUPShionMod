package VUPShionMod.finfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CharacterSelectScreenPatches;
import VUPShionMod.powers.Shion.*;
import VUPShionMod.util.SaveHelper;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

public class InvestigationFinFunnel extends AbstractFinFunnel {
    public static final String ID = InvestigationFinFunnel.class.getSimpleName();
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID(InvestigationFinFunnel.class.getSimpleName()));

    public InvestigationFinFunnel(int level) {
        super(ID);
        upgradeLevel(level);
        this.effect = 1;

        if (CharacterSelectScreenPatches.skinManager.skinCharacters.get(0).reskinCount == 0) {
            loadAnimation("VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon4.atlas",
                    "VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon4.json", 2.4f);
        } else {
            loadAnimation("VUPShionMod/img/ui/FinFunnel/Blue/YOFU4.atlas",
                    "VUPShionMod/img/ui/FinFunnel/Blue/YOFU4.json", 2.4f);
        }

        this.state.setAnimation(0, "weapon4_come_in", false);
        this.state.addAnimation(0, "weapon4_idle", true, 0.0f);
    }

    @Override
    public void preBattlePrep() {
        this.state.setAnimation(0, "weapon4_come_in", false);
        this.state.addAnimation(0, "weapon4_idle", true, 0.0f);
    }

    @Override
    public void upgradeLevel(int amount) {
        this.level += amount;
        SaveHelper.investigationFinFunnelLevel = level;
    }

    @Override
    public void loseLevel(int amount) {
        this.level -= amount;
        if (this.level < 0)
            this.level = 0;
        SaveHelper.investigationFinFunnelLevel = level;
    }

    @Override
    public int getFinalEffect() {
        return this.effect * (this.level - 1) / 3 + 2;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(orbStrings.DESCRIPTION[0], this.level, getFinalDamage(), getFinalEffect());
    }

    @Override
    public void powerToApply(AbstractCreature target) {
        if(target != null)
        addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new BleedingPower(target, AbstractDungeon.player, getFinalEffect())));
    }

    @Override
    public void updatePosition(Skeleton skeleton) {

        body = this.skeleton.findBone("weapon4_bone");
        muzzle = this.skeleton.findBone("weapon4_muzzle");

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
        if (id.equals(this.id)) {
            this.state.setAnimation(0, "weapon4_attack", false).setTimeScale(3.0f);
            this.state.addAnimation(0, "weapon4_idle", true, 0.0F);
        }
    }
}
