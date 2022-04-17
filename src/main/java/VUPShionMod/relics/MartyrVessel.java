package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.tempCards.FunnelMatrix;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MartyrVessel extends CustomRelic {
    public static final String ID = VUPShionMod.makeID("MartyrVessel");
    public static final String IMG_PATH = "img/relics/MartyrVessel.png";
    private static final String OUTLINE_PATH = "img/relics/outline/MartyrVessel.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public MartyrVessel() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

}