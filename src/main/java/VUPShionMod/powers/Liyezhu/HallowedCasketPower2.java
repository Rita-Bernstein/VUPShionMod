package VUPShionMod.powers.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class HallowedCasketPower2 extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(HallowedCasketPower2.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HallowedCasketPower2(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.loadRegion("anger");
        updateDescription();
    }


    @Override
    public void updateDescription() {
        this.description = String.format(this.amount > 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[0], this.amount, this.amount);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (card instanceof Miracle) {
            flash();
            addToBot(new DrawCardAction(this.amount));
            addToBot(new ApplyPowerAction(this.owner, this.owner, new PsychicPower(this.owner, this.amount)));
        }
    }
}
