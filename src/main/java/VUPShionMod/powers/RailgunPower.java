package VUPShionMod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import VUPShionMod.VUPShionMod;

public class RailgunPower extends AbstractPower {
    public static final String POWER_ID = VUPShionMod.makeID("RailgunPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public RailgunPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.loadRegion("time");
        updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        triggerDamage();
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        triggerDamage();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
    }

    private void triggerDamage(){
        if(AbstractDungeon.player.hasPower(HyperdimensionalLinksPower.POWER_ID))
            if( AbstractDungeon.player.getPower(HyperdimensionalLinksPower.POWER_ID).amount >= 20){
                addToBot(new RemoveSpecificPowerAction(this.owner,this.owner, HyperdimensionalLinksPower.POWER_ID));
                addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
