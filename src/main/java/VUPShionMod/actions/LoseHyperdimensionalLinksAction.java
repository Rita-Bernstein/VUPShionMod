package VUPShionMod.actions;

import VUPShionMod.powers.HolyCoffinReleasePower;
import VUPShionMod.powers.HolyCoffinSinkingSpiritPower;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LoseHyperdimensionalLinksAction extends AbstractGameAction {
    private int amount;
    private boolean removeAll;

    public LoseHyperdimensionalLinksAction(int amount) {
        this.amount = amount;
        this.removeAll = false;
    }

    public LoseHyperdimensionalLinksAction(boolean removeAll) {
        this.amount = 0;
        this.removeAll = removeAll;
    }

    public void update() {
//        if (!AbstractDungeon.player.hasPower(HolyCoffinReleasePower.POWER_ID))
            if (removeAll)
                addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, HyperdimensionalLinksPower.POWER_ID));
            else
                addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, HyperdimensionalLinksPower.POWER_ID, amount));

        AbstractPower p = AbstractDungeon.player.getPower(HolyCoffinSinkingSpiritPower.POWER_ID);
        if (p != null)
            addToBot(new GainBlockAction(AbstractDungeon.player, p.amount));

        this.isDone = true;
    }
}