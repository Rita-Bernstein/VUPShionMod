package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.OmegaFlashEffect;

public class HolyCoffinSinkingSpiritPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("HolyCoffinSinkingSpiritPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HolyCoffinSinkingSpiritPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        this.amount = amount;
        this.loadRegion("juggernaut");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }


    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        if(power instanceof HyperdimensionalLinksPower && target.isPlayer && power.amount > 0){
            flash();
            addToBot(new GainBlockAction(this.owner,this.amount));
        }
    }

//    @Override
//    public void onReducePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
//        super.onReducePower(power, target, source);
//        if(power instanceof HyperdimensionalLinksPower && target.isPlayer && power.amount > 0){
//            flash();
//            addToBot(new GainBlockAction(this.owner,this.amount));
//        }
//    }
}
