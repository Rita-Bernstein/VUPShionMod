package VUPShionMod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import VUPShionMod.VUPShionMod;

public class ReleaseFormMinamiPower extends AbstractPower {
    public static final String POWER_ID = VUPShionMod.makeID("ReleaseFormMinamiPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ReleaseFormMinamiPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.loadRegion("time");
        updateDescription();
    }


    @Override
    public void atStartOfTurn() {
        addToBot(new ApplyPowerAction(this.owner,this.owner,new HyperdimensionalLinksPower(this.owner,this.amount)));
    }


    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
