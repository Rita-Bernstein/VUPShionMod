package VUPShionMod.powers.Shion;


import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerFinFunnelPassiveAction;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.DissectingFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TrackingAnalysisPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(TrackingAnalysisPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TrackingAnalysisPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/AttackOrderPower128.png"), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/AttackOrderPower48.png"), 0, 0, 48, 48);

        updateDescription();
    }

    @Override
    public void onTriggerFinFunnel(AbstractFinFunnel finFunnel, AbstractCreature target) {
        if (finFunnel.id.equals(PursuitFinFunnel.ID)) {
            flash();
            addToBot(new TriggerFinFunnelPassiveAction((AbstractMonster) target, DissectingFinFunnel.ID,false));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
