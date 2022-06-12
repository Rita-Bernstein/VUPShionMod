package VUPShionMod.actions.Wangchuan;

import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.OppressiveSwordPower;
import VUPShionMod.relics.Wangchuan.MagiaSwordRed;
import VUPShionMod.relics.Wangchuan.MagiaSwordRuby;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class LoseCorGladiiAction extends AbstractGameAction {
    private boolean removeAll = false;
    private float percentage = 0.0f;

    public LoseCorGladiiAction(boolean removeAll) {
        this.removeAll = removeAll;
    }

    public LoseCorGladiiAction(int amount) {
        this.amount = amount;
    }


    public LoseCorGladiiAction(float percentage) {
        this.percentage = percentage;
    }

    @Override
    public void update() {
        if (!AbstractDungeon.player.hasPower(OppressiveSwordPower.POWER_ID)) {
            if (removeAll) {
                addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, CorGladiiPower.POWER_ID));
            } else {
                if (this.amount > 0)
                    addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, CorGladiiPower.POWER_ID, this.amount));
                else if (this.percentage > 0.0f) {
                    if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
                        addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, CorGladiiPower.POWER_ID,
                                (int) Math.floor(AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount * this.percentage)));
                }
            }
        }
        isDone = true;
    }


}
