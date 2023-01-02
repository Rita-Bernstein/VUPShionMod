package VUPShionMod.actions.Common;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SelectSrcCardToHandAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("VUPShionMod:SelectSrcCardToHandAction");
    private static final float DURATION_PER_CARD = 0.25F;
    public static final String[] TEXT = uiStrings.TEXT;
    private final AbstractPlayer p;
    private final boolean optional;
    private Predicate<AbstractCard> predicate = null;
    private Consumer<AbstractCard> cardConsumer = null;

    public SelectSrcCardToHandAction(int amount, boolean optional) {
        this.actionType = ActionType.DRAW;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;

        this.optional = optional;
    }

    public SelectSrcCardToHandAction(int amount, boolean optional, Predicate<AbstractCard> predicate) {
        this.actionType = ActionType.DRAW;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
        this.amount = amount;

        this.optional = optional;
        this.predicate = predicate;
    }

    public SelectSrcCardToHandAction(int amount, boolean optional, Predicate<AbstractCard> predicate, Consumer<AbstractCard> cardConsumer) {
        this(amount, optional, predicate);
        this.cardConsumer = cardConsumer;
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.amount <= 0) {
                this.isDone = true;
                return;
            }

            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            if (this.predicate != null) {
                for (AbstractCard card : AbstractDungeon.srcCommonCardPool.group) {
                    if (this.predicate.test(card) && !card.hasTag(AbstractCard.CardTags.HEALING)) {
                        temp.addToTop(card);
                    }
                }

                for (AbstractCard card : AbstractDungeon.srcUncommonCardPool.group) {
                    if (this.predicate.test(card) && !card.hasTag(AbstractCard.CardTags.HEALING)) {
                        temp.addToTop(card);
                    }
                }

                for (AbstractCard card : AbstractDungeon.srcRareCardPool.group) {
                    if (this.predicate.test(card) && !card.hasTag(AbstractCard.CardTags.HEALING)) {
                        temp.addToTop(card);
                    }
                }

            } else {
                for (AbstractCard card : AbstractDungeon.srcCommonCardPool.group) {
                    if (!card.hasTag(AbstractCard.CardTags.HEALING)) {
                        temp.addToTop(card);
                    }
                }

                for (AbstractCard card : AbstractDungeon.srcUncommonCardPool.group) {
                    if (!card.hasTag(AbstractCard.CardTags.HEALING)) {
                        temp.addToTop(card);
                    }
                }

                for (AbstractCard card : AbstractDungeon.srcRareCardPool.group) {
                    if (!card.hasTag(AbstractCard.CardTags.HEALING)) {
                        temp.addToTop(card);
                    }
                }

            }


            if (temp.isEmpty()) {
                this.isDone = true;
                return;
            }

            temp.sortAlphabetically(true);
            temp.sortByRarityPlusStatusCardType(false);


            AbstractDungeon.gridSelectScreen.open(temp, 1, TEXT[0], false, false, this.optional, false);

        }


        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                AbstractCard c = card.makeStatEquivalentCopy();
                if (this.cardConsumer != null) {
                    this.cardConsumer.accept(c);
                }
                addToTop(new MakeTempCardInHandAction(c, this.amount));
            }

        }
        AbstractDungeon.gridSelectScreen.selectedCards.clear();


        tickDuration();
    }
}


