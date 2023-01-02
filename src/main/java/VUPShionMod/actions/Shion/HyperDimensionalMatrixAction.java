package VUPShionMod.actions.Shion;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.Shion.HyperdimensionalLinksPower;
import VUPShionMod.util.SaveHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HyperDimensionalMatrixAction extends AbstractGameAction {
    private final int magicNumber;

    public HyperDimensionalMatrixAction(int magicNumber) {
        this.magicNumber = magicNumber;
        this.duration = 0.4F;
        this.startDuration = this.duration;
    }

    @Override
    public void update() {
        this.amount = 0;
        if (AbstractDungeon.player.hasPower(HyperdimensionalLinksPower.POWER_ID)) {
            this.amount = AbstractDungeon.player.getPower(HyperdimensionalLinksPower.POWER_ID).amount / this.magicNumber;
        }

        System.out.println(this.amount);

        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractFinFunnel finFunnel = FinFunnelManager.getSelectedFinFunnel();
                if (finFunnel != null) {
                    finFunnel.levelForCombat += HyperDimensionalMatrixAction.this.amount;
                    if (finFunnel.levelForCombat - 15 > finFunnel.level)
                        finFunnel.levelForCombat = finFunnel.level + 15;

                    finFunnel.updateDescription();
                }
                isDone = true;
            }
        });

        addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, HyperdimensionalLinksPower.POWER_ID));
        isDone = true;
    }
}
