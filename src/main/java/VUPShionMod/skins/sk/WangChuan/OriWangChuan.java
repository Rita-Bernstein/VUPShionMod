package VUPShionMod.skins.sk.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.WangChuan.*;
import VUPShionMod.character.WangChuan;
import VUPShionMod.relics.Wangchuan.Nebula;
import VUPShionMod.relics.Wangchuan.StarQuakes;
import VUPShionMod.skins.AbstractSkin;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class OriWangChuan extends AbstractSkin {
    public static final String ID = OriWangChuan.class.getSimpleName();
    public static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(ID));

    public OriWangChuan(int index) {
        super(ID, index);
        this.portrait_IMG = ImageMaster.loadImage("VUPShionMod/characters/WangChuan/portrait.sff");
        this.name = uiString.TEXT[0];
        this.flavorText = uiString.TEXT[1];
        this.level = uiString.TEXT[2];
        this.unlockString = uiString.TEXT[3];

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/WangChuan/corpse.png";

        this.atlasURL = "VUPShionMod/characters/WangChuan/animation/STANCE_WANGCHUAN_BREAK.atlas";
        this.jsonURL = "VUPShionMod/characters/WangChuan/animation/STANCE_WANGCHUAN_BREAK.json";
        this.renderScale = 3.0F;

    }

    @Override
    public void initialize() {
        unlock();
    }


    public String getCharacterName() {
        return WangChuan.charStrings.NAMES[0];
    }

    public String getCharacterTiTleName() {
        return WangChuan.charStrings.NAMES[1];
    }

    public String getCharacterFlavorText() {
        return WangChuan.charStrings.TEXT[0];
    }

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

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(HiltBash.ID);
        retVal.add(HiltBash.ID);
        retVal.add(HiltBash.ID);
        retVal.add(HiltBash.ID);
        retVal.add(Slide.ID);
        retVal.add(Slide.ID);
        retVal.add(Slide.ID);
        retVal.add(Slide.ID);
        retVal.add(Sheathe.ID);
        retVal.add(GlandesMagicae.ID);

        return retVal;
    }
}
