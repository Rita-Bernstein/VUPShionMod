package VUPShionMod.powers.Monster.TimePortal;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.Shion.ReinsOfWarPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class LabyrinthOfTimelinePower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(LabyrinthOfTimelinePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public LabyrinthOfTimelinePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = 0;
        updateDescription();
        this.isTurnBased = false;
        loadRegion("beat");

    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.amount2++;
        if (this.amount2 >= this.amount) {
            flash();
            this.amount2 = 0;
            ArrayList<AbstractPower> powersToRemove = new ArrayList<>();
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (!p.ID.equals(ReinsOfWarPower.POWER_ID))
                    powersToRemove.add(p);
            }

            if(!powersToRemove.isEmpty())
            addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, this.owner,
                    powersToRemove.get(AbstractDungeon.miscRng.random(powersToRemove.size() - 1))));

        }

    }


    @Override
    public void atStartOfTurn() {
        this.amount2 = 0;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

}
