package VUPShionMod.actions;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TriggerAllFinFunnelAction extends AbstractGameAction {

    public TriggerAllFinFunnelAction() {
    }

    @Override
    public void update() {
        AbstractPlayer p  = AbstractDungeon.player;

        for(AbstractFinFunnel f : AbstractPlayerPatches.AddFields.finFunnelList.get(p)){
            f.atTurnStart();
        }
        this.isDone = true;
    }
}
