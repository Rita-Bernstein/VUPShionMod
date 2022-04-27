package VUPShionMod.actions.Liyezhu;

import VUPShionMod.powers.Liyezhu.SinPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlayTheEvilAction extends AbstractGameAction {
    private int cutoff;

    public FlayTheEvilAction(AbstractCreature target, int cutoff) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.source = null;
        this.target = target;
        this.cutoff = cutoff;
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST)
            if (this.target.hasPower(SinPower.POWER_ID) && this.target instanceof com.megacrit.cardcrawl.monsters.AbstractMonster) {
                AbstractMonster m = (AbstractMonster) this.target;
                if (m.type != AbstractMonster.EnemyType.BOSS && m.getPower(SinPower.POWER_ID).amount >= this.cutoff)
                    addToTop(new InstantKillAction(this.target));
            }


        this.isDone = true;
    }
}