package VUPShionMod.skins.sk.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.anastasia.FinFunnelUpgrade;
import VUPShionMod.cards.ShionCard.minami.TacticalLayout;
import VUPShionMod.cards.ShionCard.shion.Defend_Shion;
import VUPShionMod.cards.ShionCard.shion.Strafe;
import VUPShionMod.cards.ShionCard.shion.Strike_Shion;
import VUPShionMod.relics.Shion.BlueGiant;
import VUPShionMod.relics.Shion.ConcordArray;
import VUPShionMod.relics.Shion.ConcordCharge;
import VUPShionMod.relics.Shion.ConcordSnipe;
import VUPShionMod.skins.AbstractSkin;
import VUPShionMod.util.SaveHelper;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
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


        this.portraitAtlasPath = "VUPShionMod/characters/Shion/portrait_spine/Background_ZYLJX_idle";
        loadAnimation(0.76f);
        setAnimation();

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/Shion/corpse.png";

        this.atlasURL = "VUPShionMod/characters/Shion/animation/Stance_ZYLJX.atlas";
        this.jsonURL = "VUPShionMod/characters/Shion/animation/Stance_ZYLJX.json";
        this.renderScale = 2.4f;

    }

    @Override
    public void setPos() {
        portraitSkeleton.setPosition(-20.0f * Settings.scale, -360.0f * Settings.scale);
    }

    @Override
    public void setAnimation() {
        super.setAnimation();
        if (SaveHelper.safeCampfire)
            portraitState.setAnimation(0, "background_HX_idle_longtime", true);
        else
            portraitState.setAnimation(0, "background_idle_longtime", true);
    }

    @Override
    public void safeSwitch() {
        loadAnimation(0.76f);
        setAnimation();
    }

    @Override
    public CharSelectInfo updateCharInfo(CharSelectInfo info) {
        info.relics.clear();
        info.relics.add(BlueGiant.ID);
        info.relics.add(ConcordCharge.ID);
        return info;
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(BlueGiant.ID);
        retVal.add(ConcordCharge.ID);
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
