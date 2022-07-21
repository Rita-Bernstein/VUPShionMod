package VUPShionMod.powers.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class ChasteReflectionPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(ChasteReflectionPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public ChasteReflectionPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.amount2 = 0;
        loadRegion("demonForm");
        updateDescription();
    }


    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if(info.type != DamageInfo.DamageType.HP_LOSS) {
            this.amount2 += damageAmount;
            updateDescription();
        }
        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new DamageAllEnemiesAction(null,
                          DamageInfo.createDamageMatrix(this.amount2, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount2);
    }
}