package VUPShionMod.actions.Shion;

import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.util.FinFunnelCharge;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GainFinFunnelChargeAction extends AbstractGameAction {
    public GainFinFunnelChargeAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        if (EnergyPanelPatches.PatchEnergyPanelField.canUseFunnelCharger.get(AbstractDungeon.overlayMenu.energyPanel))
            FinFunnelCharge.getFinFunnelCharge().addCharge(this.amount);
        isDone = true;
    }
}
