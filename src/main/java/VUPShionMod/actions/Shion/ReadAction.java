package VUPShionMod.actions.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ReadAction extends AbstractGameAction {
    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("ReadAction"))).TEXT;
    private AbstractPlayer player;
    private int numberOfCards;

    public ReadAction(int numberOfCards) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
    }


    public void update() {
        if (this.duration == this.startDuration) {

            if (this.player.exhaustPile.isEmpty() || this.numberOfCards <= 0) {
                this.isDone = true;
                return;
            }

            ArrayList<AbstractCard> cardsToCopy = new ArrayList<AbstractCard>();
            for (AbstractCard c : this.player.exhaustPile.group) {
                if (c.hasTag(CardTagsEnum.LOADED))
                    cardsToCopy.add(c);
            }

            if(cardsToCopy.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (cardsToCopy.size() <= this.numberOfCards) {
                for (AbstractCard c : cardsToCopy) {
                    addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
                }

                this.isDone = true;
                return;
            }


            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : cardsToCopy) {
                if (c.hasTag(CardTagsEnum.LOADED))
                    temp.addToTop(c);
            }
            temp.sortAlphabetically(true);
            temp.sortByRarityPlusStatusCardType(false);
            if (this.numberOfCards == 1) {
                AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[0], false);
            } else {
                AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
            }

            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                addToTop(new MakeTempCardInHandAction(c.makeStatEquivalentCopy()));
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
    }
}


