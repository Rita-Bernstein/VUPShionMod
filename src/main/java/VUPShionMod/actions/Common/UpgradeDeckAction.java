package VUPShionMod.actions.Common;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.function.Predicate;

public class UpgradeDeckAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("VUPShionMod:UpgradeDeckAction");
    private static final float DURATION_PER_CARD = 0.25F;
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;

    private boolean optional;
    private Predicate<AbstractCard> predicate;

    public UpgradeDeckAction(int amount, boolean optional) {


        this.actionType = ActionType.DRAW;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
        this.amount = amount;

        this.optional = optional;
    }

    public UpgradeDeckAction(int amount, boolean optional, Predicate<AbstractCard> predicate) {
        this.actionType = ActionType.DRAW;
        this.duration = 0.25F;
        this.p = AbstractDungeon.player;
        this.amount = amount;

        this.optional = optional;
        this.predicate = predicate;
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.masterDeck.isEmpty() || this.amount <= 0) {
                this.isDone = true;
                return;
            }

            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            if (this.predicate != null) {
                for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                    if (card.canUpgrade() && this.predicate.test(card))
                        temp.addToTop(card);
                }
            } else {
                for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                    if (card.canUpgrade())
                        temp.addToTop(card);
                }
            }


            if (temp.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (temp.size() <= this.amount) {
                for (AbstractCard c : temp.group) {
                    c.upgrade();
                    AbstractDungeon.player.bottledCardUpgradeCheck(c);
                    AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                }

                isDone = true;
                return;
            }

            temp.sortAlphabetically(true);
            temp.sortByRarityPlusStatusCardType(false);


            AbstractDungeon.gridSelectScreen.open(temp, this.amount, this.amount > 1 ? String.format(TEXT[1], this.amount) : TEXT[0],
                    true, false, this.optional, false);

        }


        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(c);
                if (AbstractDungeon.gridSelectScreen.selectedCards.size() > 1) {
                    float x = MathUtils.random(0.1F, 0.9F) * Settings.WIDTH;
                    float y = MathUtils.random(0.2F, 0.8F) * Settings.HEIGHT;
                    AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(x, y));
                    AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
                } else {
                    AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                }

            }
            addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

        }


        tickDuration();
    }
}

