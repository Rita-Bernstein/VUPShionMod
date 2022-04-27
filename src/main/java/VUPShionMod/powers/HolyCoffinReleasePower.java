package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
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
//
//    @Override
//    public void atStartOfTurn() {
//        addToBot(new ReducePowerAction(this.owner,this.owner,this,1));
//    }


    @Override
    public void onTriggerLoaded() {
        addToBot(new GainHyperdimensionalLinksAction(this.amount));
//        addToBot(new ApplyPowerAction(this.owner,this.owner,new HyperdimensionalLinksPower(this.owner,this.amount)));
    }
}
