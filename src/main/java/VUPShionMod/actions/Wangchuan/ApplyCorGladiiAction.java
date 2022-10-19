package VUPShionMod.actions.Wangchuan;


import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.PoisePower;
import VUPShionMod.powers.Wangchuan.PreExecutionPower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import VUPShionMod.relics.Wangchuan.MagiaSwordRed;
import VUPShionMod.relics.Wangchuan.MagiaSwordRuby;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ApplyCorGladiiAction extends AbstractGameAction {
    public ApplyCorGladiiAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {

        AbstractPower power = AbstractDungeon.player.getPower(PreExecutionPower.POWER_ID);
        if (power != null) {
            power.flash();
            this.amount += this.amount * power.amount;
        }


        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CorGladiiPower(AbstractDungeon.player, this.amount)));
        isDone = true;
    }
}
