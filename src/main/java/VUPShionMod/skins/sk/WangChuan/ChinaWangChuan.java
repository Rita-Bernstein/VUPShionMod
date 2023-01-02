package VUPShionMod.skins.sk.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.WangChuan.*;
import VUPShionMod.character.Shion;
import VUPShionMod.character.WangChuan;
import VUPShionMod.relics.Wangchuan.MagiaSwordRed;
import VUPShionMod.relics.Wangchuan.PrototypeCup;
import VUPShionMod.relics.Wangchuan.TheRipple;
import VUPShionMod.relics.Wangchuan.WaveSlasher;
import VUPShionMod.skins.AbstractSkin;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class ChinaWangChuan extends AbstractSkin {
    public static final String ID = ChinaWangChuan.class.getSimpleName();
    public static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(ID));

    public ChinaWangChuan(int index) {
        super(ID, index);
        this.portrait_IMG = ImageMaster.loadImage("VUPShionMod/characters/WangChuan/portrait4.sff");
        this.name = uiString.TEXT[0];
        this.flavorText = uiString.TEXT[1];
        this.level = uiString.TEXT[2];
        this.unlockString = uiString.TEXT[3];

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/WangChuan/corpse.png";

        this.atlasURL = "VUPShionMod/characters/WangChuan/animation/Wangchuan_ChinaTaoist.atlas";
        this.jsonURL = "VUPShionMod/characters/WangChuan/animation/Wangchuan_ChinaTaoist.json";
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
        info.relics.add(PrototypeCup.ID);
        info.relics.add(MagiaSwordRed.ID);
        return info;
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(PrototypeCup.ID);
        retVal.add(MagiaSwordRed.ID);
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
        retVal.add(MentalMotivationChop.ID);
        retVal.add(PreExecution.ID);

        return retVal;
    }

    @Override
    public void postCreateStartingDeck(CardGroup cardGroup) {
        for (AbstractCard card : cardGroup.group) {
            if (card.cardID.equals(PreExecution.ID)) {
                card.upgrade();
            }
        }
    }
}
