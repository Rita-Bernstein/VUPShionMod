package VUPShionMod.actions.EisluRen;

import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.EisluRen.SupportTimeDrivenPower;
import VUPShionMod.ui.WingShield;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoseWingShieldAction extends AbstractGameAction {
    public LoseWingShieldAction(int amount) {
        if (WingShield.getWingShield().getCount() - amount < 0) {
            this.amount = WingShield.getWingShield().getCount();
        } else
            this.amount = amount;

        this.duration = 0.1f;
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
        }

    }
}
