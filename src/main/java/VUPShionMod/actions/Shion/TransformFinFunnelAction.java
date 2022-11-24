package VUPShionMod.actions.Shion;

import VUPShionMod.finfunnels.*;
import VUPShionMod.patches.AbstractPlayerPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class TransformFinFunnelAction extends AbstractGameAction {
    private AbstractFinFunnel finFunnel;


    public TransformFinFunnelAction(AbstractFinFunnel finFunnel) {
        this.finFunnel = finFunnel;
    }

    @Override
    public void update() {
        if (finFunnel.index < 0) {
            this.isDone = true;
            return;
        }


        ArrayList<AbstractFinFunnel> finFunnelArrayList = new ArrayList<>();

        if (!DissectingFinFunnel.ID.equals(this.finFunnel.id)) {
            finFunnelArrayList.add(new DissectingFinFunnel(this.finFunnel.getLevel(),finFunnel.index));
        }
        if (!GravityFinFunnel.ID.equals(this.finFunnel.id)) {
            finFunnelArrayList.add(new GravityFinFunnel(this.finFunnel.getLevel(),finFunnel.index));
        }
        if (!InvestigationFinFunnel.ID.equals(this.finFunnel.id)) {
            finFunnelArrayList.add(new InvestigationFinFunnel(this.finFunnel.getLevel(),finFunnel.index));
        }
        if (!MatrixFinFunnel.ID.equals(this.finFunnel.id)) {
            finFunnelArrayList.add(new MatrixFinFunnel(this.finFunnel.getLevel(),finFunnel.index));
        }
        if (!PursuitFinFunnel.ID.equals(this.finFunnel.id)) {
            finFunnelArrayList.add(new PursuitFinFunnel(this.finFunnel.getLevel(),finFunnel.index));
        }


        AbstractFinFunnel finFunnelToGain = finFunnelArrayList.get(AbstractDungeon.cardRandomRng.random(finFunnelArrayList.size() - 1));
        AbstractPlayerPatches.AddFields.finFunnelManager.get(AbstractDungeon.player).selectedFinFunnel = finFunnelToGain;
        finFunnelToGain.updateMinamiPos();
        FinFunnelManager.getFinFunnelList().set(finFunnelToGain.index, finFunnelToGain);


        isDone = true;
    }
}
