package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.StiffnessPower;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrackingBeacon extends CustomRelic {
    public static final String ID = VUPShionMod.makeID("TrackingBeacon");
    public static final String IMG_PATH = "img/relics/TrackingBeacon.png";
    private static final String OUTLINE_PATH = "img/relics/outline/TrackingBeacon.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public TrackingBeacon() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

}
