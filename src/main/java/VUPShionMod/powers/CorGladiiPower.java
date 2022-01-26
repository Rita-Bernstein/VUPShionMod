package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class CorGladiiPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("CorGladiiPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public CorGladiiPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        loadRegion("demonForm");
        updateDescription();
    }

    @Override
    public void reducePower(int reduceAmount) {
        if (AbstractDungeon.player.hasPower(PetalsFallPower.POWER_ID))
            return;
        super.reducePower(reduceAmount);
    }

    @Override
    public void onRemove() {
        if (AbstractDungeon.player.hasPower(PetalsFallPower.POWER_ID))
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CorGladiiPower(this.owner, this.amount)));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}