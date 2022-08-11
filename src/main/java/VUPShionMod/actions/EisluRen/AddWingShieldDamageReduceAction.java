package VUPShionMod.actions.EisluRen;

import VUPShionMod.ui.WingShield;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class AddWingShieldDamageReduceAction extends AbstractGameAction {
    public AddWingShieldDamageReduceAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        if (!WingShield.canUseWingShield() || this.amount <= 0) {
            this.isDone = true;
            return;
        }

        WingShield.getWingShield().increaseDamageReduce(this.amount);

        isDone = true;
    }
}
