package VUPShionMod.actions.Liyezhu;

import VUPShionMod.patches.AbstractPrayerPatches;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.Liyezhu.PrecastingPower;
import VUPShionMod.prayers.AbstractPrayer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Collections;

public class ApplyPrayerAction extends AbstractGameAction {
    private final AbstractPrayer prayerToApply;
    private float startingDuration;
    private boolean justStart = true;

    public ApplyPrayerAction(AbstractPrayer prayerToApply) {
        this.prayerToApply = prayerToApply;

        if (Settings.FAST_MODE) {
            this.startingDuration = 0.1F;
        } else {
            this.startingDuration = Settings.ACTION_DUR_FAST;
        }

        this.duration = this.startingDuration;
        this.actionType = ActionType.POWER;

    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.duration = 0.0F;
            this.startingDuration = 0.0F;
            this.isDone = true;
            return;
        }

        if (justStart) {
            justStart = false;

            if (!AbstractDungeon.player.isDeadOrEscaped()) {
                for (AbstractPower pow : AbstractDungeon.player.powers) {
                    if (pow instanceof AbstractShionPower)
                        ((AbstractShionPower) pow).onCreatePrayer(this.prayerToApply);
                }
            }


            if (AbstractDungeon.player.hasPower(PrecastingPower.POWER_ID)) {
                for (int i = 0; i < prayerToApply.turns; i++)
                    prayerToApply.use();
                addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, PrecastingPower.POWER_ID, 1));
                isDone = true;
                return;
            } else {
                AbstractPrayerPatches.prayers.add(this.prayerToApply);
                Collections.sort(AbstractPrayerPatches.prayers);
                this.prayerToApply.onInitialApplication();
                this.prayerToApply.flash();
            }

//                if (this.amount < 0 && (this.prayerToApply.ID.equals("Strength") || this.prayerToApply.ID.equals("Dexterity") ||
//                        this.prayerToApply.ID.equals("Focus"))) {
//                    AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.prayerToApply.name + TEXT[3]));
//
//
//                } else if (this.prayerToApply.type == AbstractPower.PowerType.BUFF) {
//                    AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.prayerToApply.name));
//
//                } else {
//
//
//                    AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.prayerToApply.name));
//                }


//                AbstractDungeon.onModifyPower();

        }

        tickDuration();
    }
}
