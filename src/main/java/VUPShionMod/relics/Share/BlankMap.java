package VUPShionMod.relics.Share;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BlankMap extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(BlankMap.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/BlankMap.png";
    private static final String OUTLINE_PATH = "img/relics/outline/BlankMap.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));


    public BlankMap() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void onEnterRoom(AbstractRoom room) {
        if (room instanceof com.megacrit.cardcrawl.rooms.EventRoom) {
            flash();
            AbstractDungeon.player.gainGold(18);
        }
    }

    @Override
    public boolean canSpawn() {
        return EnergyPanelPatches.isShionModChar();
    }
}
