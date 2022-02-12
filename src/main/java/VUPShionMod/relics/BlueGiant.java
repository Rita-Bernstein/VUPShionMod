package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;

public class BlueGiant extends CustomRelic {
    public static final String ID = VUPShionMod.makeID("BlueGiant");
    public static final String IMG_PATH = "img/relics/BlueGiant.png";
    private static final String OUTLINE_PATH = "img/relics/outline/BlueGiant.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public BlueGiant() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

}
