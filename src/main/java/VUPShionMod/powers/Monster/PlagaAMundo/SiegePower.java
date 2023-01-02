package VUPShionMod.powers.Monster.PlagaAMundo;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Unique.ExhaustRarityCardAction;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class SiegePower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(SiegePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SiegePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        loadRegion("painfulStabs");
        updateDescription();
        this.type = PowerType.DEBUFF;
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new ApplyPowerAction(this.owner, this.owner, new VulnerablePower(this.owner, this.amount, false)));
        addToBot(new ApplyPowerAction(this.owner, this.owner, new FrailPower(this.owner, this.amount, false)));
        addToBot(new ApplyPowerAction(this.owner, this.owner, new WeakPower(this.owner, this.amount, false)));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
