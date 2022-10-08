package VUPShionMod.relics.Event;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Liyezhu.FinalPrayerPower;
import VUPShionMod.relics.AbstractShionRelic;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class FragmentsOfFaith extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(FragmentsOfFaith.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/FragmentsOfFaith.png";
    private static final String OUTLINE_PATH = "img/relics/outline/FragmentsOfFaith.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));
    private static final Texture UPGRADE_IMG = new Texture(VUPShionMod.assetPath("img/relics/FragmentsOfFaith2.png"));
    private static final Texture OUTLINE_UPGRADE_IMG = new Texture(VUPShionMod.assetPath("img/relics/outline/FragmentsOfFaith2.png"));
    private static final String UPGRADED_NAME = CardCrawlGame.languagePack.getRelicStrings(ID).DESCRIPTIONS[1];


    public FragmentsOfFaith() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        if (AbstractDungeon.player != null)
            if (AbstractDungeon.actNum >= 3)
                upgrade();
    }

    @Override
    public String getUpdatedDescription() {
        if (this.counter == -2) {
            ReflectionHacks.setPrivateFinal(this, AbstractRelic.class, "name", UPGRADED_NAME);
            this.flavorText = DESCRIPTIONS[2];
            return this.DESCRIPTIONS[3];
        }
        ReflectionHacks.setPrivateFinal(this, AbstractRelic.class, "name", CardCrawlGame.languagePack.getRelicStrings(ID).NAME);
        this.flavorText = CardCrawlGame.languagePack.getRelicStrings(ID).FLAVOR;
        return this.DESCRIPTIONS[0];
    }

    public void setDescriptionAfterLoading() {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));

        if(this.counter == -2)
        this.tips.add(new PowerTip(DESCRIPTIONS[4], DESCRIPTIONS[5]));
        this.initializeTips();
    }

    @Override
    public void atBattleStart() {
        if(this.counter == 2) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FinalPrayerPower(AbstractDungeon.player)));
        }
    }

    @Override
    public void onVictory() {
        if (this.counter != -2) {
            flash();
            AbstractDungeon.player.increaseMaxHp(5, true);
        }
    }

    @Override
    public void upgrade() {
        setCounter(-2);
        this.img = UPGRADE_IMG;
        this.outlineImg = OUTLINE_UPGRADE_IMG;
        setDescriptionAfterLoading();
    }



}
