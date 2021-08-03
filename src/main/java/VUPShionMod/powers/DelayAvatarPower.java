package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.kuroisu.DelayAvatar;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DelayAvatarPower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = VUPShionMod.makeID("DelayAvatarPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int damage;

    public DelayAvatarPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.damage = amount;
        this.loadRegion("time");
        updateDescription();
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        if (damage < this.amount) {
            this.amount -= (int)damage;
        } else {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, DelayAvatarPower.POWER_ID));
            addToBot(new ApplyPowerAction(this.owner, this.owner, new DelayAvatarAttackPower(this.owner, this.damage)));
        }

        return 0;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount, this.damage);
    }

    @Override
    public AbstractPower makeCopy() {
        return new DelayAvatarPower(this.owner, amount);
    }
}
