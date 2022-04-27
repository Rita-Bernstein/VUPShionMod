package VUPShionMod.actions.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.MakeNewLoadedCardAction;
import VUPShionMod.cards.ShionCard.shion.EnduranceInitiation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.List;

public class EnduranceInitiationAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("EnduranceInitiationAction"));
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private boolean upgraded;
    private int pickAmount = 1;
    private List<AbstractCard> toRemove = new ArrayList<>();


    public EnduranceInitiationAction(int amount, boolean upgraded) {
        this.amount = amount; //复制的份数，不是选择的牌数
        this.duration = this.startDuration = AbstractGameAction.DEFAULT_DURATION;
        this.p = AbstractDungeon.player;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }
            for (AbstractCard card : this.p.hand.group) {
                if (card.cardID.equals(EnduranceInitiation.ID)) {
                    toRemove.add(card);
                }
            }
            this.p.hand.group.removeAll(toRemove);

            if (this.p.hand.size() <= pickAmount) {
                for (AbstractCard card : this.p.hand.group) {
                    addToBot(new MakeNewLoadedCardAction(card,this.amount));
                    if(this.upgraded)
                        addToBot(new MakeNewLoadedCardAction(card,this.amount,true));
                }

                AbstractDungeon.player.hand.applyPowers();
                this.tickDuration();
                returnCardsToHand();
                return;
            }

            if (this.p.hand.size() > pickAmount) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], pickAmount, false);
            }

            AbstractDungeon.player.hand.applyPowers();
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                addToBot(new MakeNewLoadedCardAction(card,this.amount));
                if(this.upgraded)
                    addToBot(new MakeNewLoadedCardAction(card,this.amount,true));
                this.p.hand.moveToHand(card);
            }
            returnCardsToHand();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    private void returnCardsToHand() {
        for (AbstractCard card : toRemove) {
            this.p.hand.moveToHand(card);
        }
        this.p.hand.refreshHandLayout();
    }
}
