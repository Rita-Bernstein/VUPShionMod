package VUPShionMod.actions.Unique;

import VUPShionMod.powers.Monster.BossShion.SavePowerPower;
import VUPShionMod.powers.Monster.BossShion.SystemHackPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;



public class SavePlayerPowersAction extends AbstractGameAction {
    private SavePowerPower powerToSave;

    public SavePlayerPowersAction(SavePowerPower powerToSave) {
        this.powerToSave = powerToSave;
    }

    @Override
    public void update() {
        for(AbstractPower p : AbstractDungeon.player.powers){
            if(!(p instanceof InvisiblePower) && !(p instanceof SavePowerPower)){
                powerToSave.playerPowersToSave.add(p);
//                addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,p));
            }
        }

        AbstractDungeon.player.powers.removeAll(powerToSave.playerPowersToSave);
        isDone = true;
    }
}
