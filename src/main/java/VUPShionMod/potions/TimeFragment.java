package VUPShionMod.potions;

import VUPShionMod.VUPShionMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class TimeFragment extends AbstractShionImagePotion {
    public static final String POTION_ID = VUPShionMod.makeID(TimeFragment.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    private static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    private static final PotionRarity RARITY = PotionRarity.RARE;


    public TimeFragment() {
        super(POTION_ID, TimeFragment.class.getSimpleName() + ".png", RARITY);
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
            addToBot(new SkipEnemiesTurnAction());
            addToBot(new PressEndTurnButtonAction());
        }
    }


    public int getPotency(int ascensionLevel) {
        return 1;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new TimeFragment();
    }
}