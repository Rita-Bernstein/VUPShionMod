package VUPShionMod.finfunnels;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.anastasia.AttackOrderAlpha;
import VUPShionMod.powers.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
    private static final Texture IMG = new Texture(VUPShionMod.assetPath("img/finFunnels/gravityFinFunnel.png"));

    public GravityFinFunnel() {
        super();
        this.name = orbStrings.NAME;
        this.ID = VUPShionMod.makeID("GravityFinFunnel");
        this.img = IMG;
    }

    @Override
    public void updateDescription() {
        this.description = orbStrings.DESCRIPTION[0] + this.level + orbStrings.DESCRIPTION[1] + this.level + orbStrings.DESCRIPTION[2];
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
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y);
        this.renderText(sb);
        this.hb.render(sb);
    }

    @Override
    public void fire(AbstractCreature target, int damage, DamageInfo.DamageType type) {
        if (AbstractDungeon.player.hasPower(AttackOrderBetaPower.POWER_ID)) {
            addToBot(new SFXAction("ATTACK_DEFECT_BEAM"));
            addToBot(new VFXAction(AbstractDungeon.player, new SweepingBeamEffect(this.hb.cX, this.hb.cY, AbstractDungeon.player.flipHorizontal), 0.4F));
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damage, false), type, AbstractGameAction.AttackEffect.FIRE));
            if (this.level > 0) {
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, this.level, false)));
                }
            }
        } else {
            addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
            addToBot(new VFXAction(new SmallLaserEffect(target.hb.cX, target.hb.cY, this.hb.cX, this.hb.cY), 0.3F));

            if (AbstractDungeon.player.hasPower(AttackOrderAlphaPower.POWER_ID))
                addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage * 2, type)));
            else if (AbstractDungeon.player.hasPower(AttackOrderDeltaPower.POWER_ID))
                addToBot(new WallopAction(target, new DamageInfo(AbstractDungeon.player, damage, type)));
            else
                addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, type)));

            if (AbstractDungeon.player.hasPower(AttackOrderGammaPower.POWER_ID))
                addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new BleedingPower(target, AbstractDungeon.player, 2)));

            if (this.level > 0) {
                addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new WeakPower(target, this.level, false)));
            }
        }
    }
}
