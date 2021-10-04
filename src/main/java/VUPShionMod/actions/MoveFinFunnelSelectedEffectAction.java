package VUPShionMod.actions;

import VUPShionMod.VUPShionMod;
import VUPShionMod.effects.FinFunnelSelectedEffect;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.List;

public class MoveFinFunnelSelectedEffectAction extends AbstractGameAction {
    private float startX;
    private float startY;
    private float targetX;
    private float targetY;
    private AbstractFinFunnel finFunnel;
    private FinFunnelSelectedEffect effect;

    public MoveFinFunnelSelectedEffectAction(FinFunnelSelectedEffect effect, AbstractFinFunnel target) {
        this.startX = effect.cX;
        this.startY = effect.cY;
        this.finFunnel = target;
        this.duration = 0.4F;
        this.startDuration = this.duration;
        this.effect = effect;
        VUPShionMod.activeFinFunnel = AbstractPlayerPatches.AddFields.finFunnelList.get(this).indexOf(this.finFunnel);
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            this.targetX = this.finFunnel.cX;
            this.targetY = this.finFunnel.cY;
        }
        effect.cX = Interpolation.exp10Out.apply(this.startX, this.targetX, (this.startDuration - this.duration) / this.startDuration);
        effect.cY = Interpolation.exp10Out.apply(this.startY, this.targetY, (this.startDuration - this.duration) / this.startDuration);
        this.tickDuration();
    }
}
