package VUPShionMod.actions.EisluRen;

import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.ui.WingShield;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class AddWingShieldAction extends AbstractGameAction {
    private int sumGain = 0;

    public AddWingShieldAction(int amount) {
        if (WingShield.getWingShield().getCount() + amount > WingShield.getWingShield().getMaxCount()) {
            this.amount = WingShield.getWingShield().getMaxCount() - WingShield.getWingShield().getCount();
        } else
            this.amount = amount;

        this.sumGain = this.amount;

        this.duration = 0.1f;
    }

    public AddWingShieldAction(int amount, int sumGain) {
        this(amount);
        this.sumGain = sumGain;
    }

    @Override
    public void update() {
        if (!WingShield.canUseWingShield() || this.amount <= 0) {
            this.isDone = true;
            return;
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0f) {
            WingShield.getWingShield().addCharge();
            this.amount--;
            this.duration = 0.1f;
        }

        if (this.amount <= 0) {
            this.isDone = true;
            WingShield.getWingShield().updateRefund();

            for (AbstractPower power : AbstractDungeon.player.powers) {
                if (power instanceof AbstractShionPower) {
                    ((AbstractShionPower) power).onAddShieldCharge(this.sumGain);
                }
            }

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof AbstractShionRelic) {
                    ((AbstractShionRelic) r).onAddShieldCharge(this.sumGain);
                }
            }
        }

    }
}
