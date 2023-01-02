package VUPShionMod.powers.Shion;

import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.actions.Shion.MakeLoadedCardAction;
import VUPShionMod.cards.ShionCard.tempCards.QuickScreen;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import VUPShionMod.VUPShionMod;

public class ReleaseFormKuroisuPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(ReleaseFormKuroisuPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ReleaseFormKuroisuPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = 0;
        loadShionRegion("ReleaseFormKuroisuPower");
        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.amount2++;
        if (this.amount2 > 2) {
            this.amount2 = 0;
            flash();
            addToBot(new GainHyperdimensionalLinksAction(this.amount));
        }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
