package VUPShionMod.actions.EisluRen;


import VUPShionMod.VUPShionMod;
import VUPShionMod.stances.LightArmorStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.UIStrings;


public class LightArmorStanceAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(LightArmorStanceAction.class.getSimpleName()));
    public static final String[] TEXT = uiStrings.TEXT;

    public LightArmorStanceAction() {

        this.actionType = ActionType.EXHAUST;
        this.duration = 0.25F;
    }


    private void exhaustCard(AbstractCard card) {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.uuid.equals(card.uuid)) {
                addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                return;
            }
        }

        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.uuid.equals(card.uuid)) {
                addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
                return;
            }
        }

        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.uuid.equals(card.uuid)) {
                addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile));
                return;
            }
        }
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            LightArmorStance.cardsToPlay.clear();
            
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.type == AbstractCard.CardType.ATTACK)
                    temp.addToTop(c.makeSameInstanceOf());
            }

            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if (c.type == AbstractCard.CardType.ATTACK)
                    temp.addToTop(c.makeSameInstanceOf());
            }

            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c.type == AbstractCard.CardType.ATTACK)
                    temp.addToTop(c.makeSameInstanceOf());
            }

            if (temp.isEmpty()) {
                isDone = true;
                return;
            }


            temp.sortAlphabetically(true);
            temp.sortByRarityPlusStatusCardType(false);
            AbstractDungeon.gridSelectScreen.open(temp, 2, TEXT[0], false, false, true, false);
            AbstractDungeon.overlayMenu.cancelButton.show(CardCrawlGame.languagePack.getUIString("GridCardSelectScreen").TEXT[1]);
        }


        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                for (AbstractCard c : GetAllInBattleInstances.get(card.uuid)) {
                    exhaustCard(card);
                    LightArmorStance.cardsToPlay.addToTop(card.makeStatEquivalentCopy());
                    break;
                }

            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();

        }


        tickDuration();
    }
}
