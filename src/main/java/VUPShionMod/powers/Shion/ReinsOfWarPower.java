package VUPShionMod.powers.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.DissectingFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ReinsOfWarPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(ReinsOfWarPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ReinsOfWarPower(AbstractCreature owner) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        loadShionRegion("ReinsOfWarPower");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        this.description += DESCRIPTIONS[1];
        this.description += DESCRIPTIONS[2];
        this.description += DESCRIPTIONS[3];
        this.description += DESCRIPTIONS[4];
        this.description += DESCRIPTIONS[5];
        this.description += DESCRIPTIONS[6];
        this.description += DESCRIPTIONS[7];

    }

    @Override
    public void atStartOfTurnPostDraw() {
        AbstractFinFunnel finFunnel = AbstractPlayerPatches.AddFields.finFunnelManager.get(AbstractDungeon.player).getFinFunnel(DissectingFinFunnel.ID);

        if (finFunnel != null) {
            if (finFunnel.getLevel() >= 26) {
                addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 3)));
                addToBot(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, 2)));
                addToBot(new GainEnergyAction(2));
                return;
            }

            if (finFunnel.getLevel() >= 21) {
                addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 3)));
                addToBot(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, 2)));
                addToBot(new GainEnergyAction(1));
                return;
            }

            if (finFunnel.getLevel() >= 16) {
                addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 2)));
                addToBot(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, 2)));
                addToBot(new GainEnergyAction(1));
                return;
            }

            if (finFunnel.getLevel() >= 11) {
                addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 2)));
                addToBot(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, 2)));
                return;
            }

            if (finFunnel.getLevel() >= 6) {
                addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 2)));
                addToBot(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, 1)));
                return;
            }

            if (finFunnel.getLevel() >= 4) {
                addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1)));
                addToBot(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, 1)));
                return;
            }

            addToBot(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, 1)));
        }
    }
}
