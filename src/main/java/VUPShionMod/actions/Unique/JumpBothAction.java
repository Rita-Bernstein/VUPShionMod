package VUPShionMod.actions.Unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class JumpBothAction extends AbstractGameAction {
    private boolean called = false;
    private boolean called2 = false;

    public JumpBothAction(AbstractCreature target) {
        this.target = target;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        if (!this.called) {
            this.target.useJumpAnimation();
            this.called = true;
        }

        if (!this.called2 && this.duration < 0.2f) {
            AbstractDungeon.player.useJumpAnimation();
            this.called2 = true;
        }

        this.tickDuration();
    }
}
