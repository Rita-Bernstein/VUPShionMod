package VUPShionMod.skins.sk.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.anastasia.OverloadFortress;
import VUPShionMod.cards.ShionCard.kuroisu.BlackHand;
import VUPShionMod.cards.ShionCard.minami.FinFunnelActive;
import VUPShionMod.cards.ShionCard.minami.TacticalLayout;
import VUPShionMod.cards.ShionCard.minami.TacticalLink;
import VUPShionMod.cards.ShionCard.shion.*;
import VUPShionMod.character.Liyezhu;
import VUPShionMod.character.Shion;
import VUPShionMod.character.WangChuan;
import VUPShionMod.relics.Shion.ConcordArray;
import VUPShionMod.relics.Shion.ConcordSnipe;
import VUPShionMod.relics.Shion.Drapery;
import VUPShionMod.skins.AbstractSkin;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

public class AquaShion extends AbstractSkin {
    public static final String ID = AquaShion.class.getSimpleName();
    public static final UIStrings uiString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(ID));

    public AquaShion(int index) {
        super(ID, index);
        this.portrait_IMG = ImageMaster.loadImage("VUPShionMod/characters/Shion/portrait3.sff");
        this.name = uiString.TEXT[0];
        this.flavorText = uiString.TEXT[1];
        this.level = uiString.TEXT[2];
        this.unlockString = uiString.TEXT[3];

        this.SHOULDER1 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.SHOULDER2 = "VUPShionMod/characters/Shion/shoulder2.png";
        this.CORPSE = "VUPShionMod/characters/Shion/corpse.png";

        this.atlasURL = "VUPShionMod/characters/Shion/animation/Stance_ZYLJX_BIKINI.atlas";
        this.jsonURL = "VUPShionMod/characters/Shion/animation/Stance_ZYLJX_BIKINI.json";
        this.renderScale = 2.4f;


    }


    @Override
    public void setAnimation() {
        portraitState.setAnimation(0, "idle", true);
    }

    public String getCharacterName() {
        return Shion.charStrings.NAMES[0];
    }

    public String getCharacterTiTleName() {
        return Shion.charStrings.NAMES[1];
    }

    public String getCharacterFlavorText() {
        return Shion.charStrings.TEXT[0];
    }


    @Override
    public CharSelectInfo updateCharInfo(CharSelectInfo info) {
        info.relics.clear();
        info.relics.add(Drapery.ID);
        info.relics.add(ConcordArray.ID);
        return info;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.play("SHION_" + (3 + MathUtils.random(2)));
    }

    @Override
    public void justSkinSelected(int lastSelectedCount) {
        if (lastSelectedCount == 3) {
            doCharSelectScreenSelectEffect();
        }
    }

    @Override
    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Drapery.ID);
        retVal.add(ConcordArray.ID);
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
        retVal.add(OverloadFortress.ID);
        retVal.add(BlackHand.ID);

        return retVal;
    }

    @Override
    public void postCreateStartingDeck(CardGroup cardGroup) {
        for (AbstractCard card : cardGroup.group) {
            if (card.cardID.equals(OverloadFortress.ID)) {
                card.upgrade();
            }
        }
    }

}
