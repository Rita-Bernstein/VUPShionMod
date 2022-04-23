package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class LoseHPPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(LoseHPPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public LoseHPPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        isTurnBased = true;
        this.type = PowerType.DEBUFF;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/MorsLibraquePower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/MorsLibraquePower36.png")), 0, 0, 36, 36);
    }


    @Override
    public void updateDescription() {
        this.description =  String.format(DESCRIPTIONS[0],this.amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new LoseHPAction(this.owner,this.owner,this.amount));
    }
}