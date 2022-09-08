package VUPShionMod.actions.Liyezhu;

import VUPShionMod.patches.EnergyPanelPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoseSansAction extends AbstractGameAction {
    public LoseSansAction(int amount){
        this.amount = amount;
    }

    @Override
    public void update() {
        if (EnergyPanelPatches.PatchEnergyPanelField.canUseSans.get(AbstractDungeon.overlayMenu.energyPanel)) {
            EnergyPanelPatches.PatchEnergyPanelField.sans.get(AbstractDungeon.overlayMenu.energyPanel).loseSan(this.amount);
        }
        isDone =true;
    }
}
