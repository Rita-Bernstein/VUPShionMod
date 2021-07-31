package VUPShionMod.actions;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class EnduranceInitiationAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean upgraded;
    private int pickAmount = 1;

    public EnduranceInitiationAction(int amount, boolean upgraded) {
        this.amount = amount; //复制的份数，不是选择的牌数
        this.duration = this.startDuration = AbstractGameAction.DEFAULT_DURATION;
        this.p = AbstractDungeon.player;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.size() <= pickAmount) {
                for(AbstractCard card : this.p.hand.group) {
                    AbstractCard t = card.makeSameInstanceOf();
                    t.exhaustOnUseOnce = true;
                    if (this.upgraded) {
                        t.tags.add(CardTagsEnum.LOADED);
                        t.rawDescription = t.rawDescription + TEXT[2];
                    } else {
                        t.rawDescription = t.rawDescription + TEXT[1];
                    }
                    t.initializeDescription();
                    addToBot(new MakeTempCardInDrawPileAction(t, this.amount, true, true, false));
                }

                AbstractDungeon.player.hand.applyPowers();
                this.tickDuration();
                return;
            }

            if (this.p.hand.size() > pickAmount) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], pickAmount, false);
            }

            AbstractDungeon.player.hand.applyPowers();
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractCard t = card.makeSameInstanceOf();
                t.exhaustOnUseOnce = true;
                if (this.upgraded) {
                    t.tags.add(CardTagsEnum.LOADED);
                    t.rawDescription = t.rawDescription + TEXT[2];
                } else {
                    t.rawDescription = t.rawDescription + TEXT[1];
                }
                t.initializeDescription();
                addToBot(new MakeTempCardInDrawPileAction(t, this.amount, true, true, false));
                this.p.hand.moveToHand(card);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("EnduranceInitiationAction"));
        TEXT = uiStrings.TEXT;
    }
}
