package VUPShionMod.skins.sk.Shion;

import VUPShionMod.skins.AbstractSkin;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class BlueGiantShion extends AbstractSkin {
    public BlueGiantShion() {
        this.portraitStatic_IMG = ImageMaster.loadImage("VUPShionMod/characters/Shion/portrait2.png");
        this.portraitAnimation_IMG = ImageMaster.loadImage("VUPShionMod/characters/Shion/portrait2.png");

        this.NAME = CardCrawlGame.languagePack.getUIString("VUPShionMod:AbstractShionSkin").TEXT[1];
        this.DESCRIPTION = CardCrawlGame.languagePack.getUIString("VUPShionMod:AbstractShionSkin").EXTRA_TEXT[1];

//        this.portraitAtlasPath = "VUPShionMod/characters/Shion/portrait_spine/Shion";

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/Shion/corpse.png";
        this.atlasURL = "VUPShionMod/characters/Shion/animation/ShionAnimation.atlas";
        this.jsonURL = "VUPShionMod/characters/Shion/animation/ShionAnimation.json";
        this.renderscale = 1.0F;
    }


    @Override
    public void setAnimation() {
        portraitState.setAnimation(0, "idle", true);
        InitializeStaticPortraitVar();
    }
}
