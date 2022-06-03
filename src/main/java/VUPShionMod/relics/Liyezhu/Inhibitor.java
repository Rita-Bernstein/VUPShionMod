package VUPShionMod.relics.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;

public class Inhibitor extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(Inhibitor.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/Inhibitor.png";
    private static final String OUTLINE_PATH = "img/relics/outline/Inhibitor.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public Inhibitor() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public int onLoseSan(int amount) {
        return amount - 1;
    }
}
