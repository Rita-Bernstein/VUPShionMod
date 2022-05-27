package VUPShionMod.powers.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DelayAvatarPower extends AbstractShionPower implements CloneablePowerInterface {
    public static final String POWER_ID = VUPShionMod.makeID("DelayAvatarPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int damage;
    private static int idOffset;

    public DelayAvatarPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID + idOffset;
        idOffset++;
        this.owner = owner;
        this.amount = amount;
        this.damage = amount;
        this.setImage("Clock84.png", "Clock32.png");
        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount < this.amount) {
            this.amount -= damageAmount;
        } else {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (int i = 0; i < 5; i++)
                    addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount, true),
                            DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            }
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        updateDescription();
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