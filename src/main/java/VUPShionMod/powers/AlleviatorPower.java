package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.GainHyperdimensionalLinksAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AlleviatorPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("AlleviatorPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean turnTriggered = false;

    public AlleviatorPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        this.amount = amount;
        this.loadRegion("juggernaut");
        updateDescription();
        this.isTurnBased = true;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public void atStartOfTurn() {
        this.turnTriggered = false;
    }

    @Override
    public void onTriggerMagiamObruor(AbstractPower power) {
        if(!turnTriggered) {
            flash();
            addToBot(new GainEnergyAction(this.amount));
            turnTriggered = true;
        }
    }
}
