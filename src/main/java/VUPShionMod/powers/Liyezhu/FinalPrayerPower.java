package VUPShionMod.powers.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class FinalPrayerPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(FinalPrayerPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FinalPrayerPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        loadRegion("barricade");
        updateDescription();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if ((card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE) && !this.owner.hasPower("No Draw")) {
            flash();
            addToBot(new DrawCardAction(this.owner, 1));
        }
    }


    @Override
    public int onPlayerGainedBlock(int blockAmount) {
        return super.onPlayerGainedBlock(blockAmount);
    }

    @Override
    public void onGainedBlock(float blockAmount) {
        if (blockAmount > 0.0f)
            addToBot(new GainShieldAction(this.owner, (int) blockAmount));
    }

    @Override
    public int onLoseBlock(int amount) {
        addToBot(new GainBlockAction(this.owner, 5));
        return super.onLoseBlock(amount);
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
