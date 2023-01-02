package VUPShionMod.powers.Shion;


import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerFinFunnelPassiveAction;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GravityRepressionPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(GravityRepressionPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GravityRepressionPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        loadShionRegion("AttackOrderPower");

        updateDescription();
    }

    @Override
    public void onTriggerFinFunnel(AbstractFinFunnel finFunnel, AbstractCreature target) {
        if (finFunnel.id.equals(GravityFinFunnel.ID)) {
            flash();
            addToBot(new TriggerFinFunnelPassiveAction((AbstractMonster) target, PursuitFinFunnel.ID, false));
        }
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
