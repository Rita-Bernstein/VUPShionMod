package VUPShionMod.actions.Common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class DecreaseHPAction extends AbstractGameAction {

    public DecreaseHPAction(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.DAMAGE;

    }


    public void update() {
        this.target.currentHealth -= this.amount;
        this.target.healthBarUpdatedEvent();
        this.target.damage(new DamageInfo((AbstractCreature)null, 0, DamageInfo.DamageType.HP_LOSS));
        isDone = true;
    }
}


