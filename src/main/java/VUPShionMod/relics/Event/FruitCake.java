package VUPShionMod.relics.Event;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.AchievementPatches;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.util.SaveHelper;
import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue.Save;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FruitCake extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(FruitCake.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/FruitCake.png";
    private static final String OUTLINE_PATH = "img/relics/outline/FruitCake.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public FruitCake() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        super.onEquip();
        AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
        //        AchievementPatches.unlockAchievement("RitaShop");
    }

    @Override
    public void atTurnStart() {
        if (!this.usedUp) {
            if (!AbstractDungeon.player.isBloodied) {
                flash();
                addToBot(new GainEnergyAction(1));
            } else {
                this.grayscale = true;
                this.usedUp = true;
            }
        }
    }

    @Override
    public void atBattleStart() {
        if (AbstractDungeon.player.isBloodied) {
            this.grayscale = true;
            this.usedUp = true;
        } else {
            this.grayscale = false;
            this.usedUp = false;
        }
    }
}
