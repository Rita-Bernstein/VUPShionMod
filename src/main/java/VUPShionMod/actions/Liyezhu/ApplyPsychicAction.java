package VUPShionMod.actions.Liyezhu;

import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.PsychicPower;
import VUPShionMod.powers.SinPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ApplyPsychicAction extends AbstractGameAction {

    public ApplyPsychicAction(AbstractCreature target, int amount) {
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

        AbstractPower power = new PsychicPower(this.source, this.amount);


        addToTop(new ApplyPowerAction(this.source, this.source, power));

        isDone = true;
    }
}
