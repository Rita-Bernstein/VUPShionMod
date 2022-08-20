package VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.finfunnels.DissectingFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.powers.Shion.GravitoniumPower;
import VUPShionMod.powers.Shion.StructureDissectionPower;
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

public class BossGravityFinFunnel extends AbstractBossFinFunnel {
    public static final String ID = GravityFinFunnel.class.getSimpleName();
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID(GravityFinFunnel.class.getSimpleName()));


    public BossGravityFinFunnel(int level, AbstractCreature owner, int skinIndex) {
        super(owner, ID);
        upgradeLevel(level);
        this.effect = 1;

        if (skinIndex == 0) {
            loadAnimation("VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon2.atlas",
                    "VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon2.json", SkinManager.getSkin(0, 2).renderScale);
        } else {
            loadAnimation("VUPShionMod/img/ui/FinFunnel/Blue/YOFU2.atlas",
                    "VUPShionMod/img/ui/FinFunnel/Blue/YOFU2.json", SkinManager.getSkin(0, 0).renderScale);
        }

        this.state.setAnimation(0, "weapon2_come_in", false);
        this.state.addAnimation(0, "weapon2_idle", true, 0.0f);
    }


    @Override
    public int getFinalEffect() {
        return this.effect * (getLevel() - 1) / 3 + 2;
    }


    @Override
    public void preBattlePrep() {

        this.state.setAnimation(0, "weapon2_come_in", false);
        this.state.addAnimation(0, "weapon2_idle", true, 0.0f);
    }


    @Override
    public void powerToApply(AbstractCreature target, float amountScale, boolean top) {
        if ((int) Math.floor(getFinalEffect() * amountScale) > 0) {
            if (top) {
                Consumer<AnimationState> stateConsumer = state -> {
                    state.setAnimation(0, "ZL2_GH7", false);
                };

                addToTop(new VFXAction(new AbstractSpineEffect(true,
                        "VUPShionMod/img/vfx/Spine/ZL_GH3/ZL_GH3", this.owner.hb.cX, this.owner.hb.y, 2.0f,
                        3.0f, 10.0f, stateConsumer)));


                addToTop(new GainBlockAction(this.owner, (int) Math.floor(getFinalEffect() * amountScale), true));

            } else {

                addToBot(new GainBlockAction(this.owner, (int) Math.floor(getFinalEffect() * amountScale), true));

                Consumer<AnimationState> stateConsumer = state -> {
                    state.setAnimation(0, "ZL2_GH7", false);
                };

                addToBot(new VFXAction(new AbstractSpineEffect(true,
                        "VUPShionMod/img/vfx/Spine/ZL_GH3/ZL_GH3", this.owner.hb.cX, this.owner.hb.y, 2.0f,
                        3.0f, 10.0f, stateConsumer)));
            }

        }
        super.powerToApply(target, amountScale, top);
    }

    @Override
    public void updatePosition() {

        body = this.skeleton.findBone("weapon2_bone");
        muzzle = this.skeleton.findBone("weapon2_muzzle");


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
            this.state.setAnimation(0, "weapon2_attack", false).setTimeScale(3.0f);
            this.state.addAnimation(0, "weapon2_idle", true, 0.0F);
        }
    }

}
