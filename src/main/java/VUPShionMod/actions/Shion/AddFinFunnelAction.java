package VUPShionMod.actions.Shion;

import VUPShionMod.finfunnels.*;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.relics.Shion.ConcordCompanion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class AddFinFunnelAction extends AbstractGameAction {
    private boolean isRandom = false;
    private String forceId = "";
    private int newLevel;

    public AddFinFunnelAction(int newLevel) {
        isRandom = true;
        this.newLevel = newLevel;
    }

    public AddFinFunnelAction(String forceId, int newLevel) {
        this.forceId = forceId;
        this.newLevel = newLevel;
    }


    @Override
    public void update() {
        int maxSize = 4;
        if (AbstractDungeon.player.hasRelic(ConcordCompanion.ID))
            maxSize += 2;

        if (FinFunnelManager.getFinFunnelList().size() >= maxSize) {
            isDone = true;
            return;
        }


        ArrayList<AbstractFinFunnel> finFunnelArrayList = new ArrayList<>();

        finFunnelArrayList.add(new DissectingFinFunnel(this.newLevel, FinFunnelManager.getFinFunnelList().size()));
        finFunnelArrayList.add(new GravityFinFunnel(this.newLevel, FinFunnelManager.getFinFunnelList().size()));
        finFunnelArrayList.add(new InvestigationFinFunnel(this.newLevel, FinFunnelManager.getFinFunnelList().size()));
        finFunnelArrayList.add(new MatrixFinFunnel(this.newLevel, FinFunnelManager.getFinFunnelList().size()));
        finFunnelArrayList.add(new PursuitFinFunnel(this.newLevel, FinFunnelManager.getFinFunnelList().size()));

        AbstractFinFunnel finFunnelToGain = null;

        if (this.isRandom) {
            finFunnelToGain = finFunnelArrayList.get(AbstractDungeon.cardRandomRng.random(finFunnelArrayList.size() - 1));
        } else {
            for (AbstractFinFunnel f : finFunnelArrayList) {
                if (f.id.equals(forceId)) {
                    finFunnelToGain = f;
                    break;
                }
            }
        }

        if (finFunnelToGain != null) {
            if(FinFunnelManager.getFinFunnelList().isEmpty()){
                AbstractPlayerPatches.AddFields.finFunnelManager.get(AbstractDungeon.player).selectedFinFunnel = finFunnelToGain;
            }

            finFunnelToGain.updateMinamiPos();
            FinFunnelManager.getFinFunnelList().add(finFunnelToGain);
        }

        finFunnelArrayList.clear();

        isDone = true;
    }
}
