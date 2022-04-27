package VUPShionMod.powers.Unique;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.LoseMaxHPAction;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class DefectPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("DefectPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DefectPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        loadRegion("painfulStabs");
        updateDescription();
    }

    @Override
    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && info.type != DamageInfo.DamageType.THORNS) {
            addToBot(new LoseMaxHPAction(target, this.owner, this.amount));
        }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
