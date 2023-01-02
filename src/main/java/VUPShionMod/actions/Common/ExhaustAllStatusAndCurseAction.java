package VUPShionMod.actions.Common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustAllStatusAndCurseAction extends AbstractGameAction {
    public ExhaustAllStatusAndCurseAction() {
    }

    @Override
    public void update() {
        for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
            if (card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.STATUS)
                addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
        }

        for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
            if (card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.STATUS)
                addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
        }

        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.STATUS)
                addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
        }
        isDone = true;
    }
}
