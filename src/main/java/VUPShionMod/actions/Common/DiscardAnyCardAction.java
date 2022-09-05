package VUPShionMod.actions.Common;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.function.Consumer;


public class DiscardAnyCardAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("BetterDiscardAction"));
    public static final String[] TEXT = uiStrings.TEXT;

    private boolean justStart = true;

    private Consumer<Integer> consumer;

    public DiscardAnyCardAction(int amount) {
        this.actionType = ActionType.DISCARD;
        this.amount = amount;
        this.duration = 0.5f;
    }

    public DiscardAnyCardAction(int amount, Consumer<Integer> consumer) {
        this.actionType = ActionType.DISCARD;
        this.amount = amount;
        this.duration = 0.5f;
        this.consumer = consumer;
    }


    public void update() {
        if (this.justStart) {
            this.justStart = false;
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, true, true);
            addToBot(new WaitAction(0.25F));
            tickDuration();

            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    GameActionManager.incrementDiscard(false);
                    c.triggerOnManualDiscard();

                }

                if (this.consumer != null)
                    consumer.accept(AbstractDungeon.handCardSelectScreen.selectedCards.group.size());
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        tickDuration();
    }
}