package VUPShionMod.powers.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class BrokenSanctuaryPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(BrokenSanctuaryPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean justApplied;

    public BrokenSanctuaryPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        loadRegion("swivel");
        updateDescription();
    }


    @Override
    public void updateDescription() {
        this.description = this.amount > 1 ? String.format(DESCRIPTIONS[1], this.amount) : DESCRIPTIONS[0];
    }


    public void atEndOfRound() {
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }




}
