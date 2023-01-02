package VUPShionMod.potions;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;

public class MinamiDepository extends AbstractShionImagePotion {
    public static final String POTION_ID = VUPShionMod.makeID(MinamiDepository.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    private static final PotionRarity RARITY = PotionRarity.RARE;

    public MinamiDepository() {
        super(POTION_ID, MinamiDepository.class.getSimpleName() + ".png", RARITY);
        this.isThrown = false;
        this.targetRequired = false;
//        this.labOutlineColor = VUPShionMod.Shion_Color;
    }


    public void initializeData() {
        this.potency = getPotency();
        this.description = String.format(this.potency > 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[0], this.potency, this.potency);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }


    public void use(AbstractCreature target) {
        AbstractDungeon.player.potionSlots += this.potency;
        for (int i = this.potency; i > 0; i--) {
            AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - i));
        }


        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            for (int i = 0; i < this.potency; i++) {
                addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
            }
        } else if (AbstractDungeon.player.hasRelic("Sozu")) {
            AbstractDungeon.player.getRelic("Sozu").flash();
        } else {
            for (int i = 0; i < this.potency; i++) {
                AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(AbstractDungeon.returnRandomPotion()));
            }
        }
    }

    @Override
    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            return false;
        }
        return (AbstractDungeon.getCurrRoom()).event == null ||
                !((AbstractDungeon.getCurrRoom()).event instanceof com.megacrit.cardcrawl.events.shrines.WeMeetAgain);
    }


    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new MinamiDepository();
    }
}