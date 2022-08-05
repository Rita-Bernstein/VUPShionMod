package VUPShionMod.actions.EisluRen;

import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.ui.WingShield;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AddRefundChargeAction extends AbstractGameAction {
    public AddRefundChargeAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        if (!WingShield.canUseWingShield() || this.amount <= 0) {
            this.isDone = true;
            return;
        }
        WingShield.getWingShield().addRefundCount(this.amount);

        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof AbstractShionPower)
                ((AbstractShionPower) p).onAddShieldRefund(this.amount);
        }

        isDone = true;
    }
}
