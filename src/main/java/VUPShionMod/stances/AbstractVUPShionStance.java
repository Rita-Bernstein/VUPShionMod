package VUPShionMod.stances;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;

public abstract class AbstractVUPShionStance extends AbstractStance {
    public AbstractVUPShionStance(){
    }


    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target){}

    public void onVictory(){}

    public int onHeal(int healAmount){
        return healAmount;
    }


    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
}
