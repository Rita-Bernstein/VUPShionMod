package VUPShionMod.relics.Event;

import VUPShionMod.VUPShionMod;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;

public class Warlike extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(Warlike.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/Warlike.png";
    private static final String OUTLINE_PATH = "img/relics/outline/Warlike.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public Warlike() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }




}
