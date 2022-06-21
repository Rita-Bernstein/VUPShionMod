package VUPShionMod.powers.Shion;


import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerBleedingAction;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.InvestigationFinFunnel;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class StrikeIntegratedPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(StrikeIntegratedPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public StrikeIntegratedPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/AttackOrderPower128.png"), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/AttackOrderPower48.png"), 0, 0, 48, 48);

        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        if (this.owner.hasPower(GravityVortexPower.POWER_ID))
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, GravityVortexPower.POWER_ID));

        if (this.owner.hasPower(AoeAnalysisPower.POWER_ID))
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, AoeAnalysisPower.POWER_ID));

        if (this.owner.hasPower(GravityVortexPower.POWER_ID))
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, GravityVortexPower.POWER_ID));
    }

    @Override
    public void onTriggerFinFunnel(AbstractFinFunnel finFunnel, AbstractCreature target) {
        if (target != null && !target.isDeadOrEscaped())
            if (finFunnel.id.equals(InvestigationFinFunnel.ID)) {
                flash();
                addToBot(new TriggerBleedingAction(target));
            }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
