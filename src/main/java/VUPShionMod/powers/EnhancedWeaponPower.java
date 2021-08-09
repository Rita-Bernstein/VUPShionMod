package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnhancedWeaponPower extends AbstractPower {
    public static final String POWER_ID = VUPShionMod.makeID("EnhancedWeaponPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public EnhancedWeaponPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.loadRegion("time");
        updateDescription();
    }

//    @Override
//    public void atEndOfTurn(boolean isPlayer) {
//        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,POWER_ID));
//    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount) ;
    }
}
