package VUPShionMod.prayers;

import VUPShionMod.VUPShionMod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;

public class BloodPrayerPrayer extends AbstractPrayer {
    public static final String Prayer_ID = VUPShionMod.makeID("BloodPrayerPrayer");
    private static final OrbStrings prayerStrings = CardCrawlGame.languagePack.getOrbString(Prayer_ID);
    public static final String NAME = prayerStrings.NAME;
    public static final String[] DESCRIPTIONS = prayerStrings.DESCRIPTION;

    public BloodPrayerPrayer(int turns, int amount) {
        this.ID = Prayer_ID;
        this.name = NAME;
        this.turns = turns;
        this.amount = amount;
        updateDescription();

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/prayer/BloodPrayerPrayer128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/prayer/BloodPrayerPrayer36.png")), 0, 0, 36, 36);
    }

    @Override
    public void updateDescription() {
        if (this.amount > 1)
            this.description = String.format(DESCRIPTIONS[1], this.amount);
        else
            this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void use() {
    }

    @Override
    public AbstractPrayer makeCopy() {
        return new BloodPrayerPrayer(this.turns,this.amount);
    }
}
