package VUPShionMod.powers.Shion;

import VUPShionMod.actions.Shion.MakeLoadedCardAction;
import VUPShionMod.cards.ShionCard.tempCards.QuickScreen;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import VUPShionMod.VUPShionMod;

public class UpgradedReleaseFormKuroisuPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(UpgradedReleaseFormKuroisuPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public UpgradedReleaseFormKuroisuPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        loadShionRegion("Clock");
        updateDescription();
        this.isTurnBased = true;
    }


    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractCard c = new QuickScreen();
        c.upgrade();
        addToBot(new MakeLoadedCardAction(true, c, this.amount));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
