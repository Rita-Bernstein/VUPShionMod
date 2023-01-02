package VUPShionMod.skins.sk.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.EisluRen.Totsugeki;
import VUPShionMod.cards.Liyezhu.Barrier;
import VUPShionMod.cards.ShionCard.minami.*;
import VUPShionMod.relics.Shion.ConcordCompanion;
import VUPShionMod.relics.Shion.PowerCore;
import VUPShionMod.skins.AbstractSkin;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class MinamiShion extends AbstractSkin {
    public static final String ID = MinamiShion.class.getSimpleName();
    public static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(ID));

    public MinamiShion(int index) {
        super(ID, index);
        this.portrait_IMG = ImageMaster.loadImage("VUPShionMod/characters/Shion/portrait.png");
        this.name = uiString.TEXT[0];
        this.flavorText = uiString.TEXT[1];
        this.level = uiString.TEXT[2];
        this.unlockString = uiString.TEXT[3];

        this.portraitAtlasPath = "VUPShionMod/characters/Shion/portrait_spine/XMZDY_break";
        loadAnimation(getScale(1.1f));
        setAnimation();

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/Shion/corpse.png";

        this.atlasURL = "VUPShionMod/characters/Shion/animation/Stance_NXM.atlas";
        this.jsonURL = "VUPShionMod/characters/Shion/animation/Stance_NXM.json";
        this.renderScale = 2.0f;

    }

    private float getScale(float scale) {
        if (Settings.isFourByThree)
            return scale * 1.3f;

        if (Settings.isSixteenByTen) {
            return scale * 1.12f;
        }

        return scale;
    }



    @Override
    public void setPos() {
        if (Settings.isFourByThree) {
            portraitSkeleton.setPosition(-100.0f * Settings.scale, 0.0f * Settings.scale);
        } else {
            portraitSkeleton.setPosition(-40.0f * Settings.scale, 0.0f * Settings.scale);
        }
    }


    @Override
    public void setAnimation() {
        portraitState.setAnimation(0, "idle", true);
    }

    public String getCharacterName() {
        return uiString.TEXT[4];
    }

    public String getCharacterFlavorText() {
        return uiString.TEXT[6];
    }

    public String getCharacterTiTleName() {
        return uiString.TEXT[5];
    }

    @Override
    public CharSelectInfo updateCharInfo(CharSelectInfo info) {
        info.relics.clear();
        info.relics.add(PowerCore.ID);
        info.relics.add(ConcordCompanion.ID);
        return info;
    }

    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.play("MINAMI_12");
    }

    @Override
    public void justSkinSelected(int lastSelectedCount) {
        doCharSelectScreenSelectEffect();
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(PowerCore.ID);
        retVal.add(ConcordCompanion.ID);
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
        retVal.add(Barrier.ID);
        retVal.add(Barrier.ID);
        retVal.add(Barrier.ID);
        retVal.add(Barrier.ID);
        retVal.add(Barrier.ID);

        retVal.add(SetupFinFunnel.ID);
        retVal.add(CalibrationDeployment.ID);
        retVal.add(MinamiHandCard.ID);
        retVal.add(MinamiReact.ID);

        return retVal;
    }

}
