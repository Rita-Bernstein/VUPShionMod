package VUPShionMod.prayers;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.OrbStrings;

public class MiracleUpgradePrayer extends AbstractPrayer {
    public static final String Prayer_ID = VUPShionMod.makeID(MiracleUpgradePrayer.class.getSimpleName());
    private static final OrbStrings prayerStrings = CardCrawlGame.languagePack.getOrbString(Prayer_ID);
    public static final String NAME = prayerStrings.NAME;
    public static final String[] DESCRIPTIONS = prayerStrings.DESCRIPTION;

    public MiracleUpgradePrayer(int turns, int amount) {
        this.ID = Prayer_ID;
        this.name = NAME;
        this.turns = turns;
        this.amount = amount;
        updateDescription();
        loadRegion("energized_blue");
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void use() {
        AbstractCard mir = new Miracle();
        mir.upgrade();
        addToBot(new MakeTempCardInHandAction(mir, this.amount));
    }

    @Override
    public AbstractPrayer makeCopy() {
        return new MiracleUpgradePrayer(this.turns, this.amount);
    }
}
