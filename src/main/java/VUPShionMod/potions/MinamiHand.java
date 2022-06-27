package VUPShionMod.potions;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.SelectCardToHandAction;
import VUPShionMod.actions.Common.SelectSrcCardToHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.function.Predicate;

public class MinamiHand extends AbstractShionImagePotion {
    public static final String POTION_ID = VUPShionMod.makeID(MinamiHand.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    private static final PotionRarity RARITY = PotionRarity.RARE;

    public MinamiHand() {
        super(POTION_ID, MinamiHand.class.getSimpleName() + ".png", RARITY);
        this.isThrown = false;
        this.targetRequired = false;
//        this.labOutlineColor = VUPShionMod.Shion_Color;
    }


    public void initializeData() {
        this.potency = getPotency();
        this.description = String.format(DESCRIPTIONS[0], this.potency);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }


    public void use(AbstractCreature target) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            Predicate<AbstractCard> predicate = card -> card.type == AbstractCard.CardType.POWER;
            addToBot(new SelectSrcCardToHandAction(this.potency, true, predicate));
        }
    }


    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new MinamiHand();
    }
}