package VUPShionMod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import VUPShionMod.VUPShionMod;

public class AttackOrderBetaPower extends AbstractPower {
    public static final String POWER_ID = VUPShionMod.makeID("AttackOrderBetaPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AttackOrderBetaPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.loadRegion("time");
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        if(this.owner.hasPower(AttackOrderAlphaPower.POWER_ID))
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner, AttackOrderAlphaPower.POWER_ID));

        if(this.owner.hasPower(AttackOrderGammaPower.POWER_ID))
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner, AttackOrderGammaPower.POWER_ID));

        if(this.owner.hasPower(AttackOrderDeltaPower.POWER_ID))
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner, AttackOrderDeltaPower.POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount) ;
    }
}
