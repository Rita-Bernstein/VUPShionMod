package VUPShionMod.powers.Wangchuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class MoonstriderPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(MoonstriderPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public MoonstriderPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        loadRegion("flameBarrier");
        updateDescription();
    }


    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner) {
            flash();
            addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new CorGladiiPower(AbstractDungeon.player,this.amount)));
        }
        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,MoonstriderPower.POWER_ID));
    }
}