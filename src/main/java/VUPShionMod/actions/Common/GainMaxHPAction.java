package VUPShionMod.actions.Common;

import VUPShionMod.minions.ElfMinion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class GainMaxHPAction extends AbstractGameAction {
    private boolean showEffect;

    public GainMaxHPAction(AbstractCreature target, int amount) {
        this(target, amount, true);
    }

    public GainMaxHPAction(AbstractCreature target, int amount, boolean showEffect) {
        this.target = target;
        this.amount = amount;
        this.showEffect = showEffect;
    }

    @Override
    public void update() {
        if (target instanceof ElfMinion) {
            ((ElfMinion) target).increaseMaxHP(amount);
        } else {
            target.increaseMaxHp(amount, this.showEffect);
        }
        isDone = true;
    }
}
