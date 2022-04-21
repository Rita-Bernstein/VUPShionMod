package VUPShionMod.skins.sk.Liyezhu;

import VUPShionMod.relics.DimensionSplitterAria;
import VUPShionMod.relics.MartyrVessel;
import VUPShionMod.relics.Nebula;
import VUPShionMod.relics.StarQuakes;
import VUPShionMod.skins.AbstractSkin;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class OriLiyezhu extends AbstractSkin {
    public OriLiyezhu() {
        this.portraitStatic_IMG = ImageMaster.loadImage("VUPShionMod/characters/Liyezhu/portrait.png");
        this.portraitAnimation_IMG = ImageMaster.loadImage("VUPShionMod/characters/Liyezhu/portrait.png");

        this.NAME = CardCrawlGame.languagePack.getUIString("VUPShionMod:AbstractLiyezhuSkin").TEXT[0];
        this.DESCRIPTION = CardCrawlGame.languagePack.getUIString("VUPShionMod:AbstractLiyezhuSkin").EXTRA_TEXT[0];

//        this.portraitAtlasPath = "VUPShionMod/characters/Shion/portrait_spine/Shion";

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/Liyezhu/corpse.png";
        this.atlasURL = "VUPShionMod/characters/Liyezhu/animation/Stance_Lan.atlas";
        this.jsonURL = "VUPShionMod/characters/Liyezhu/animation/Stance_Lan.json";
        this.renderscale = 3.0F;
    }


//    @Override
//    public void setAnimation() {
//        portraitState.setAnimation(0, "idle", true);
//        InitializeStaticPortraitVar();
//    }

    @Override
    public CharSelectInfo updateCharInfo(CharSelectInfo info) {
        info.relics.clear();
        info.relics.add(MartyrVessel.ID);
        return info;
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(MartyrVessel.ID);
        return retVal;
    }
}
