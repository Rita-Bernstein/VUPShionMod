package VUPShionMod.actions.Common;

import VUPShionMod.patches.ShieldPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class GainShieldAction extends AbstractGameAction {
    private int amount;

    public GainShieldAction(AbstractCreature target, int amount) {
        this.amount = amount;
        this.target = target;
        this.actionType = AbstractGameAction.ActionType.BLOCK;
        this.duration = 0.25F;
        this.startDuration = 0.25F;
    }

    public GainShieldAction(AbstractCreature target, int amount, boolean superFast) {
        this(target, amount);
        if (superFast)
            this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
    }


    @Override
    public void update() {
        if (amount > 0)
            if (!this.target.isDying && !this.target.isDead && this.duration == this.startDuration) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
                ShieldPatches.AddFields.shield.get(this.target).addBlock(this.amount);
            } else {
                isDone = true;
                return;
            }
        tickDuration();
    }
}
