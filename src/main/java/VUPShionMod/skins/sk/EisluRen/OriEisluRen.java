package VUPShionMod.skins.sk.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.EisluRen.*;
import VUPShionMod.relics.EisluRen.ElfCore;
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
        super(ID, 0);
        this.portrait_IMG = ImageMaster.loadImage("VUPShionMod/characters/EisluRen/portrait.png");
        this.name = uiString.TEXT[0];
        this.flavorText = uiString.TEXT[1];
        this.level = uiString.TEXT[2];
        this.unlockString = uiString.TEXT[3];

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/EisluRen/corpse.png";

        this.atlasURL = "VUPShionMod/characters/EisluRen/animation/Stance_YSLR.atlas";
        this.jsonURL = "VUPShionMod/characters/EisluRen/animation/Stance_YSLR.json";
        this.renderScale = 2.4f;

    }

    @Override
    public void initialize() {
    }

    @Override
    public CharSelectInfo updateCharInfo(CharSelectInfo info) {
        info.relics.clear();
        info.relics.add(ShieldHRzy1.ID);
        info.relics.add(ElfCore.ID);
        return info;
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(ShieldHRzy1.ID);
        retVal.add(ElfCore.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Totsugeki.ID);
        retVal.add(Totsugeki.ID);
        retVal.add(Totsugeki.ID);
        retVal.add(Totsugeki.ID);
        retVal.add(Totsugeki.ID);
        retVal.add(Station.ID);
        retVal.add(Station.ID);
        retVal.add(Station.ID);
        retVal.add(Station.ID);
        retVal.add(Station.ID);
        retVal.add(WindArrow.ID);
        retVal.add(VineCatapult.ID);
        retVal.add(SummonElf.ID);

        return retVal;
    }
}
