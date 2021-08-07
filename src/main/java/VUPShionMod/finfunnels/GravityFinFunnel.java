package VUPShionMod.finfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.DamageAndGainBlockAction;
import VUPShionMod.cards.anastasia.AttackOrderAlpha;
import VUPShionMod.powers.*;
import VUPShionMod.vfx.FinFunnelBeamEffect;
import VUPShionMod.vfx.FinFunnelSmallLaserEffect;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.WallopAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import com.megacrit.cardcrawl.vfx.combat.SweepingBeamEffect;

public class GravityFinFunnel extends AbstractFinFunnel {
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID("GravityFinFunnel"));


    public GravityFinFunnel() {
        super();
        this.name = orbStrings.NAME;
        this.ID = VUPShionMod.makeID("GravityFinFunnel");
    }

    @Override
    public void updateDescription() {
        this.description = orbStrings.DESCRIPTION[0] + this.level + orbStrings.DESCRIPTION[1] + 1 + orbStrings.DESCRIPTION[2];
    }

    @Override
    public void atTurnStart() {
        if (this.level <= 0) return;
        AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(true);
        if (m != null) {
            fire(m, this.level, DamageInfo.DamageType.THORNS);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        this.renderText(sb);
        this.hb.render(sb);
    }

    @Override
    public void fire(AbstractCreature target, int damage, DamageInfo.DamageType type) {

        if (AbstractDungeon.player.hasPower(AttackOrderBetaPower.POWER_ID)) {
            playFinFunnelAnimation(this.ID);
            addToBot(new SFXAction("ATTACK_DEFECT_BEAM"));
            addToBot(new VFXAction(AbstractDungeon.player, new FinFunnelBeamEffect(this), 0.4F));
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damage, true), type, AbstractGameAction.AttackEffect.FIRE));
            if (this.level > 0) {
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, 1, false)));
                }
            }
        } else {
            playFinFunnelAnimation(this.ID);
            addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
            addToBot(new VFXAction(new FinFunnelSmallLaserEffect(this, target.hb.cX, target.hb.cY), 0.3F));

            if (AbstractDungeon.player.hasPower(AttackOrderAlphaPower.POWER_ID))
                addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage * 2, type)));
            else if (AbstractDungeon.player.hasPower(AttackOrderDeltaPower.POWER_ID))
                addToBot(new DamageAndGainBlockAction(target, new DamageInfo(AbstractDungeon.player, damage, type), 0.5f));
            else
                addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, type)));

            if (AbstractDungeon.player.hasPower(AttackOrderGammaPower.POWER_ID))
                addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new BleedingPower(target, AbstractDungeon.player, 2)));

            if (this.level > 0) {
                addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new WeakPower(target, 1, false)));
            }
        }
    }

    @Override
    public void updatePosition(Skeleton skeleton) {
        body = skeleton.findBone("weapon1_bone");
        muzzle = skeleton.findBone("weapon1_fire");

        this.cX = skeleton.getX() + body.getWorldX();
        this.cY = skeleton.getY() + body.getWorldY();
        hb.move(cX + hb.width * 0.5f, cY);
        this.muzzle_X = skeleton.getX() + muzzle.getWorldX();
        this.muzzle_Y = skeleton.getY() + muzzle.getWorldY();

    }
}
