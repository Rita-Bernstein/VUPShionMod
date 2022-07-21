package VUPShionMod.powers.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class DelayAvatarAttackPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(DelayAvatarAttackPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DelayAvatarAttackPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.setImage("Clock84.png", "Clock32.png");
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (int i = 0; i < 5; i++)
                addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount, true),
                        DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }

        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, DelayAvatarAttackPower.POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = String.format( DESCRIPTIONS[0],this.amount);
    }
}
