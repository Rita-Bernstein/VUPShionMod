package VUPShionMod.powers;

import VUPShionMod.actions.Shion.MakeLoadedCardAction;
import VUPShionMod.cards.ShionCard.tempCards.QuickScreen;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import VUPShionMod.VUPShionMod;

public class UpgradedReleaseFormKuroisuPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("UpgradedReleaseFormKuroisuPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public UpgradedReleaseFormKuroisuPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.setImage("Clock84.png", "Clock32.png");
        updateDescription();
        this.isTurnBased = true;
    }



    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractCard c = new QuickScreen();
        c.upgrade();
        addToBot(new MakeLoadedCardAction(true,c,this.amount));
//        addToBot(new MakeTempCardInDrawPileAction(c, this.amount, false, true, false));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
