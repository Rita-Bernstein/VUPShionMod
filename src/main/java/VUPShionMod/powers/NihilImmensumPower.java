package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class NihilImmensumPower extends TwoAmountPower {
    public static final String POWER_ID = VUPShionMod.makeID("NihilImmensumPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int triggerAmount = 9;

    public NihilImmensumPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        this.amount = amount;
        this.amount2 = 0;
        this.loadRegion("juggernaut");
        updateDescription();
        this.isTurnBased = true;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], triggerAmount, amount, this.amount2);
    }


    @Override
    public void onSpecificTrigger() {
        this.amount2++;
        if (this.amount2 >= triggerAmount) {
            flash();
            addToBot(new ApplyPowerAction(this.owner, this.owner, new IntangiblePlayerPower(this.owner, this.amount)));
            this.amount2 = 0;
        }
        updateDescription();
    }
}
