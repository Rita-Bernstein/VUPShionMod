package VUPShionMod.powers.Shion;

import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import VUPShionMod.VUPShionMod;

public class LockOnPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("LockOnPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public LockOnPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.setImage("Lock84.png", "Lock32.png");
        updateDescription();
        this.type = PowerType.DEBUFF;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS)
            return damageAmount + this.amount;

        return super.onAttackedToChangeDamage(info, damageAmount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(!isPlayer)
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,LockOnPower.POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
