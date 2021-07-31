package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class AttackInitiationPower extends TwoAmountPower {
    public static final String POWER_ID = VUPShionMod.makeID("AttackInitiationPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AttackInitiationPower(AbstractCreature owner, int amount, int amount2) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = amount2;
        this.loadRegion("draw");
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.amount2 = Math.max(amount2, 4);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.flash();
        addToBot(new DrawCardAction(this.amount));
        this.amount2--;
        if (amount2 <= 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }
}
