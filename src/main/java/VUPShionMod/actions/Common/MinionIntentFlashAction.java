package VUPShionMod.actions.Common;

import VUPShionMod.minions.AbstractPlayerMinion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MinionIntentFlashAction extends AbstractGameAction {
    private final AbstractPlayerMinion m;

    public MinionIntentFlashAction(AbstractPlayerMinion m) {
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_MED;
        } else {
            this.startDuration = Settings.ACTION_DUR_XLONG;
        }

        this.duration = this.startDuration;
        this.m = m;
        this.actionType = AbstractGameAction.ActionType.WAIT;
    }


    public void update() {
        if (this.duration == this.startDuration) {
            this.m.flashIntent();
        }

        tickDuration();
    }
}