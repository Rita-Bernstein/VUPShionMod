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
    public static final String POWER_ID = VUPShionMod.makeID("ReinsOfWarPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ReinsOfWarPower(AbstractCreature owner) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/ReinsOfWarPower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/ReinsOfWarPower36.png")), 0, 0, 36, 36);

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
            if(finFunnel.level >= 26){
                addToBot(new ApplyPowerAction(this.owner,this.owner,new StrengthPower(this.owner,3)));
                addToBot(new GainHyperdimensionalLinksAction(3));
                addToBot(new GainEnergyAction(2));
                return;
            }

            if(finFunnel.level >= 21){
                addToBot(new ApplyPowerAction(this.owner,this.owner,new StrengthPower(this.owner,3)));
                addToBot(new GainHyperdimensionalLinksAction(3));
                addToBot(new GainEnergyAction(1));
                return;
            }

            if(finFunnel.level >= 16){
                addToBot(new ApplyPowerAction(this.owner,this.owner,new StrengthPower(this.owner,2)));
                addToBot(new GainHyperdimensionalLinksAction(2));
                addToBot(new GainEnergyAction(1));
                return;
            }

            if(finFunnel.level >= 11){
                addToBot(new ApplyPowerAction(this.owner,this.owner,new StrengthPower(this.owner,2)));
                addToBot(new GainHyperdimensionalLinksAction(2));
                return;
            }

            if(finFunnel.level >= 6){
                addToBot(new ApplyPowerAction(this.owner,this.owner,new StrengthPower(this.owner,2)));
                addToBot(new ApplyPowerAction(this.owner,this.owner,new DexterityPower(this.owner,2)));
                return;
            }

            if(finFunnel.level >= 4){
                addToBot(new ApplyPowerAction(this.owner,this.owner,new StrengthPower(this.owner,1)));
                addToBot(new ApplyPowerAction(this.owner,this.owner,new DexterityPower(this.owner,1)));
                return;
            }

            addToBot(new ApplyPowerAction(this.owner,this.owner,new DexterityPower(this.owner,1)));
        }
    }
}
