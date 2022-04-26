package VUPShionMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;

public abstract class AbstractShionRelic extends CustomRelic {
    public AbstractShionRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, texture, outline, tier, sfx);
    }

    public int onLoseSan(int amount) {
        return amount;
    }
}
