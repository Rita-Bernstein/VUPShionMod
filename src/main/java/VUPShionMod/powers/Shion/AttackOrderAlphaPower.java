package VUPShionMod.powers.Shion;


import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import VUPShionMod.VUPShionMod;

public class AttackOrderAlphaPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(AttackOrderAlphaPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AttackOrderAlphaPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        loadShionRegion("AttackOrderPower");

        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        if (this.owner.hasPower(AttackOrderBetaPower.POWER_ID))
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, AttackOrderBetaPower.POWER_ID));

        if (this.owner.hasPower(AttackOrderGammaPower.POWER_ID))
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, AttackOrderGammaPower.POWER_ID));

        if (this.owner.hasPower(AttackOrderDeltaPower.POWER_ID))
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, AttackOrderDeltaPower.POWER_ID));
    }


    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], 5);
    }
}
