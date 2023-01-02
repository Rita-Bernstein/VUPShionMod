package VUPShionMod.actions.Common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class ExhaustDiscardPileAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    private static final float DURATION_PER_CARD = 0.25F;
    public static final String[] TEXT = uiStrings.TEXT;
    private final AbstractPlayer p;
    private int dupeAmount;
    private final ArrayList<AbstractCard> cannotDuplicate;
    private boolean anyNum = false;

    public ExhaustDiscardPileAction(AbstractCreature source, int amount) {
        this(source, amount, false);
    }


    public ExhaustDiscardPileAction(AbstractCreature source, int amount, boolean anyNum) {
        this.dupeAmount = 1;
        this.cannotDuplicate = new ArrayList();

        setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
        this.dupeAmount = amount;
        this.anyNum = anyNum;
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.discardPile.group) {
                temp.addToTop(c);
            }

            if (temp.isEmpty()) {
                isDone = true;
                return;
            }


            if (temp.size() <= this.amount && !this.anyNum) {
                for (AbstractCard c : temp.group) {
                    this.p.discardPile.moveToExhaustPile(c);
                    CardCrawlGame.dungeon.checkForPactAchievement();
                    c.exhaustOnUseOnce = false;
                    c.freeToPlayOnce = false;
                }
                isDone = true;
                return;
            }

            temp.sortAlphabetically(true);
            temp.sortByRarityPlusStatusCardType(false);
            AbstractDungeon.gridSelectScreen.open(temp, this.amount, TEXT[0], false, false, this.anyNum, false);
            if (this.anyNum)
                AbstractDungeon.overlayMenu.cancelButton.show(CardCrawlGame.languagePack.getUIString("GridCardSelectScreen").TEXT[1]);
        }


        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                this.p.discardPile.moveToExhaustPile(c);
                CardCrawlGame.dungeon.checkForPactAchievement();
                c.exhaustOnUseOnce = false;
                c.freeToPlayOnce = false;
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();

        }


        tickDuration();
    }
}


