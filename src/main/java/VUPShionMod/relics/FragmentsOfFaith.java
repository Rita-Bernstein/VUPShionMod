package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
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
        this.initializeTips();
    }

    @Override
    public void onPlayerEndTurn() {
        if (this.counter == -2) {
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new HealAction(p, p, p.maxHealth - p.currentHealth));
        }
    }

    @Override
    public int onPlayerHeal(int healAmount) {
        if (this.counter == -2 && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            if(!(AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(healAmount,
                    true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
        return super.onPlayerHeal(healAmount);
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (this.counter == -2)
            AbstractDungeon.player.increaseMaxHp(3, true);
        return super.onAttackedToChangeDamage(info, damageAmount);
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
