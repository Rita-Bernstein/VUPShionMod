package VUPShionMod.actions.Shion;

import VUPShionMod.powers.Shion.BleedingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class TriggerBleedingAction  extends AbstractGameAction {
    public TriggerBleedingAction(AbstractCreature target){
        this.target = target;
    }

    @Override
    public void update() {
        if(this.target == null) {
            isDone = true;
            return;
        }

        if(this.target.hasPower(BleedingPower.POWER_ID)){
            this.target.getPower(BleedingPower.POWER_ID).onSpecificTrigger();
        }

        isDone = true;
    }
}
