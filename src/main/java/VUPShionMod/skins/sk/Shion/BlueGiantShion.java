package VUPShionMod.skins.sk.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.anastasia.FinFunnelUpgrade;
import VUPShionMod.cards.ShionCard.minami.TacticalLayout;
import VUPShionMod.cards.ShionCard.shion.Defend_Shion;
import VUPShionMod.cards.ShionCard.shion.Strafe;
import VUPShionMod.cards.ShionCard.shion.Strike_Shion;
import VUPShionMod.relics.BlueGiant;
import VUPShionMod.relics.Concord;
import VUPShionMod.relics.DimensionSplitterAria;
import VUPShionMod.skins.AbstractSkin;
import VUPShionMod.skins.sk.WangChuan.AquaWangChuan;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class BlueGiantShion extends AbstractSkin {
    public static final String ID = BlueGiantShion.class.getSimpleName();
    public static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(ID));

    public BlueGiantShion() {
        super(ID,1);
        this.portrait_IMG = ImageMaster.loadImage("VUPShionMod/characters/Shion/portrait2.png");
        this.name = uiString.TEXT[0];
        this.flavorText = uiString.TEXT[1];
        this.level = uiString.TEXT[2];
        this.unlockString = uiString.TEXT[3];

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/Shion/corpse.png";

        this.atlasURL = "VUPShionMod/characters/Shion/animation/Stance_ZYLJX.atlas";
        this.jsonURL = "VUPShionMod/characters/Shion/animation/Stance_ZYLJX.json";
        this.renderScale = 2.4f;

    }

    @Override
    public CharSelectInfo updateCharInfo(CharSelectInfo info) {
        info.relics.clear();
        info.relics.add(BlueGiant.ID);
        info.relics.add(Concord.ID);
        return info;
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(BlueGiant.ID);
        retVal.add(Concord.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike_Shion.ID);
        retVal.add(Strike_Shion.ID);
        retVal.add(Strike_Shion.ID);
        retVal.add(Strike_Shion.ID);
        retVal.add(Defend_Shion.ID);
        retVal.add(Defend_Shion.ID);
        retVal.add(Defend_Shion.ID);
        retVal.add(Defend_Shion.ID);
        retVal.add(TacticalLayout.ID);
        retVal.add(FinFunnelUpgrade.ID);
        retVal.add(Strafe.ID);

        return retVal;
    }
}
