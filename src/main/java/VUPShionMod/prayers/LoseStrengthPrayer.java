package VUPShionMod.prayers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ApplyPowerToAllEnemyAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.function.Supplier;

public class LoseStrengthPrayer extends AbstractPrayer {
    public static final String Prayer_ID = VUPShionMod.makeID(LoseStrengthPrayer.class.getSimpleName());
    private static final OrbStrings prayerStrings = CardCrawlGame.languagePack.getOrbString(Prayer_ID);
    public static final String NAME = prayerStrings.NAME;
    public static final String[] DESCRIPTIONS = prayerStrings.DESCRIPTION;

    public LoseStrengthPrayer(int turns, int amount) {
        this.ID = Prayer_ID;
        this.name = NAME;
        this.turns = turns;
        this.amount = amount;
        updateDescription();
        loadRegion("shackle");
//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/prayer/BloodPrayerPrayer128.png")), 0, 0, 128, 128);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/prayer/BloodPrayerPrayer36.png")), 0, 0, 36, 36);
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void use() {
        Supplier<AbstractPower> powerToApply = () -> new StrengthPower(null, -this.amount);
        addToBot(new ApplyPowerToAllEnemyAction(powerToApply));
    }

    @Override
    public AbstractPrayer makeCopy() {
        return new LoseStrengthPrayer(this.turns, this.amount);
    }
}
