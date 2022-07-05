package VUPShionMod.actions.Shion;

import VUPShionMod.util.FinFunnelCharge;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class GainFinFunnelChargeAction extends AbstractGameAction {
    public GainFinFunnelChargeAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        FinFunnelCharge.getFinFunnelCharge().addCharge(this.amount);
        isDone = true;
    }
}
