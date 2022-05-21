package VUPShionMod.skins.sk.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.anastasia.FinFunnelUpgrade;
import VUPShionMod.cards.ShionCard.minami.TacticalLayout;
import VUPShionMod.cards.ShionCard.shion.Defend_Shion;
import VUPShionMod.cards.ShionCard.shion.Strafe;
import VUPShionMod.cards.ShionCard.shion.Strike_Shion;
import VUPShionMod.relics.BlueGiant;
import VUPShionMod.relics.DimensionSplitterAria;
import VUPShionMod.skins.AbstractSkin;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class OriShion extends AbstractSkin {
    public static final String ID = OriShion.class.getSimpleName();
    public static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(ID));

    public OriShion() {
        super(ID,0);
        this.portrait_IMG = ImageMaster.loadImage("VUPShionMod/characters/Shion/portrait.png");
        this.name = uiString.TEXT[0];
        this.flavorText = uiString.TEXT[1];
        this.level = uiString.TEXT[2];
        this.unlockString = uiString.TEXT[3];

        this.portraitAtlasPath = "VUPShionMod/characters/Shion/portrait_spine/Shion";

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/Shion/corpse.png";

        this.atlasURL = "VUPShionMod/characters/Shion/animation/STANCE_ZY_YTD_without_weapon.atlas";
        this.jsonURL = "VUPShionMod/characters/Shion/animation/STANCE_ZY_YTD_without_weapon.json";
        this.renderScale = 2.4f;

        loadAnimation();
        setAnimation();
    }


    @Override
    public void setAnimation() {
        portraitState.setAnimation(0, "idle", true);
    }

    @Override
    public CharSelectInfo updateCharInfo(CharSelectInfo info) {
        info.relics.clear();
        info.relics.add(DimensionSplitterAria.ID);
        return info;
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(DimensionSplitterAria.ID);
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
