package VUPShionMod.actions.Common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class CustomWaitAction extends AbstractGameAction {
    public CustomWaitAction(float setDur) {
        this.duration = setDur;

        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        this.tickDuration();
    }
}