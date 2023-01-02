package VUPShionMod.powers.Wangchuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class NihilImmensumPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(NihilImmensumPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public static final int triggerAmount = 15;

    public NihilImmensumPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        this.amount = amount;
        this.amount2 = 0;
        loadShionRegion("CircuitPower");
        updateDescription();
        this.isTurnBased = true;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], triggerAmount, amount, this.amount2);
    }


    @Override
    public void onSpecificTrigger() {
        this.amount2++;
        if (this.amount2 >= triggerAmount) {
            flash();
            addToBot(new ApplyPowerAction(this.owner, this.owner, new ImmuneDamagePower(this.owner)));
            this.amount2 = 0;
        }
        updateDescription();
    }
}
