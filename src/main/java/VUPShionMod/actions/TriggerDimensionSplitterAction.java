package VUPShionMod.actions;

import VUPShionMod.relics.DimensionSplitterAria;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TriggerDimensionSplitterAction extends AbstractGameAction {

    public TriggerDimensionSplitterAction() {
    }

    @Override
    public void update() {
        AbstractPlayer p  = AbstractDungeon.player;

        if(p.hasRelic(DimensionSplitterAria.ID)){
            p.getRelic(DimensionSplitterAria.ID).atTurnStart();
        }

        this.isDone = true;
    }
}
