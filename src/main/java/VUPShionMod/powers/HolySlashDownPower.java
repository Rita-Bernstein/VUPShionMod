package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HolySlashDownPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("HolySlashDownPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HolySlashDownPower(AbstractCreature owner, int multiplier) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        this.amount = multiplier;
        this.setImage("Clock84.png", "Clock32.png");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        if (this.owner.hasPower(BadgeOfThePaleBlueCrossPower.POWER_ID)) {
            this.flash();
            int dmg = this.owner.getPower(BadgeOfThePaleBlueCrossPower.POWER_ID).amount * this.amount;
            addToBot(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(dmg, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
