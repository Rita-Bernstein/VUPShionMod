package VUPShionMod.actions;

import VUPShionMod.relics.DimensionSplitterAria;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TriggerDimensionSplitterAction extends AbstractGameAction {
    private int extraDamage;
    private boolean isLoseHP;
    private AbstractMonster target;

    public TriggerDimensionSplitterAction(int extraDamage) {
        this.extraDamage = extraDamage;
        this.isLoseHP = false;
    }


    public TriggerDimensionSplitterAction(AbstractMonster target, int extraDamage, boolean isLoseHP) {
        this.extraDamage = extraDamage;
        this.isLoseHP = isLoseHP;
        this.target = target;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (target == null)
            this.target = AbstractDungeon.getRandomMonster();;
        if (p.hasRelic(DimensionSplitterAria.ID)) {
            if (target != null) {
                if (target.isDeadOrEscaped())
                    ((DimensionSplitterAria) p.getRelic(DimensionSplitterAria.ID)).doDamage(target, extraDamage, isLoseHP);
            } else
                ((DimensionSplitterAria) p.getRelic(DimensionSplitterAria.ID)).doDamage(extraDamage, isLoseHP);
        }

        this.isDone = true;
    }
}
