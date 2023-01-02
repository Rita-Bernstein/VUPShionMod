package VUPShionMod.powers.Common;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class NextTurnAttackPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(NextTurnAttackPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public NextTurnAttackPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        loadRegion("phantasmal");
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        flash();
        Predicate<AbstractCard> predicate = (pr) -> pr.type == AbstractCard.CardType.ATTACK;
        addToBot(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.discardPile, predicate, this.amount));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public void updateDescription() {
        if (this.amount > 1) {
            this.description = String.format(DESCRIPTIONS[1], this.amount);
        } else {
            this.description = String.format(DESCRIPTIONS[0], this.amount);
        }
    }
}
