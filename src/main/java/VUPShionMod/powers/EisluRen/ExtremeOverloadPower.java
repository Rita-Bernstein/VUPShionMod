package VUPShionMod.powers.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.ui.WingShield;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class ExtremeOverloadPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(ExtremeOverloadPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public ExtremeOverloadPower(AbstractCreature owner) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/ExtremeOverloadPower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/ExtremeOverloadPower48.png")), 0, 0, 48, 48);
        updateDescription();
        this.isTurnBased = true;
        this.type = PowerType.DEBUFF;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        addToBot(new LoseWingShieldAction(WingShield.getWingShield().getCount() / 2));
    }

    @Override
    public void onVictory() {
        addToBot(new LoseWingShieldAction(WingShield.getWingShield().getCount() / 2));
    }
}
