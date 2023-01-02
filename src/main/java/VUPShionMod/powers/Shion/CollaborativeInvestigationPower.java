package VUPShionMod.powers.Shion;


import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerFinFunnelPassiveAction;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.InvestigationFinFunnel;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CollaborativeInvestigationPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(CollaborativeInvestigationPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CollaborativeInvestigationPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        loadShionRegion("AttackOrderPower");

        updateDescription();
    }


    @Override
    public void onTriggerFinFunnel(AbstractFinFunnel finFunnel, AbstractCreature target) {
        if (!finFunnel.id.equals(InvestigationFinFunnel.ID)) {
            flash();
            addToBot(new TriggerFinFunnelPassiveAction((AbstractMonster) target, InvestigationFinFunnel.ID, false));
        }
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
