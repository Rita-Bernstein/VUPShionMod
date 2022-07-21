package VUPShionMod.relics.Event;

import VUPShionMod.VUPShionMod;
import VUPShionMod.relics.AbstractShionRelic;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SapphireRoseNecklace extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(SapphireRoseNecklace.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/SapphireRoseNecklace.png";
    private static final String OUTLINE_PATH = "img/relics/outline/SapphireRoseNecklace.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public SapphireRoseNecklace() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void onVictory() {
        flash();
        AbstractDungeon.player.increaseMaxHp(2, true);
    }
}
