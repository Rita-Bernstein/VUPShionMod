package VUPShionMod.relics.Event;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.LoseMaxHPAction;
import VUPShionMod.relics.AbstractShionRelic;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.RestOption;

public class AbyssalCrux extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(AbyssalCrux.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/AbyssalCrux.png";
    private static final String OUTLINE_PATH = "img/relics/outline/AbyssalCrux.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));
    private static final Texture UPGRADE_IMG = new Texture(VUPShionMod.assetPath("img/relics/AbyssalCrux2.png"));
    private static final Texture OUTLINE_UPGRADE_IMG = new Texture(VUPShionMod.assetPath("img/relics/outline/AbyssalCrux2.png"));
    private static final String UPGRADED_NAME = CardCrawlGame.languagePack.getRelicStrings(ID).DESCRIPTIONS[1];

    public boolean dontHeal = false;

    public AbyssalCrux() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        if (this.upgraded) {
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

    public boolean canUseCampfireOption(AbstractCampfireOption option) {
        if (!upgraded)
            if (option instanceof RestOption && option.getClass().getName().equals(RestOption.class.getName())) {
                ((RestOption) option).updateUsability(false);
                return false;
            }
        return true;
    }


    @Override
    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && damageAmount > 0) {
            if (!upgraded) {
                if (!this.dontHeal && info.type == DamageInfo.DamageType.HP_LOSS) {
                    flash();
                    addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, (int) Math.floor(damageAmount * 0.3f)));
                    addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                }
            } else {
                flash();
                addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(damageAmount,
                        true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));
            }
        }
    }


    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (upgraded && info.type != DamageInfo.DamageType.HP_LOSS) {
            addToTop(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, damageAmount));
            return 0;
        }
        return damageAmount;
    }

    @Override
    public void onPlayerEndTurn() {
        if (upgraded) {
            addToBot(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 500));
            addToBot(new LoseMaxHPAction(AbstractDungeon.player, AbstractDungeon.player, 500));
        }
    }

    @Override
    public void upgrade() {
        this.upgraded = true;
        this.img = UPGRADE_IMG;
        this.outlineImg = OUTLINE_UPGRADE_IMG;
        setDescriptionAfterLoading();
    }
}
