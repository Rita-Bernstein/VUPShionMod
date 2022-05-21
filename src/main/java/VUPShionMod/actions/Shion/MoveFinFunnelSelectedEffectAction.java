package VUPShionMod.actions.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MoveFinFunnelSelectedEffectAction extends AbstractGameAction {
    private AbstractFinFunnel finFunnel;

    public MoveFinFunnelSelectedEffectAction(AbstractFinFunnel target) {
        this.finFunnel = target;
        this.duration = 0.4F;
        this.startDuration = this.duration;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            AbstractPlayerPatches.AddFields.finFunnelManager.get(AbstractDungeon.player).selectedFinFunnel = this.finFunnel;
            VUPShionMod.activeFinFunnel = this.finFunnel.id;
        }

        tickDuration();
    }
}
