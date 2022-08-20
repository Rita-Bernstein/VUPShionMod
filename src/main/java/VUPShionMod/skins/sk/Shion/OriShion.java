package VUPShionMod.skins.sk.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.anastasia.FinFunnelUpgrade;
import VUPShionMod.cards.ShionCard.minami.TacticalLayout;
import VUPShionMod.cards.ShionCard.minami.TacticalLink;
import VUPShionMod.cards.ShionCard.shion.*;
import VUPShionMod.relics.Shion.ConcordSnipe;
import VUPShionMod.relics.Shion.DimensionSplitterAria;
import VUPShionMod.skins.AbstractSkin;
import VUPShionMod.util.SaveHelper;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
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

        this.portraitAtlasPath = "VUPShionMod/characters/Shion/victory/background_break";
        loadAnimation(1.16f);
        setAnimation();

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/Shion/corpse.png";

        this.atlasURL = "VUPShionMod/characters/Shion/animation/STANCE_ZY_YTD_without_weapon.atlas";
        this.jsonURL = "VUPShionMod/characters/Shion/animation/STANCE_ZY_YTD_without_weapon.json";
        this.renderScale = 2.0f;

    }

    @Override
    public void initialize() {
        unlock();
    }

    @Override
    public void setPos() {
        portraitSkeleton.setPosition(-40.0f * Settings.scale, -140.0f * Settings.scale);
    }

    @Override
    public void setAnimation() {
        super.setAnimation();
        if (SaveHelper.safeCampfire)
            portraitState.setAnimation(0, "idle_HX", true);
        else
            portraitState.setAnimation(0, "idle", true);
    }

    @Override
    public void safeSwitch() {
        loadAnimation(1.16f);
        setAnimation();
    }

    @Override
    public CharSelectInfo updateCharInfo(CharSelectInfo info) {
        info.relics.clear();
        info.relics.add(DimensionSplitterAria.ID);
        info.relics.add(ConcordSnipe.ID);
        return info;
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(DimensionSplitterAria.ID);
        retVal.add(ConcordSnipe.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike_Shion2.ID);
        retVal.add(Strike_Shion2.ID);
        retVal.add(Strike_Shion2.ID);
        retVal.add(Strike_Shion2.ID);
        retVal.add(Defend_Shion2.ID);
        retVal.add(Defend_Shion2.ID);
        retVal.add(Defend_Shion2.ID);
        retVal.add(Defend_Shion2.ID);
        retVal.add(TacticalLink.ID);
        retVal.add(FinFunnelUpgrade.ID);
        retVal.add(Strafe2.ID);

        return retVal;
    }
}
