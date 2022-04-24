package VUPShionMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MarchDrawCardAction extends AbstractGameAction {
    private AbstractCard card;
    private  int healAmount;
    public MarchDrawCardAction(AbstractCard card,int healAmount){
        this.card = card;
        this.healAmount = healAmount;
    }

    @Override
    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards){
            if(c.type == AbstractCard.CardType.ATTACK){
                addToBot(new AttackDamageRandomEnemyAction(this.card, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
            if(c.type == AbstractCard.CardType.SKILL){
                addToTop(new HealAction(AbstractDungeon.player,AbstractDungeon.player,this.healAmount ));
            }

            if(c.type == AbstractCard.CardType.POWER){
                addToBot(new DrawCardAction(1));
            }
        }
        isDone = true;
    }
}
