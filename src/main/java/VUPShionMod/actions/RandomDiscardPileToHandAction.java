package VUPShionMod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class RandomDiscardPileToHandAction extends AbstractGameAction {
    private AbstractPlayer player;
    private int numberOfCards;

    public RandomDiscardPileToHandAction(int numberOfCards) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
    }


    public void update() {
        if (this.duration == this.startDuration) {
            if (this.player.discardPile.isEmpty() || this.numberOfCards <= 0) {
                this.isDone = true;
                return;
            }

            ArrayList<AbstractCard> cardsToMove = new ArrayList<AbstractCard>(this.player.discardPile.group);

            if (this.player.discardPile.size() <= this.numberOfCards) {
                for (AbstractCard c : cardsToMove) {
                    if (this.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                        this.player.hand.addToHand(c);
                        this.player.discardPile.removeCard(c);
                    }
                    c.lighten(false);
                    c.applyPowers();
                }

                this.isDone = true;
                return;
            }

            AbstractCard c;
            for (int i = 0; i < this.numberOfCards; i++) {
                if (this.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                    c = cardsToMove.get(AbstractDungeon.cardRng.random(cardsToMove.size() - 1));
                    this.player.hand.addToHand(c);
                    this.player.discardPile.removeCard(c);

                    c.lighten(false);
                    c.applyPowers();
                }
            }

            this.isDone = true;
            return;
        }

        tickDuration();
    }
}
