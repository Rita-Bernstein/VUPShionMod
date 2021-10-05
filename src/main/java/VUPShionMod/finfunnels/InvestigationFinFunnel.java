package VUPShionMod.finfunnels;

import VUPShionMod.VUPShionMod;
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
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

public class InvestigationFinFunnel extends AbstractFinFunnel {
    private static final OrbStrings orbStrings = CardCrawlGame.languagePack.getOrbString(VUPShionMod.makeID("InvestigationFinFunnel"));
    public static final String ID = VUPShionMod.makeID("InvestigationFinFunnel");

    public InvestigationFinFunnel() {
        super(ID);
    }

    public InvestigationFinFunnel(int level) {
        super(ID);
        upgradeLevel(level);
        this.effect = 1;
    }

    @Override
    public void upgradeLevel(int amount) {
        this.level += amount;
        VUPShionMod.investigationFinFunnelLevel = level;
    }

    @Override
    public void loseLevel(int amount) {
        this.level -= amount;
        if (this.level < 0)
            this.level = 0;
        VUPShionMod.investigationFinFunnelLevel = level;
    }

    @Override
    public int getFinalEffect() {
        return this.effect * (this.level - 1) / 3 + 2;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(orbStrings.DESCRIPTION[0], this.level, getFinalDamage(),getFinalEffect());
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
    public void activeFire(AbstractCreature target, int damage, DamageInfo.DamageType type,boolean triggerPassive,int loopTimes) {
        if (AbstractDungeon.player.hasPower(AttackOrderSpecialPower.POWER_ID)){
            addToBot(new VFXAction(new FinFunnelBeamEffect(this), 0.4f));
            for (int i = 0; i < loopTimes; i++)
                addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damage, true), type, AbstractGameAction.AttackEffect.FIRE));

            if (triggerPassive){
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                        if (!mo.isDeadOrEscaped()) {
                            addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new BleedingPower(mo,AbstractDungeon.player, getFinalEffect())));
                        }
                    }
            }
        }else {
            addToBot(new VFXAction(new FinFunnelSmallLaserEffect(this, target), 0.3F));
            addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
            for (int i = 0; i < loopTimes; i++) {
                addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, type), AbstractGameAction.AttackEffect.FIRE));
            }

            if (triggerPassive)
                addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new BleedingPower(target,AbstractDungeon.player, getFinalEffect())));
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
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damage, true), type, AbstractGameAction.AttackEffect.FIRE));
            if (this.level > 0) {
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    for (int i = 0; i < loopTimes; i++)
                    addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new BleedingPower(mo, AbstractDungeon.player, getFinalEffect())));
                }
            }
        } else {
            playFinFunnelAnimation(ID);
            addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
            addToBot(new VFXAction(new FinFunnelSmallLaserEffect(this, target), 0.3F));

            if (AbstractDungeon.player.hasPower(AttackOrderAlphaPower.POWER_ID))
                for (int i = 0; i < loopTimes; i++)
                addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage * 3, type)));
            else if (AbstractDungeon.player.hasPower(AttackOrderDeltaPower.POWER_ID))
                for (int i = 0; i < loopTimes; i++)
                addToBot(new DamageAndGainBlockAction(target, new DamageInfo(AbstractDungeon.player, damage, type), 1.0f));
            else
                addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, type)));

            if (AbstractDungeon.player.hasPower(AttackOrderGammaPower.POWER_ID))
                for (int i = 0; i < loopTimes; i++)
                addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new BleedingPower(target, AbstractDungeon.player, 2)));

            if (this.level > 0) {
                for (int i = 0; i < loopTimes; i++)
                addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new BleedingPower(target, AbstractDungeon.player, getFinalEffect())));
            }
        }
    }


    @Override
    public void updatePosition(Skeleton skeleton) {
        body = skeleton.findBone("weapon2_bone");
        muzzle = skeleton.findBone("weapon2_fire");

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
