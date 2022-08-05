package VUPShionMod.powers.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.minions.AbstractPlayerMinion;
import VUPShionMod.minions.ElfMinion;
import VUPShionMod.minions.MinionGroup;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class SynchroSummonPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(SynchroSummonPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public SynchroSummonPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/SynchroSummonPower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/SynchroSummonPower48.png")), 0, 0, 48, 48);
        updateDescription();
        this.isTurnBased = true;

    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(!MinionGroup.areMinionsBasicallyDead()){
            for(AbstractPlayerMinion minion : MinionGroup.getMinions()){
                if(minion instanceof ElfMinion){
                    addToBot(new ApplyPowerAction(this.owner,this.owner,new StrengthPower(this.owner,this.amount)));
                }
            }
        }
    }
}
