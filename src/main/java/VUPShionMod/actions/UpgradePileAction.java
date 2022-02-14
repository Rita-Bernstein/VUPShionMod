package VUPShionMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class UpgradePileAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("VUPShionMod:UpgradeAction");
    private static final float DURATION_PER_CARD = 0.25F;
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private int dupeAmount;
    private ArrayList<AbstractCard> cannotDuplicate;


    public UpgradePileAction(AbstractCreature source, int amount) {
        this.dupeAmount = 1;
        this.cannotDuplicate = new ArrayList();

        setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
        this.dupeAmount = amount;
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.hand.group) {
                if (c.canUpgrade()) {
                    temp.addToTop(c.makeSameInstanceOf());
                }
            }

            for (AbstractCard c : this.p.drawPile.group) {
                if (c.canUpgrade()) {
                    temp.addToTop(c);
                }
            }

            for (AbstractCard c : this.p.discardPile.group) {
                if (c.canUpgrade()) {
                    temp.addToTop(c);
                }
            }
            if(temp.isEmpty())
                return;

            temp.sortAlphabetically(true);
            temp.sortByRarityPlusStatusCardType(false);
            AbstractDungeon.gridSelectScreen.open(temp, this.amount, TEXT[0], false);

        }


        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)  {
                    for(AbstractCard card : GetAllInBattleInstances.get(c.uuid)){
                            card.upgrade();
                            card.superFlash();
                    }
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();

        }



        tickDuration();
    }
}


