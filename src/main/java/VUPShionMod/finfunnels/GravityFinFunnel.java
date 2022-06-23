package VUPShionMod.finfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.patches.CharacterSelectScreenPatches;
import VUPShionMod.powers.Shion.*;
import VUPShionMod.util.SaveHelper;
import VUPShionMod.vfx.Common.AbstractSpineEffect;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;

import java.util.function.Consumer;

public class GravityFinFunnel extends AbstractFinFunnel {
    public static final String ID = GravityFinFunnel.class.getSimpleName();
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID(GravityFinFunnel.class.getSimpleName()));


    public GravityFinFunnel(int level) {
        super(ID);
        upgradeLevel(level);
        this.effect = 1;

        if (CharacterSelectScreenPatches.skinManager.skinCharacters.get(0).reskinCount == 0) {
            loadAnimation("VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon2.atlas",
                    "VUPShionMod/img/ui/FinFunnel/Ori/STANCE_ZY_YTD_weapon2.json", 2.4f);
        } else {
            loadAnimation("VUPShionMod/img/ui/FinFunnel/Blue/YOFU2.atlas",
                    "VUPShionMod/img/ui/FinFunnel/Blue/YOFU2.json", 2.4f);
        }


        this.state.setAnimation(0, "weapon2_come_in", false);
        this.state.addAnimation(0, "weapon2_idle", true, 0.0f);
    }

    @Override
    public void preBattlePrep() {

        this.state.setAnimation(0, "weapon2_come_in", false);
        this.state.addAnimation(0, "weapon2_idle", true, 0.0f);
    }

    @Override
    public void upgradeLevel(int amount) {
        super.upgradeLevel(amount);
        SaveHelper.gravityFinFunnelLevel = level;
    }

    @Override
    public void loseLevel(int amount) {
        super.loseLevel(amount);
        SaveHelper.gravityFinFunnelLevel = level;
    }

    @Override
    public int getFinalEffect() {
        return this.effect * (getLevel() - 1) / 2 + 2;
    }

    @Override
    public void powerToApply(AbstractCreature target) {
        if (AbstractDungeon.player.hasPower(GravitoniumPower.POWER_ID))
            addToBot(new GainShieldAction(AbstractDungeon.player, getFinalEffect(), true));
        else
            addToBot(new GainBlockAction(AbstractDungeon.player, getFinalEffect(), true));

        Consumer<AnimationState> stateConsumer = state -> {
            state.setAnimation(0, "ZL2_GH7", false);
        };

        addToBot(new VFXAction(new AbstractSpineEffect(true,
                "VUPShionMod/img/vfx/Spine/ZL_GH3/ZL_GH3", AbstractDungeon.player.hb.cX,  AbstractDungeon.player.hb.y, 3.0f * Settings.scale,
                3.0f, 10.0f, stateConsumer)));

        super.powerToApply(target);
    }

    @Override
    public void updatePosition(Skeleton skeleton) {

        body = this.skeleton.findBone("weapon2_bone");
        muzzle = this.skeleton.findBone("weapon2_muzzle");


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
            this.state.setAnimation(0, "weapon2_attack", false).setTimeScale(3.0f);
            this.state.addAnimation(0, "weapon2_idle", true, 0.0F);
        }
    }

}
