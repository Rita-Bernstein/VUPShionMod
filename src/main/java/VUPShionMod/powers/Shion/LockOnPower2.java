package VUPShionMod.powers.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class LockOnPower2 extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(LockOnPower2.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public LockOnPower2(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        loadShionRegion("LockOnPower");
        updateDescription();
        this.type = PowerType.DEBUFF;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        addToBot(new GainHyperdimensionalLinksAction(this.amount));
        return damageAmount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer)
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, LockOnPower2.POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
