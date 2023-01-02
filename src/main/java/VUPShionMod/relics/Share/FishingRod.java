package VUPShionMod.relics.Share;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;

public class FishingRod extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(FishingRod.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/FishingRod.png";
    private static final String OUTLINE_PATH = "img/relics/outline/FishingRod.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));


    public FishingRod() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof com.megacrit.cardcrawl.rooms.RestRoom) {
            flash();
            if (AbstractDungeon.player.hasRelic("Sozu")) {
                AbstractDungeon.player.getRelic("Sozu").flash();
            } else {
                int amount = AbstractDungeon.relicRng.random(2);
                for (int i = 0; i < amount; i++) {
                    AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(AbstractDungeon.returnRandomPotion()));
                }
            }
        }


    }

    @Override
    public boolean canSpawn() {
        return EnergyPanelPatches.isShionModChar();
    }
}
