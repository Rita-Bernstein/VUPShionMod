package VUPShionMod.actions.Unique;

import VUPShionMod.vfx.Monster.Boss.VersusPortraitEffect;
import VUPShionMod.vfx.Monster.Boss.VersusLineEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class VersusEffectAction extends AbstractGameAction {
    private boolean portraitTriggered = false;
    private final String IDp2;

    public VersusEffectAction(String IDp2) {
        this.duration = 5.0f;
        this.IDp2 = IDp2;
    }

    @Override
    public void update() {
        if (this.duration == 5.0f) {
            AbstractDungeon.effectsQueue.add(new VersusLineEffect(IDp2));
        }

        if (this.duration <= 2.8f && !portraitTriggered) {
            AbstractDungeon.effectsQueue.add(new VersusPortraitEffect(IDp2));
            portraitTriggered = true;
        }

        tickDuration();
    }


}
