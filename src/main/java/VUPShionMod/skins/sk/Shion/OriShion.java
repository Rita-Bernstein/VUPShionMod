package VUPShionMod.skins.sk.Shion;

import VUPShionMod.relics.BlueGiant;
import VUPShionMod.relics.DimensionSplitterAria;
import VUPShionMod.skins.AbstractSkin;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class OriShion extends AbstractSkin {
    public OriShion() {
        this.portraitStatic_IMG = ImageMaster.loadImage("VUPShionMod/characters/Shion/portrait.png");
        this.portraitAnimation_IMG = ImageMaster.loadImage("VUPShionMod/characters/Shion/portrait.png");

        this.NAME = CardCrawlGame.languagePack.getUIString("VUPShionMod:AbstractShionSkin").TEXT[0];
        this.DESCRIPTION = CardCrawlGame.languagePack.getUIString("VUPShionMod:AbstractShionSkin").EXTRA_TEXT[0];

        this.portraitAtlasPath = "VUPShionMod/characters/Shion/portrait_spine/Shion";

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

    @Override
    public CharSelectInfo updateCharInfo(CharSelectInfo info) {
        info.relics.clear();
        info.relics.add(DimensionSplitterAria.ID);
        return info;
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(DimensionSplitterAria.ID);
        return retVal;
    }
}
