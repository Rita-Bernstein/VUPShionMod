package VUPShionMod.powers.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LeachingBloodPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(LeachingBloodPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public LeachingBloodPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        loadRegion("barricade");
        updateDescription();
    }


    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && card.type == AbstractCard.CardType.ATTACK && this.amount > 0) {
            flash();
            this.amount--;
            if (this.amount <= 0) {
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }
        }
    }

    @Override
    public void monsterAfterOnAttack(DamageInfo info, AbstractMonster m, int damageAmount) {
        if (m != null && info.type == DamageInfo.DamageType.NORMAL)
            if (m.lastDamageTaken > 0) {

                AbstractDungeon.player.heal(m.lastDamageTaken);
            }
    }


    @Override
    public void updateDescription() {
        this.description = this.amount > 1 ? String.format(DESCRIPTIONS[1], this.amount) : DESCRIPTIONS[0];
    }
}
