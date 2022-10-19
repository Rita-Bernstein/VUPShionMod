package VUPShionMod.actions.Unique;

import VUPShionMod.powers.Liyezhu.FinalPrayerPower;
import VUPShionMod.powers.Shion.ConcordPower;
import VUPShionMod.powers.Shion.ReinsOfWarPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class RemoveAPlayerDebuffAction extends AbstractGameAction {

    public RemoveAPlayerDebuffAction() {

    }

    public void update() {
        ArrayList<AbstractPower> powersToRemove = new ArrayList<>();
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p.type == AbstractPower.PowerType.DEBUFF)
                powersToRemove.add(p);
        }

        if (!powersToRemove.isEmpty())
            addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    powersToRemove.get(AbstractDungeon.miscRng.random(powersToRemove.size() - 1))));

        this.tickDuration();
    }
}
