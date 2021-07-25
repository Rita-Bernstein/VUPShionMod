package VUPShionMod.actions;

import VUPShionMod.effects.FinFunnelSelectedEffect;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

/**
 * 移动浮游炮的动作
 * @author Temple9
 * @since 2021-7-22
 */
public class MoveFinFunnelAction extends AbstractGameAction {
    private float startX;
    private float startY;
    private float targetX;
    private float targetY;
    private AbstractFinFunnel funnel;
    private boolean moveEffect;

    public MoveFinFunnelAction(AbstractFinFunnel funnel, float targetX, float targetY) {
        this(funnel, targetX, targetY, false);
    }

    public MoveFinFunnelAction(AbstractFinFunnel funnel, float targetX, float targetY, boolean moveEffect) {
        this.funnel = funnel;
        this.startX = funnel.cX;
        this.startY = funnel.cY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.duration = 0.3F;
        this.startDuration = this.duration;
        this.moveEffect = moveEffect;
    }

    @Override
    public void update() {
        funnel.cX = Interpolation.exp10Out.apply(this.startX, this.targetX, (this.startDuration - this.duration) / this.startDuration);
        funnel.cY = Interpolation.exp10Out.apply(this.startY, this.targetY, (this.startDuration - this.duration) / this.startDuration);
        funnel.hb.move(funnel.cX, funnel.cY);
        this.tickDuration();
        if (isDone && moveEffect) {
            addToBot(new MoveFinFunnelSelectedEffectAction(FinFunnelSelectedEffect.instance, this.funnel));
        }
    }
}
