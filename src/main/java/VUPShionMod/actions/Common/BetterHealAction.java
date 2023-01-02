package VUPShionMod.actions.Common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

public class BetterHealAction extends AbstractGameAction {
    private boolean showEffect = true;

    public BetterHealAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.setValues(target, source, amount);
        this.startDuration = this.duration;
        if (Settings.FAST_MODE) {
            this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        }

        this.actionType = ActionType.HEAL;
    }

    public BetterHealAction(AbstractCreature target, AbstractCreature source, int amount, boolean showEffect) {
        this.setValues(target, source, amount);
        this.startDuration = this.duration;
        if (Settings.FAST_MODE) {
            this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        }

        this.showEffect = showEffect;
        this.actionType = ActionType.HEAL;
    }

    public BetterHealAction(AbstractCreature target, AbstractCreature source, int amount, float duration) {
        this(target, source, amount);
        this.duration = this.startDuration = duration;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            this.target.heal(this.amount, this.showEffect);
        }

        this.tickDuration();
    }
}
