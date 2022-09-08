package VUPShionMod.skins.sk.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.Liyezhu.*;
import VUPShionMod.relics.Liyezhu.Hymn;
import VUPShionMod.relics.Liyezhu.MartyrVessel;
import VUPShionMod.skins.AbstractSkin;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class OriLiyezhu extends AbstractSkin {
    public static final String ID = OriLiyezhu.class.getSimpleName();
    public static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(ID));

    public OriLiyezhu() {
        super(ID,0);
        this.portrait_IMG = ImageMaster.loadImage("VUPShionMod/characters/Liyezhu/portrait.png");
        this.name = uiString.TEXT[0];
        this.flavorText = uiString.TEXT[1];
        this.level = uiString.TEXT[2];
        this.unlockString = uiString.TEXT[3];

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/Liyezhu/corpse.png";

        this.atlasURL = "VUPShionMod/characters/Liyezhu/animation/Stance_Lan.atlas";
        this.jsonURL = "VUPShionMod/characters/Liyezhu/animation/Stance_Lan.json";
        this.renderScale = 3.0f;


    }

    @Override
    public void initialize() {
        unlock();
    }

    @Override
    public CharSelectInfo updateCharInfo(CharSelectInfo info) {
        info.relics.clear();
        info.relics.add(MartyrVessel.ID);
        info.relics.add(Hymn.ID);
        return info;
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(MartyrVessel.ID);
        retVal.add(Hymn.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(HolyLight.ID);
        retVal.add(HolyLight.ID);
        retVal.add(HolyLight.ID);
        retVal.add(HolyLight.ID);
        retVal.add(Barrier.ID);
        retVal.add(Barrier.ID);
        retVal.add(Barrier.ID);
        retVal.add(Barrier.ID);
        retVal.add(Barrier.ID);
        retVal.add(Sentence.ID);
        retVal.add(TranquilPrayer.ID);
        retVal.add(JudgementOfSins.ID);

        return retVal;
    }
}
