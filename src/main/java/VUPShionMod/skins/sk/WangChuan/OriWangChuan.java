package VUPShionMod.skins.sk.WangChuan;

import VUPShionMod.relics.DimensionSplitterAria;
import VUPShionMod.relics.Nebula;
import VUPShionMod.relics.StarQuakes;
import VUPShionMod.skins.AbstractSkin;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class OriWangChuan extends AbstractSkin {
    public OriWangChuan() {
        this.portraitStatic_IMG = ImageMaster.loadImage("VUPShionMod/characters/WangChuan/portrait.png");
        this.portraitAnimation_IMG = ImageMaster.loadImage("VUPShionMod/characters/WangChuan/portrait.png");

        this.NAME = CardCrawlGame.languagePack.getUIString("VUPShionMod:AbstractWangChuanSkin").TEXT[0];
        this.DESCRIPTION = CardCrawlGame.languagePack.getUIString("VUPShionMod:AbstractWangChuanSkin").EXTRA_TEXT[0];

//        this.portraitAtlasPath = "VUPShionMod/characters/Shion/portrait_spine/Shion";

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/WangChuan/corpse.png";
        this.atlasURL = "VUPShionMod/characters/WangChuan/animation/STANCE_WANGCHUAN_BREAK.atlas";
        this.jsonURL = "VUPShionMod/characters/WangChuan/animation/STANCE_WANGCHUAN_BREAK.json";
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
        info.relics.add(Nebula.ID);
        info.relics.add(StarQuakes.ID);
        return info;
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Nebula.ID);
        retVal.add(StarQuakes.ID);
        return retVal;
    }
}
