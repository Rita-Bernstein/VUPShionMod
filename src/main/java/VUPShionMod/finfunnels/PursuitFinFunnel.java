package VUPShionMod.finfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.DamageAndApplyPursuitAction;
import VUPShionMod.actions.DamageAndGainBlockAction;
import VUPShionMod.powers.*;
import VUPShionMod.vfx.FinFunnelBeamEffect;
import VUPShionMod.vfx.FinFunnelSmallLaserEffect;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID("PursuitFinFunnel"));
    public static final String ID = VUPShionMod.makeID("PursuitFinFunnel");

    public PursuitFinFunnel() {
        super(ID);
    }

    public PursuitFinFunnel(int level) {
        super(ID);
        upgradeLevel(level);
        this.effect = 1;
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
    public void atTurnStart() {
        if (this.level <= 0) return;
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        if (m != null) {
            fire(m, this.level, DamageInfo.DamageType.THORNS);
        }
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
    public void activeFire(AbstractCreature target, int damage, DamageInfo.DamageType type, boolean triggerPassive, int loopTimes) {
        if (AbstractDungeon.player.hasPower(AttackOrderSpecialPower.POWER_ID)) {
            addToBot(new VFXAction(new FinFunnelBeamEffect(this), 0.4f));
            for (int i = 0; i < loopTimes; i++) {
                addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damage, true), type, AbstractGameAction.AttackEffect.FIRE));
            }

            if (triggerPassive) {
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                        if (!mo.isDeadOrEscaped()) {
                            addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new PursuitPower(mo, getFinalEffect())));
                        }
                    }
            }
        } else {
            addToBot(new VFXAction(new FinFunnelSmallLaserEffect(this, target), 0.3F));
            addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
            addToBot(new DamageAndApplyPursuitAction(target, new DamageInfo(AbstractDungeon.player, damage, type), loopTimes, triggerPassive, getFinalEffect()));
        }
    }

    @Override
    public void fire(AbstractCreature target, int damage, DamageInfo.DamageType type, int loopTimes) {
        if (target.isDeadOrEscaped()) return;
        if (AbstractDungeon.player.hasPower(AttackOrderBetaPower.POWER_ID)) {
            playFinFunnelAnimation(ID);
            addToBot(new SFXAction("ATTACK_DEFECT_BEAM"));
            addToBot(new VFXAction(AbstractDungeon.player, new FinFunnelBeamEffect(this), 0.4F));
            for (int i = 0; i < loopTimes; i++)
                addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(damage, true), type, AbstractGameAction.AttackEffect.FIRE));

            if (this.level > 0) {
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    for (int i = 0; i < loopTimes; i++)
                        addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new PursuitPower(mo, getFinalEffect())));
                }
            }
        } else {
            addToBot(new VFXAction(new FinFunnelSmallLaserEffect(this, target), 0.3F));
            addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
            if (AbstractDungeon.player.hasPower(AttackOrderAlphaPower.POWER_ID))
                for (int i = 0; i < loopTimes; i++)
                    addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage * 5, type)));
            else if (AbstractDungeon.player.hasPower(AttackOrderDeltaPower.POWER_ID))
                for (int i = 0; i < loopTimes; i++)
                    addToBot(new DamageAndGainBlockAction(target, new DamageInfo(AbstractDungeon.player, damage, type), 1.0f));
            else
                for (int i = 0; i < loopTimes; i++)
                    addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, type)));

//            if (AbstractDungeon.player.hasPower(AttackOrderGammaPower.POWER_ID))
//                for (int i = 0; i < loopTimes; i++)
//                    addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new BleedingPower(target, AbstractDungeon.player, 2), 2));

            if (this.level > 0) {
                for (int i = 0; i < loopTimes; i++)
                    addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new PursuitPower(target, getFinalEffect())));
            }
        }
    }

    @Override
    public void updatePosition(Skeleton skeleton) {
        body = skeleton.findBone("weapon3_bone");
        muzzle = skeleton.findBone("weapon3_fire");

        if (AbstractDungeon.player.flipHorizontal)
            this.cX = skeleton.getX() + body.getWorldX() - 48.0f * Settings.scale;
        else
            this.cX = skeleton.getX() + body.getWorldX() + 48.0f * Settings.scale;

        this.cY = skeleton.getY() + body.getWorldY();
        hb.move(cX, cY);
        this.muzzle_X = skeleton.getX() + muzzle.getWorldX();
        this.muzzle_Y = skeleton.getY() + muzzle.getWorldY();

    }
}
