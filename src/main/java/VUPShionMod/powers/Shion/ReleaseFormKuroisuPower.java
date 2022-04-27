package VUPShionMod.powers.Shion;

import VUPShionMod.actions.Shion.MakeLoadedCardAction;
import VUPShionMod.cards.ShionCard.tempCards.QuickScreen;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import VUPShionMod.VUPShionMod;

public class ReleaseFormKuroisuPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("ReleaseFormKuroisuPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ReleaseFormKuroisuPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/ReleaseFormKuroisuPower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/ReleaseFormKuroisuPower32.png")), 0, 0, 32, 32);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new MakeLoadedCardAction(new QuickScreen(),this.amount,false));
//        addToBot(new MakeTempCardInDrawPileAction(new QuickScreen(), this.amount, false, true, false));
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
