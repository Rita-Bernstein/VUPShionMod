package VUPShionMod.powers.Monster.RitaShop;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DefenceMonsterPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(DefenceMonsterPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DefenceMonsterPower(AbstractCreature owner, int amount) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        if (this.amount < 15)
            this.amount = 15;
        updateDescription();
        loadRegion("armor");
        this.priority = 10;
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        float damageFinal = (float) Math.ceil(damage * this.amount / 255.0f);
        if (damageType == DamageInfo.DamageType.NORMAL) {
            return (float) Math.ceil(damageFinal / 10.0f) * 10.0f;
        }
        return damageFinal;
    }


    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
