package VUPShionMod.potions;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
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
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class HolyWater extends AbstractShionImagePotion {
    public static final String POTION_ID = VUPShionMod.makeID(HolyWater.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    private static final PotionRarity RARITY = PotionRarity.UNCOMMON;

    public HolyWater() {
        super(POTION_ID, HolyWater.class.getSimpleName() + ".png", RARITY);
        this.isThrown = false;
        this.targetRequired = false;

    }


    public void initializeData() {
        this.potency = getPotency();
        this.description = String.format(DESCRIPTIONS[0], this.potency);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }


    public void use(AbstractCreature target) {
        for (int i = AbstractDungeon.player.masterDeck.group.size() - 1; i >= 0; i--) {
            if ((AbstractDungeon.player.masterDeck.group.get(i)).type == AbstractCard.CardType.CURSE &&
                    !(AbstractDungeon.player.masterDeck.group.get(i)).inBottleFlame &&
                    !(AbstractDungeon.player.masterDeck.group.get(i)).inBottleLightning) {

                AbstractDungeon.effectList.add(new PurgeCardEffect(AbstractDungeon.player.masterDeck.group.get(i)));
                AbstractDungeon.player.masterDeck.removeCard(AbstractDungeon.player.masterDeck.group.get(i));
            }
        }


        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, this.potency));
        } else {
            AbstractDungeon.player.heal(this.potency);
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

        return true;
    }

    public int getPotency(int ascensionLevel) {
        return 10;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new HolyWater();
    }
}