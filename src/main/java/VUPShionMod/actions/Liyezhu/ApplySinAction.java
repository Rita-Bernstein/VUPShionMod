package VUPShionMod.actions.Liyezhu;

import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.Liyezhu.SinPower;
import VUPShionMod.powers.Liyezhu.SinfulMarkPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ApplySinAction extends AbstractGameAction {

    public ApplySinAction(AbstractCreature target, int amount) {
        this.target = target;
        this.amount = amount;
    }


    @Override
    public void update() {
        if (target == null) {
            isDone = true;
            return;
        }

        for (AbstractPower power : target.powers) {
            if (power instanceof SinfulMarkPower) {
                this.amount += power.amount;
                power.flash();
            }
        }

        AbstractPower power = new SinPower(this.target, this.amount);

        if (EnergyPanelPatches.PatchEnergyPanelField.canUseSans.get(AbstractDungeon.overlayMenu.energyPanel)) {
            power.amount = EnergyPanelPatches.PatchEnergyPanelField.sans.get(AbstractDungeon.overlayMenu.energyPanel).changeSinApply(power);
        }

        for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
            if (card instanceof AbstractVUPShionCard) {
                ((AbstractVUPShionCard) card).onApplySin();
            }
        }

        addToTop(new ApplyPowerAction(this.target, this.target, power));

        isDone = true;
    }
}
