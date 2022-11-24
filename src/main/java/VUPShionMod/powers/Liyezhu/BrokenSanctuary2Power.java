package VUPShionMod.powers.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class BrokenSanctuary2Power extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(BrokenSanctuary2Power.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean justApplied;

    public BrokenSanctuary2Power(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        loadRegion("swivel");
        updateDescription();
    }


    @Override
    public void updateDescription() {
        this.description = String.format(this.amount > 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.hasTag(CardTagsEnum.Prayer_CARD)){
            addToBot(new DrawCardAction(this.amount));
        }
    }

}
