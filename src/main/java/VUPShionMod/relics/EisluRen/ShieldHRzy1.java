package VUPShionMod.relics.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.tempCards.FunnelMatrix;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShieldHRzy1 extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(ShieldHRzy1.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/BlueGiant.png";
    private static final String OUTLINE_PATH = "img/relics/outline/BlueGiant.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public ShieldHRzy1() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

}
