package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class StiffnessPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("StiffnessPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public StiffnessPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        loadRegion("demonForm");
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        if (AbstractDungeon.player.hasPower(PoisePower.POWER_ID)) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, StiffnessPower.POWER_ID));
            return;
        }

        if (this.amount >= 4) {
            addToBot(new PressEndTurnButtonAction());
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        if (AbstractDungeon.player.hasPower(PoisePower.POWER_ID))
            return;
        super.stackPower(stackAmount);
        if (this.amount >= 4) {
            addToBot(new PressEndTurnButtonAction());
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}