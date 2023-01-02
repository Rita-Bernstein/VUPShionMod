package VUPShionMod.powers.Monster.BossShion;

import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public abstract class SavePowerPower extends AbstractShionPower {
    public ArrayList<AbstractPower> playerPowersToSave = new ArrayList<>();
    public ArrayList<AbstractPower> playerPowers = new ArrayList<>();
    public boolean justApplied = true;
    public boolean endingEffect = false;

    public SavePowerPower() {

    }
}
