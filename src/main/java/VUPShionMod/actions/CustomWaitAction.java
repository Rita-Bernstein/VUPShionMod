package VUPShionMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class CustomWaitAction extends AbstractGameAction {
    public CustomWaitAction(float setDur) {
        this.duration = setDur;

        this.actionType = ActionType.WAIT;
    }

    public void update() {
        this.tickDuration();
    }
}