package VUPShionMod.skins.sk.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.WangChuan.*;
import VUPShionMod.character.Shion;
import VUPShionMod.character.WangChuan;
import VUPShionMod.relics.Wangchuan.TheRipple;
import VUPShionMod.relics.Wangchuan.WaveSlasher;
import VUPShionMod.skins.AbstractSkin;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class AquaWangChuan extends AbstractSkin {
    public static final String ID = AquaWangChuan.class.getSimpleName();
    public static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(ID));

    public AquaWangChuan(int index) {
        super(ID, index);
        this.portrait_IMG = ImageMaster.loadImage("VUPShionMod/characters/WangChuan/portrait3.sff");
        this.name = uiString.TEXT[0];
        this.flavorText = uiString.TEXT[1];
        this.level = uiString.TEXT[2];
        this.unlockString = uiString.TEXT[3];

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/WangChuan/corpse.png";

        this.atlasURL = "VUPShionMod/characters/WangChuan/animation/STANCE_WANGCHUAN_BIKINI.atlas";
        this.jsonURL = "VUPShionMod/characters/WangChuan/animation/STANCE_WANGCHUAN_BIKINI.json";
        this.renderScale = 3.0F;


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
        info.relics.add(TheRipple.ID);
        info.relics.add(WaveSlasher.ID);
        return info;
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(TheRipple.ID);
        retVal.add(WaveSlasher.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(HiltBash.ID);
        retVal.add(HiltBash.ID);
        retVal.add(HiltBash.ID);
        retVal.add(HiltBash.ID);
        retVal.add(Warp.ID);
        retVal.add(Warp.ID);
        retVal.add(Warp.ID);
        retVal.add(Warp.ID);
        retVal.add(CirrocumulusChop.ID);
        retVal.add(SpaceSlice.ID);

        return retVal;
    }
}
