package VUPShionMod.actions.Unique;

import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SummonMinionAction extends AbstractGameAction {
    private final AbstractMonster owner;
    private final AbstractMonster m;
    private final int index;
    private float starterX = 0.0f;
    private float starterY = 0.0f;


    public SummonMinionAction(AbstractMonster owner, AbstractMonster m, int index) {
        if (Settings.FAST_MODE)
            this.startDuration = Settings.ACTION_DUR_FAST;
        else
            this.startDuration = Settings.ACTION_DUR_LONG;

        this.duration = this.startDuration;

        this.owner = owner;
        this.m = m;
        this.index = index;

        for (AbstractRelic r : AbstractDungeon.player.relics) {
            r.onSpawnMonster(this.m);
        }

        this.actionType = ActionType.DAMAGE;

        this.starterX = this.m.hb.cX - this.owner.hb.cX;
        this.starterY = this.m.hb.y - this.owner.hb.y;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            this.m.animX = starterX;
            this.m.animY = starterY;

            this.m.init();
            this.m.applyPowers();
            this.m.createIntent();
            (AbstractDungeon.getCurrRoom()).monsters.addMonster(index, this.m);

            if (ModHelper.isModEnabled("Lethality")) {
                addToBot(new ApplyPowerAction(this.m, this.m, new StrengthPower(this.m, 3), 3));
            }
            if (ModHelper.isModEnabled("Time Dilation")) {
                addToBot(new ApplyPowerAction(this.m, this.m, new SlowPower(this.m, 0)));
            }
        }

        tickDuration();

        if (this.isDone) {
            this.m.animX = 0.0F;
            this.m.showHealthBar();
            this.m.usePreBattleAction();
        } else {
            this.m.animX = Interpolation.fade.apply(0.0F, starterX, this.duration);
            this.m.animY = Interpolation.fade.apply(0.0F, starterY, this.duration);
        }

    }
}
