package VUPShionMod.actions.Wangchuan;


import VUPShionMod.powers.Wangchuan.PoisePower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ApplyStiffnessAction extends AbstractGameAction {
    public ApplyStiffnessAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        for(AbstractPower p : AbstractDungeon.player.powers){
            if(p instanceof PoisePower)
                this.amount -= p.amount;
        }

        if(this.amount <=0) {
            isDone = true;
            return;
        }

        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StiffnessPower(AbstractDungeon.player, this.amount)));
        isDone = true;
    }
}
