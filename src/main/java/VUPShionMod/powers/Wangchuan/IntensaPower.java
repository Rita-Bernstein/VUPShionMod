package VUPShionMod.powers.Wangchuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.ApplyCorGladiiAction;
import VUPShionMod.patches.GameStatsPatch;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class IntensaPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(IntensaPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public IntensaPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;

        if (this.amount > 2)
            this.amount = 2;

        loadShionRegion("PetalsFallPower");
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);

        if (this.amount > 2)
            this.amount = 2;
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        addToBot(new ApplyCorGladiiAction((int) (this.amount * GameStatsPatch.corGladiiLoseThisTurn * 0.5f)));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], (int) (this.amount * GameStatsPatch.corGladiiLoseThisTurn * 0.5f));
    }
}
