package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AnastasiaCorePower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("AnastasiaCorePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AnastasiaCorePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.loadRegion("focus");
        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(CardTagsEnum.FIN_FUNNEL)) {
            this.flash();
            addToBot(new GainEnergyAction(this.amount));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
