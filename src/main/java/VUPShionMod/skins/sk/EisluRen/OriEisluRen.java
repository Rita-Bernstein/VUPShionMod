package VUPShionMod.skins.sk.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.Liyezhu.*;
import VUPShionMod.relics.EisluRen.ShieldHRzy1;
import VUPShionMod.relics.Liyezhu.MartyrVessel;
import VUPShionMod.skins.AbstractSkin;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.BurningBlood;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class OriEisluRen extends AbstractSkin {
    public static final String ID = OriEisluRen.class.getSimpleName();
    public static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(ID));

    public OriEisluRen() {
        super(ID,0);
        this.portrait_IMG = ImageMaster.loadImage("VUPShionMod/characters/EisluRen/portrait.png");
        this.name = uiString.TEXT[0];
        this.flavorText = uiString.TEXT[1];
        this.level = uiString.TEXT[2];
        this.unlockString = uiString.TEXT[3];

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/EisluRen/corpse.png";

        this.atlasURL = "VUPShionMod/characters/Liyezhu/animation/Stance_Lan.atlas";
        this.jsonURL = "VUPShionMod/characters/Liyezhu/animation/Stance_Lan.json";
        this.renderScale = 3.0f;

    }



    @Override
    public CharSelectInfo updateCharInfo(CharSelectInfo info) {
        info.relics.clear();
        info.relics.add(ShieldHRzy1.ID);
        return info;
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(ShieldHRzy1.ID);
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
        retVal.add(SoleAnthem.ID);
        retVal.add(TranquilPrayer.ID);
        retVal.add(JudgementOfSins.ID);

        return retVal;
    }
}
