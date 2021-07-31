package VUPShionMod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import VUPShionMod.VUPShionMod;

public class ReleaseFormKuroisuPower extends AbstractPower {
    public static final String POWER_ID = VUPShionMod.makeID("ReleaseFormKuroisuPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ReleaseFormKuroisuPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.loadRegion("time");
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (this.owner.hasPower(BadgeOfTimePower.POWER_ID)) {
            addToBot(new GainBlockAction(this.owner, this.owner.getPower(BadgeOfTimePower.POWER_ID).amount * this.amount));
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, BadgeOfTimePower.POWER_ID));
        }

    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
