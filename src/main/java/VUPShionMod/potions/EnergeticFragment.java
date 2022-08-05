package VUPShionMod.potions;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.UpgradeDeckAction;
import VUPShionMod.actions.EisluRen.AddWingShieldAction;
import VUPShionMod.vfx.Common.UpPotionEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.function.Predicate;

public class EnergeticFragment extends AbstractShionImagePotion {
    public static final String POTION_ID = VUPShionMod.makeID(EnergeticFragment.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    private static final PotionRarity RARITY = PotionRarity.RARE;


    public EnergeticFragment() {
        super(POTION_ID, EnergeticFragment.class.getSimpleName() + ".png", RARITY);
        this.isThrown = false;
        this.targetRequired = false;
        this.labOutlineColor = VUPShionMod.EisluRenPotion_Color;
    }


    public void initializeData() {
        this.potency = getPotency();
        this.description = String.format(DESCRIPTIONS[0], this.potency);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }


    public void use(AbstractCreature target) {
        addToBot(new AddWingShieldAction(this.potency));
    }


    public int getPotency(int ascensionLevel) {
        return 5;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new EnergeticFragment();
    }
}