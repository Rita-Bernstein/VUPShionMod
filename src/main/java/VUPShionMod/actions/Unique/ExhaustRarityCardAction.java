package VUPShionMod.actions.Unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StasisPower;

public class ExhaustRarityCardAction extends AbstractGameAction {

    public ExhaustRarityCardAction() {
        this.card = null;
        this.duration = Settings.ACTION_DUR_LONG;
        this.startingDuration = Settings.ACTION_DUR_LONG;
        this.actionType = AbstractGameAction.ActionType.WAIT;
    }

    private float startingDuration;
    private AbstractCard card;

    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty() && AbstractDungeon.player.discardPile.isEmpty()) {
            this.isDone = true;

            return;
        }
        if (this.duration == this.startingDuration) {
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.card = AbstractDungeon.player.discardPile.getRandomCard(AbstractDungeon.cardRandomRng, AbstractCard.CardRarity.RARE);


                if (this.card == null) {
                    this.card = AbstractDungeon.player.discardPile.getRandomCard(AbstractDungeon.cardRandomRng, AbstractCard.CardRarity.UNCOMMON);


                    if (this.card == null) {
                        this.card = AbstractDungeon.player.discardPile.getRandomCard(AbstractDungeon.cardRandomRng, AbstractCard.CardRarity.COMMON);


                        if (this.card == null) {
                            this.card = AbstractDungeon.player.discardPile.getRandomCard(AbstractDungeon.cardRandomRng);
                        }
                    }
                }
                AbstractDungeon.player.discardPile.moveToExhaustPile(this.card);
            } else {
                this.card = AbstractDungeon.player.drawPile.getRandomCard(AbstractDungeon.cardRandomRng, AbstractCard.CardRarity.RARE);


                if (this.card == null) {
                    this.card = AbstractDungeon.player.drawPile.getRandomCard(AbstractDungeon.cardRandomRng, AbstractCard.CardRarity.UNCOMMON);


                    if (this.card == null) {
                        this.card = AbstractDungeon.player.drawPile.getRandomCard(AbstractDungeon.cardRandomRng, AbstractCard.CardRarity.COMMON);


                        if (this.card == null) {
                            this.card = AbstractDungeon.player.drawPile.getRandomCard(AbstractDungeon.cardRandomRng);
                        }
                    }
                }
                AbstractDungeon.player.drawPile.moveToExhaustPile(this.card);
            }
            CardCrawlGame.dungeon.checkForPactAchievement();
        }

        tickDuration();
//        if (this.isDone && this.card != null) {
//            addToTop(new ShowCardAction(this.card));
//        }
    }
}


