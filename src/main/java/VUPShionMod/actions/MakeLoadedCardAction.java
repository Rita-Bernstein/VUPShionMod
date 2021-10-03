package VUPShionMod.actions;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.QuickTriggerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MakeLoadedCardAction extends AbstractGameAction {
    private AbstractCard card;
    private int amount;
    private boolean inDiscardPile = false;
    private String text = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("EnduranceInitiationAction")).TEXT[1];

    public MakeLoadedCardAction(AbstractCard card) {
        this.card = card;
        this.amount = 1;
    }

    public MakeLoadedCardAction(AbstractCard card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    public MakeLoadedCardAction(AbstractCard card, boolean inDiscardPile) {
        this.card = card;
        this.amount = 1;
        this.inDiscardPile = inDiscardPile;
    }

    public MakeLoadedCardAction(AbstractCard card, int amount, boolean inDiscardPile) {
        this.card = card;
        this.amount = amount;
        this.inDiscardPile = inDiscardPile;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hasPower(QuickTriggerPower.POWER_ID)) {
            addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, QuickTriggerPower.POWER_ID,1));
            for (int i = 0; i < this.amount; i++) {
                AbstractCard card = this.card.makeSameInstanceOf();
                AbstractDungeon.player.useCard(card,
                        AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.miscRng), 0);
                AbstractDungeon.actionManager.cardsPlayedThisTurn.add(card);
            }
        } else {
            if (!inDiscardPile)
                addToBot(new MakeTempCardInDrawPileAction(this.card, this.amount, true, true, false));
            else
                addToBot(new MakeTempCardInDiscardAction(this.card, this.amount));
        }

        isDone = true;
    }
}
