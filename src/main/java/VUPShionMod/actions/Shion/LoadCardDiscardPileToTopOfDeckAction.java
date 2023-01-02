package VUPShionMod.actions.Shion;

import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

import java.util.ArrayList;

public class LoadCardDiscardPileToTopOfDeckAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final boolean upgraded;

    public LoadCardDiscardPileToTopOfDeckAction(AbstractCreature source, boolean upgraded) {
        this.p = AbstractDungeon.player;
        setValues(null, source, this.amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.upgraded = upgraded;
    }


    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
            return;
        }
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            if (this.p.discardPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.p.discardPile.size() == 1) {
                AbstractCard tmp = this.p.discardPile.getTopCard();
                this.p.discardPile.removeCard(tmp);
                this.p.discardPile.moveToDeck(tmp, false);
            }
            ArrayList<AbstractCard> cardsToMove = new ArrayList<>();
            for (AbstractCard c : this.p.discardPile.group) {
                if (c.hasTag(CardTagsEnum.LOADED))
                    cardsToMove.add(c);
            }
            for (AbstractCard c : cardsToMove)
                this.p.discardPile.moveToDeck(c, !upgraded);

            this.p.discardPile.group.removeAll(cardsToMove);
        }

        tickDuration();
    }
}


