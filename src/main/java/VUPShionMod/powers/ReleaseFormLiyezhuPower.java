package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.OmegaFlashEffect;

public class ReleaseFormLiyezhuPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("ReleaseFormLiyezhuPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ReleaseFormLiyezhuPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        this.amount = amount;
        this.setImage("Clock84.png", "Clock32.png");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            int dmg = 0;
            if (this.owner.hasPower(HyperdimensionalLinksPower.POWER_ID)) {
                dmg = this.owner.getPower(HyperdimensionalLinksPower.POWER_ID).amount;
            }
            if (dmg > 0) {
                this.flash();
                for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!m.isDeadOrEscaped()) {
                        addToBot(new VFXAction(new OmegaFlashEffect(m.hb.cX, m.hb.cY)));
                    }
                }
                addToBot(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(dmg*this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
            }
        }
    }
}
