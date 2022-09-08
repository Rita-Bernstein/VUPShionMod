package VUPShionMod.actions.Liyezhu;

import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.Event.AbyssalCrux;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AddSansAction extends AbstractGameAction {
    public AddSansAction(int amount){
        this.amount = amount;
    }

    @Override
    public void update() {
        if (!AbstractDungeon.player.hasRelic(AbyssalCrux.ID))
        if (EnergyPanelPatches.PatchEnergyPanelField.canUseSans.get(AbstractDungeon.overlayMenu.energyPanel)) {
            EnergyPanelPatches.PatchEnergyPanelField.sans.get(AbstractDungeon.overlayMenu.energyPanel).addSan(this.amount);
        }
        isDone =true;
    }
}
