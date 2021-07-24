package VUPShionMod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import VUPShionMod.VUPShionMod;

public class BadgeOfTimePower extends AbstractPower {
    public static final String POWER_ID = VUPShionMod.makeID("BadgeOfTimePower");
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public BadgeOfTimePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.loadRegion("time");
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;

        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

        if (this.amount > 10) {
            this.amount = 10;
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
