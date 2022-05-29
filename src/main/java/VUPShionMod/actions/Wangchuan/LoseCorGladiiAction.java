package VUPShionMod.actions.Wangchuan;

import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.OppressiveSwordPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoseCorGladiiAction extends AbstractGameAction {
    private boolean removeAll = false;

    public LoseCorGladiiAction(boolean removeAll) {
        this.removeAll = removeAll;
    }

    public LoseCorGladiiAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        if (!AbstractDungeon.player.hasPower(OppressiveSwordPower.POWER_ID))
            if (removeAll) {
                addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, CorGladiiPower.POWER_ID));
            } else {
                if (this.amount > 0)
                    addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, CorGladiiPower.POWER_ID, this.amount));
            }
        isDone = true;
    }

}
