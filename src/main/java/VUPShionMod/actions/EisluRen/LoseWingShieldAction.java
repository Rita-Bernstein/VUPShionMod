package VUPShionMod.actions.EisluRen;

import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.EisluRen.SupportTimeDrivenPower;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.ui.WingShield;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class LoseWingShieldAction extends AbstractGameAction {
    private int effect ;
    public LoseWingShieldAction(int amount) {
        if (WingShield.getWingShield().getCount() - amount < 0) {
            this.amount = WingShield.getWingShield().getCount();
        } else
            this.amount = amount;

        this.effect = this.amount;
        this.duration = 0.1f;

        this.actionType = ActionType.DAMAGE;
    }

    @Override
    public void update() {
        if (!WingShield.canUseWingShield() || this.amount <= 0) {
            isDone = true;
            return;
        }

        if(AbstractDungeon.player.hasPower(SupportTimeDrivenPower.POWER_ID)){
            isDone = true;
            return;
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0f) {
            WingShield.getWingShield().loseCharge();
            this.amount--;
            this.duration = 0.1f;
        }

        if (this.amount <= 0) {
            this.isDone = true;
            WingShield.getWingShield().updateRefund();

            for (AbstractPower power : AbstractDungeon.player.powers) {
                if (power instanceof AbstractShionPower) {
                    ((AbstractShionPower) power).onLoseShieldCharge(this.effect);
                }
            }

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof AbstractShionRelic) {
                    ((AbstractShionRelic) r).onLoseShieldCharge(this.effect);
                }
            }
        }

    }
}
