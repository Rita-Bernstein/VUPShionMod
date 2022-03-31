package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;

public class HallowedCase extends CustomRelic {
    public static final String ID = VUPShionMod.makeID("HallowedCase");
    public static final String IMG_PATH = "img/relics/HallowedCase.png";
    private static final String OUTLINE_PATH = "img/relics/outline/HallowedCase.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public HallowedCase() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

}
