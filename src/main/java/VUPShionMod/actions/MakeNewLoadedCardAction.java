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

public class MakeNewLoadedCardAction extends AbstractGameAction {
    private AbstractCard card;
    private int amount;
    private boolean inDiscardPile = false;
    private String text = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("EnduranceInitiationAction")).TEXT[1];

    public MakeNewLoadedCardAction(AbstractCard card) {
        this.card = card;
        this.amount = 1;
    }

    public MakeNewLoadedCardAction(AbstractCard card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    public MakeNewLoadedCardAction(AbstractCard card, boolean inDiscardPile) {
        this.card = card;
        this.amount = 1;
        this.inDiscardPile = inDiscardPile;
    }

    public MakeNewLoadedCardAction(AbstractCard card, int amount, boolean inDiscardPile) {
        this.card = card;
        this.amount = amount;
        this.inDiscardPile = inDiscardPile;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hasPower(QuickTriggerPower.POWER_ID)) {
            int loopTimes = AbstractDungeon.player.getPower(QuickTriggerPower.POWER_ID).amount;
            if (loopTimes <= this.amount) {
                this.amount -= loopTimes;
                addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, QuickTriggerPower.POWER_ID));

                for (int i = 0; i < loopTimes; i++) {
                    AbstractCard t = card.makeSameInstanceOf();
                    t.tags.add(CardTagsEnum.LOADED);
                    t.rawDescription = t.rawDescription + text;
                    t.initializeDescription();
                    AbstractDungeon.player.useCard(t,
                            AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.miscRng), 0);
                    AbstractDungeon.actionManager.cardsPlayedThisTurn.add(t);
                }
            } else {
                loopTimes -= this.amount;
                addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, QuickTriggerPower.POWER_ID, this.amount));

                for (int i = 0; i < this.amount; i++) {
                    AbstractCard t = card.makeSameInstanceOf();
                    t.tags.add(CardTagsEnum.LOADED);
                    t.rawDescription = t.rawDescription + text;
                    t.initializeDescription();
                    AbstractDungeon.player.useCard(t,
                            AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.miscRng), 0);
                    AbstractDungeon.actionManager.cardsPlayedThisTurn.add(t);
                }

                this.amount = 0;
            }


        }

        if (this.amount > 0) {
            AbstractCard t = card.makeSameInstanceOf();
            t.tags.add(CardTagsEnum.LOADED);
            t.rawDescription = t.rawDescription + text;
            t.initializeDescription();
            if (!inDiscardPile)
                addToBot(new MakeTempCardInDrawPileAction(t, this.amount, true, true, false));
            else
                addToBot(new MakeTempCardInDiscardAction(t, this.amount));
        }


        isDone = true;
    }
}
