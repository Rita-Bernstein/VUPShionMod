package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.ApplyPowerToAllEnemyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.function.Supplier;

public class OculusMortis2Power extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("OculusMortis2Power");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public OculusMortis2Power(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        this.amount = amount;
        this.loadRegion("juggernaut");
        updateDescription();
        this.isTurnBased = true;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public void atStartOfTurn() {
            Supplier<AbstractPower> powerToApply = () -> new VulnerablePower(null, this.amount, false);
            addToBot(new ApplyPowerToAllEnemyAction(powerToApply));
    }
}
