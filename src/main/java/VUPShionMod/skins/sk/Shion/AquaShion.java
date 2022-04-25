package VUPShionMod.skins.sk.Shion;

import VUPShionMod.relics.BlueGiant;
import VUPShionMod.relics.Drapery;
import VUPShionMod.relics.TheRipple;
import VUPShionMod.skins.AbstractSkin;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class AquaShion extends AbstractSkin {
    public AquaShion() {
        this.portraitStatic_IMG = ImageMaster.loadImage("VUPShionMod/characters/Shion/portrait3.png");
        this.portraitAnimation_IMG = ImageMaster.loadImage("VUPShionMod/characters/Shion/portrait3.png");

        this.NAME = CardCrawlGame.languagePack.getUIString("VUPShionMod:AbstractShionSkin").TEXT[2];
        this.DESCRIPTION = CardCrawlGame.languagePack.getUIString("VUPShionMod:AbstractShionSkin").EXTRA_TEXT[2];

//        this.portraitAtlasPath = "VUPShionMod/characters/Shion/portrait_spine/Shion";

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/Shion/corpse.png";
        this.atlasURL = "VUPShionMod/characters/Shion/animation/Stance_ZYLJX_BIKINI.atlas";
        this.jsonURL = "VUPShionMod/characters/Shion/animation/Stance_ZYLJX_BIKINI.json";
        this.renderscale = 2.4F;
    }


    @Override
    public void setAnimation() {
        portraitState.setAnimation(0, "idle", true);
        InitializeStaticPortraitVar();
    }

    @Override
    public CharSelectInfo updateCharInfo(CharSelectInfo info) {
        info.relics.clear();
        info.relics.add(BlueGiant.ID);
        info.relics.add(Drapery.ID);
        return info;
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(BlueGiant.ID);
        retVal.add(Drapery.ID);
        return retVal;
    }
}
