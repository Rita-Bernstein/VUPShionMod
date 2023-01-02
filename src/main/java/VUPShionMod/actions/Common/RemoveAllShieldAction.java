package VUPShionMod.actions.Common;

import VUPShionMod.msic.Shield;
import VUPShionMod.patches.ShieldPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class RemoveAllShieldAction extends AbstractGameAction {
    private int amount;

    public RemoveAllShieldAction(AbstractCreature target) {
        this.amount = amount;
        this.target = target;
        this.actionType = ActionType.BLOCK;
        this.duration = 0.25F;
        this.startDuration = 0.25F;
    }

    public RemoveAllShieldAction(AbstractCreature target, boolean superFast) {
        this(target);
        if (superFast)
            this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
    }


    @Override
    public void update() {
        if (!this.target.isDying && !this.target.isDead && this.duration == this.startDuration) {
            Shield.getShield(this.target).loseBlock(Shield.getShield(this.target).getCurrentShield());
        } else {
            isDone = true;
            return;
        }
        tickDuration();
    }
}
