package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.GainShieldAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CrimsonDelugePower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(CrimsonDelugePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CrimsonDelugePower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        loadRegion("barricade");
        updateDescription();
    }

    @Override
    public int onHeal(int healAmount) {
        addToTop(new GainShieldAction(this.owner, this.owner.maxHealth - (this.owner.currentHealth + healAmount)));
        return super.onHeal(healAmount);
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
