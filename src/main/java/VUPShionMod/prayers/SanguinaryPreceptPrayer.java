package VUPShionMod.prayers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.LoseSansAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SanguinaryPreceptPrayer extends AbstractPrayer {
    public static final String Prayer_ID = VUPShionMod.makeID(SanguinaryPreceptPrayer.class.getSimpleName());
    private static final OrbStrings prayerStrings = CardCrawlGame.languagePack.getOrbString(Prayer_ID);
    public static final String NAME = prayerStrings.NAME;
    public static final String[] DESCRIPTIONS = prayerStrings.DESCRIPTION;

    public SanguinaryPreceptPrayer(int turns, int amount) {
        this.ID = Prayer_ID;
        this.name = NAME;
        this.turns = turns;
        this.amount = amount;
        updateDescription();
        loadRegion("strength");
//        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/prayer/BloodPrayerPrayer128.png")), 0, 0, 128, 128);
//        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/prayer/BloodPrayerPrayer36.png")), 0, 0, 36, 36);
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void use() {
        addToBot(new LoseSansAction(this.amount));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                        if (!monster.isDeadOrEscaped()) {
                            addToTop(new LoseHPAction(monster, AbstractDungeon.player, monster.maxHealth / 10));
                        }
                    }
                }
                isDone = true;
            }
        });

    }

    @Override
    public AbstractPrayer makeCopy() {
        return new SanguinaryPreceptPrayer(this.turns, this.amount);
    }
}