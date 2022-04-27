package VUPShionMod.actions.Liyezhu;

import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.SinPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ApplySinAction extends AbstractGameAction {

    public ApplySinAction(AbstractCreature target, int amount) {
        this.source = target;
        this.amount = amount;
    }


    @Override
    public void update() {
//        for (AbstractPower power : this.source.powers) {
//            if (power instanceof AbstractIrisRaphaelPower) {
//                this.amount = ((AbstractIrisRaphaelPower) power).changeHeatApplyAmount(this.amount);
//            }
//        }

        AbstractPower power = new SinPower(this.source, this.amount);

        if (EnergyPanelPatches.PatchEnergyPanelField.canUseSans.get(AbstractDungeon.overlayMenu.energyPanel)) {
            power.amount = EnergyPanelPatches.PatchEnergyPanelField.sans.get(AbstractDungeon.overlayMenu.energyPanel).changeSinApply(power);
        }

        addToTop(new ApplyPowerAction(this.source, this.source, power));

        isDone = true;
    }
}
