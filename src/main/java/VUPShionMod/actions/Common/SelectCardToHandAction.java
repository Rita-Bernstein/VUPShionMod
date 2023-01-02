package VUPShionMod.actions.Common;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class SelectCardToHandAction extends AbstractGameAction {
    private boolean retrieveCard;
    private final ArrayList<AbstractCard> cards;
    private final boolean freeForTurn;
    private final boolean isExhaust;
    private final boolean isEthereal;

    public SelectCardToHandAction(ArrayList<AbstractCard> cards, boolean freeForTurn, boolean isExhaust, boolean isEthereal) {
        this.retrieveCard = false;
        this.cards = cards;

        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;

        this.freeForTurn = freeForTurn;
        this.isEthereal = isEthereal;
        this.isExhaust = isExhaust;
    }

    public SelectCardToHandAction(ArrayList<AbstractCard> cards, boolean freeForTurn, boolean isExhaust) {
        this(cards, freeForTurn, isExhaust, false);
    }

    public SelectCardToHandAction(ArrayList<AbstractCard> cards, boolean freeForTurn) {
        this(cards, freeForTurn, false, false);
    }

    public SelectCardToHandAction(ArrayList<AbstractCard> cards) {
        this(cards, false, false, false);
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding() || cards.isEmpty()) {
            this.isDone = true;
            return;
        }

        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.cardRewardScreen.customCombatOpen(cards, CardRewardScreen.TEXT[1], false);
            tickDuration();
            return;
        }
        if (!this.retrieveCard) {
            if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                if (this.freeForTurn)
                    disCard.setCostForTurn(0);

                if (this.isExhaust) {
                    disCard.exhaust = true;
                    disCard.rawDescription += CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("SelectCardToHandAction")).TEXT[0];
                    disCard.initializeDescription();
                }

                if (this.isEthereal) {
                    disCard.isEthereal = true;
                    disCard.rawDescription = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("SelectCardToHandAction")).TEXT[0] + disCard.rawDescription;
                    disCard.initializeDescription();
                }

                disCard.current_x = -1000.0F * Settings.scale;
                if (AbstractDungeon.player.hand.size() < 10) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                }
                AbstractDungeon.cardRewardScreen.discoveryCard = null;
            }
            this.retrieveCard = true;
        }
        tickDuration();
    }
}


