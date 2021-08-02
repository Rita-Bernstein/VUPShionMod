package VUPShionMod.util;

import com.megacrit.cardcrawl.core.Settings;

public class ShionPortraitAnimation extends AbstractSkin {
    private static float guardianSFX_timer = 0.0f;
    private static boolean guardianWhirl_played = false;

    public ShionPortraitAnimation() {
        this.portraitAtlasPath = "VUPShionMod/characters/Shion/portrait_spine/Shion";

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder.png";
        this.CORPSE = "VUPShionMod/characters/Shion/corpse.png";
        this.atlasURL = "VUPShionMod/characters/Shion/animation/hero_00601.atlas";
        this.jsonURL = "VUPShionMod/characters/Shion/animation/hero_00601.json";
        this.renderscale = 16.0F;
    }


    @Override
    public void InitializeStaticPortraitVar() {
        guardianSFX_timer = 0.0f;
        guardianWhirl_played = false;
    }

    @Override
    public void setPos() {
        portraitSkeleton.setPosition(0.0f * Settings.scale, Settings.HEIGHT - 1080.0f * Settings.scale);
    }

    @Override
    public void update() {
    }
}


