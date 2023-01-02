package VUPShionMod.actions.EisluRen;

import VUPShionMod.minions.AbstractPlayerMinion;
import VUPShionMod.minions.ElfMinion;
import VUPShionMod.minions.MinionGroup;
import VUPShionMod.patches.AbstractPlayerPatches;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SummonElfAction extends AbstractGameAction {
    private final ElfMinion minion;

    private float starterX = 0.0f;
    private float starterY = 0.0f;

    public SummonElfAction(ElfMinion minion) {
        if (Settings.FAST_MODE)
            this.startDuration = Settings.ACTION_DUR_FAST;
        else
            this.startDuration = Settings.ACTION_DUR_LONG;
        this.duration = this.startDuration;


        this.minion = minion;

        this.starterX = this.minion.hb.cX - AbstractDungeon.player.hb.cX;
        this.starterY = this.minion.hb.y - AbstractDungeon.player.hb.y;
    }

    @Override
    public void update() {

        if (this.duration == this.startDuration) {
            this.minion.animX = starterX;
            this.minion.animY = starterY;


            if (!MinionGroup.getMinions().isEmpty())
                for (AbstractPlayerMinion minion : MinionGroup.getMinions()) {
                    if (minion instanceof ElfMinion) {
                        ((ElfMinion) minion).summonElf(this.minion);
                        isDone = true;
                        return;
                    }
                }


            this.minion.init();
            this.minion.applyPowers();
            this.minion.createIntent();
            AbstractPlayerPatches.AddFields.playerMinions.get(AbstractDungeon.player).addMinion(this.minion);
            isDone = true;
        }


        tickDuration();

        if (this.isDone) {
            this.minion.animX = 0.0F;
            this.minion.showHealthBar();
            this.minion.usePreBattleAction();
        } else {
            this.minion.animX = Interpolation.fade.apply(0.0F, starterX, this.duration);
            this.minion.animY = Interpolation.fade.apply(0.0F, starterY, this.duration);
        }

    }
}
