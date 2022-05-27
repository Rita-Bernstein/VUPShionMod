package VUPShionMod.prayers;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;

public class ThieveryPrayer extends AbstractPrayer {
    public static final String Prayer_ID = VUPShionMod.makeID(ThieveryPrayer.class.getSimpleName());
    private static final OrbStrings prayerStrings = CardCrawlGame.languagePack.getOrbString(Prayer_ID);
    public static final String NAME = prayerStrings.NAME;
    public static final String[] DESCRIPTIONS = prayerStrings.DESCRIPTION;

    public ThieveryPrayer(int turns, int amount) {
        this.ID = Prayer_ID;
        this.name = NAME;
        this.turns = turns;
        this.amount = amount;
        updateDescription();
        loadRegion("thievery");
//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/prayer/BloodPrayerPrayer128.png")), 0, 0, 128, 128);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/prayer/BloodPrayerPrayer36.png")), 0, 0, 36, 36);
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void use() {
        int alive = 0;
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                if (!monster.isDeadOrEscaped()) {
                    alive++;
                }
            }
        }

        addToBot(new GainGoldAction(alive * this.amount));

    }

    @Override
    public AbstractPrayer makeCopy() {
        return new ThieveryPrayer(this.turns, this.amount);
    }
}