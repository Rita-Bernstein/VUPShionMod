package VUPShionMod.actions;

import VUPShionMod.relics.DimensionSplitterAria;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TriggerDimensionSplitterAction extends AbstractGameAction {
    private int extraDamage;
    private boolean isLoseHP;

    public TriggerDimensionSplitterAction() {
        this(0, false);
    }

    public TriggerDimensionSplitterAction(int extraDamage) {
        this(extraDamage, false);
    }

    public TriggerDimensionSplitterAction(boolean isLoseHP) {
        this(0, isLoseHP);
    }

    public TriggerDimensionSplitterAction(int extraDamage, boolean isLoseHP) {
        this.extraDamage = extraDamage;
        this.isLoseHP = isLoseHP;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        if (p.hasRelic(DimensionSplitterAria.ID)) {
            ((DimensionSplitterAria) p.getRelic(DimensionSplitterAria.ID)).doDamage(extraDamage,isLoseHP);
        }

        this.isDone = true;
    }
}
