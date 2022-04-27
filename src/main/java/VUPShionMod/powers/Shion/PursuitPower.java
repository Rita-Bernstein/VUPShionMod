package VUPShionMod.powers.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PursuitPower extends AbstractShionPower implements HealthBarRenderPower {
    public static final String POWER_ID = VUPShionMod.makeID("PursuitPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PursuitPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.setImage("Lock84.png", "Lock32.png");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        for (AbstractFinFunnel funnel : AbstractPlayerPatches.AddFields.finFunnelList.get(AbstractDungeon.player)) {
            if (!this.owner.isDeadOrEscaped()) {
                funnel.onPursuitEnemy(this.owner);
            }
        }
    }

    @Override
    public int getHealthBarAmount() {
        return this.amount;
    }

    @Override
    public Color getColor() {
        return new Color(1.0F, 0.5F, 0.0F, 0.0F);
    }
}
