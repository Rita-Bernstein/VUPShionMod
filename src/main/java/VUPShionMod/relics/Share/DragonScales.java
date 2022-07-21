package VUPShionMod.relics.Share;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class DragonScales extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(DragonScales.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/DragonScales.png";
    private static final String OUTLINE_PATH = "img/relics/outline/DragonScales.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));



    public DragonScales() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }



    @Override
    public void onLoseHp(int damageAmount) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            flash();
            addToTop(new GainBlockAction(AbstractDungeon.player,AbstractDungeon.player,3));
        }
    }

    @Override
    public boolean canSpawn() {
        return EnergyPanelPatches.isShionModChar();
    }
}
