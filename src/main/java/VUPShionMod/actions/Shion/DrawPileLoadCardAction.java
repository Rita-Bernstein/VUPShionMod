package VUPShionMod.actions.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
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

public class DrawPileLoadCardAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(DrawPileLoadCardAction.class.getSimpleName()));
    private static final float DURATION_PER_CARD = 0.25F;
    private final AbstractPlayer p;
    private final ArrayList<AbstractCard> cannotDuplicate;
    private boolean anyNum = false;


    public DrawPileLoadCardAction(int amount) {
        this(amount, false);
    }

    public DrawPileLoadCardAction(int amount, boolean anyNum) {
        this.cannotDuplicate = new ArrayList();

        setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        this.actionType = ActionType.DRAW;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
        this.anyNum = anyNum;
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.drawPile.group) {
                if (!c.hasTag(CardTagsEnum.LOADED))
                    temp.addToTop(c);
            }

            if (temp.isEmpty()) {
                isDone = true;
                return;
            }


            if (temp.size() <= this.amount && !anyNum) {
                for (AbstractCard c : temp.group) {
                    c.tags.add(CardTagsEnum.LOADED);
                    c.exhaust = true;
                    c.rawDescription = c.rawDescription + uiStrings.TEXT[1];
                    c.initializeDescription();
                }
                isDone = true;
                return;
            }

            temp.sortAlphabetically(true);
            temp.sortByRarityPlusStatusCardType(false);
            AbstractDungeon.gridSelectScreen.open(temp, this.amount, uiStrings.TEXT[0], false, false, true, false);
            if (this.anyNum)
                AbstractDungeon.overlayMenu.cancelButton.show(CardCrawlGame.languagePack.getUIString("GridCardSelectScreen").TEXT[1]);
        }


        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.tags.add(CardTagsEnum.LOADED);
                c.exhaust = true;
                c.rawDescription = c.rawDescription + uiStrings.TEXT[1];
                c.initializeDescription();
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();

        }


        tickDuration();
    }
}


