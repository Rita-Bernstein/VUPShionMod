package VUPShionMod.skins.sk.WangChuan;

import VUPShionMod.relics.Nebula;
import VUPShionMod.relics.PureHeart;
import VUPShionMod.relics.StarQuakes;
import VUPShionMod.relics.TheRipple;
import VUPShionMod.skins.AbstractSkin;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class AquaWangChuan extends AbstractSkin {
    public AquaWangChuan() {
        this.portraitStatic_IMG = ImageMaster.loadImage("VUPShionMod/characters/WangChuan/portrait3.png");
        this.portraitAnimation_IMG = ImageMaster.loadImage("VUPShionMod/characters/WangChuan/portrait3.png");

        this.NAME = CardCrawlGame.languagePack.getUIString("VUPShionMod:AbstractWangChuanSkin").TEXT[2];
        this.DESCRIPTION = CardCrawlGame.languagePack.getUIString("VUPShionMod:AbstractWangChuanSkin").EXTRA_TEXT[2];

//        this.portraitAtlasPath = "VUPShionMod/characters/Shion/portrait_spine/Shion";

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/WangChuan/corpse.png";
        this.atlasURL = "VUPShionMod/characters/WangChuan/animation/STANCE_WANGCHUAN_BIKINI.atlas";
        this.jsonURL = "VUPShionMod/characters/WangChuan/animation/STANCE_WANGCHUAN_BIKINI.json";
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
        info.relics.add(TheRipple.ID);
        return info;
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Nebula.ID);
        retVal.add(StarQuakes.ID);
        retVal.add(TheRipple.ID);
        return retVal;
    }
}
