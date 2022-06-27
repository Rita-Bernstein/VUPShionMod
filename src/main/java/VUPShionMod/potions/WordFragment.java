package VUPShionMod.potions;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.UpgradeDeckAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.vfx.Common.UpPotionEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.function.Predicate;

public class WordFragment extends AbstractShionImagePotion {
    public static final String POTION_ID = VUPShionMod.makeID(WordFragment.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    private static final PotionRarity RARITY = PotionRarity.RARE;


    public WordFragment() {
        super(POTION_ID, WordFragment.class.getSimpleName() + ".png", RARITY);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = VUPShionMod.LiyezhuPotion_Color;
    }


    public void initializeData() {
        this.potency = getPotency();
        this.description = String.format(DESCRIPTIONS[0], this.potency);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }


    public void use(AbstractCreature target) {
        Predicate<AbstractCard> predicate = card -> card.hasTag(CardTagsEnum.Prayer_CARD);
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !(AbstractDungeon.getCurrRoom()).isBattleEnding()) {
            for (int i = 0; i < this.potency; i++)
                addToBot(new UpgradeDeckAction(1, true, predicate));
        } else {
            AbstractDungeon.effectList.add(new UpPotionEffect(this.potency, predicate));
        }
    }

    @Override
    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            return false;
        }

        if ((AbstractDungeon.getCurrRoom()).event != null && (AbstractDungeon.getCurrRoom()).event instanceof com.megacrit.cardcrawl.events.shrines.WeMeetAgain) {
            return false;
        }

        if ((AbstractDungeon.getCurrRoom()).phase != AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.isScreenUp) {
            return false;
        }

        return true;
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new WordFragment();
    }
}