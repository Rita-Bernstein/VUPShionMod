package VUPShionMod.actions.Wangchuan;

import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExitiumMaerorisExhaustAction extends AbstractGameAction {
    public ExitiumMaerorisExhaustAction() {
        this.actionType = ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_LONG;
        this.duration = this.startDuration;
    }

    private boolean cardToExhaust(AbstractCard c){
        return c.hasTag(CardTagsEnum.Suffering_CARD) || c instanceof Miracle;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            int statusCount = 0;

            for (int i = 0; i < AbstractDungeon.player.drawPile.size(); ) {
                AbstractCard c = AbstractDungeon.player.drawPile.group.get(i);
                if (cardToExhaust(c)) {
                    statusCount++;
                    AbstractDungeon.player.drawPile.removeCard(c);
                    AbstractDungeon.player.limbo.addToTop(c);
                    c.targetDrawScale = 0.5F;
                    c.setAngle(0, true);
                    c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                    c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                    addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.limbo));
                    addToBot(new WaitAction(0.1F));
                } else {
                    i++;
                }
            }

            for (int i = 0; i < AbstractDungeon.player.discardPile.size(); ) {
                AbstractCard c = AbstractDungeon.player.discardPile.group.get(i);
                if (cardToExhaust(c)) {
                    statusCount++;
                    AbstractDungeon.player.discardPile.removeCard(c);
                    AbstractDungeon.player.limbo.addToTop(c);
                    c.targetDrawScale = 0.5F;
                    c.setAngle(0, true);
                    c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                    c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                    addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.limbo));
                    addToBot(new WaitAction(0.1F));
                } else{
                    i++;
                }
            }

            for (int i = 0; i < AbstractDungeon.player.hand.size(); ) {
                AbstractCard c = AbstractDungeon.player.hand.group.get(i);
                if (cardToExhaust(c)) {
                    statusCount++;
                    AbstractDungeon.player.hand.removeCard(c);
                    AbstractDungeon.player.limbo.addToTop(c);
                    c.targetDrawScale = 0.5F;
                    c.setAngle(0, true);
                    c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                    c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                    addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.limbo));
                    addToBot(new WaitAction(0.1F));
                } else{
                    i++;
                }
            }

            if (statusCount <= 20) {
                addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 100 - statusCount * 5));
            }
        }
        tickDuration();
    }
}