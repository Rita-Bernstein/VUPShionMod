package VUPShionMod.powers.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class HolyCoffinReleasePower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("HolyCoffinReleasePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HolyCoffinReleasePower(AbstractCreature owner, int amount) {
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
    public void onTriggerLoaded() {
        flash();
        addToBot(new GainHyperdimensionalLinksAction(this.amount));
    }
}
