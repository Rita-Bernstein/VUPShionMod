package VUPShionMod.relics.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.stances.PrayerStance;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;

public class MartyrVessel extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(MartyrVessel.class.getSimpleName());
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

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new ChangeStanceAction(PrayerStance.STANCE_ID));
        addToBot(new MakeTempCardInDiscardAction(new Miracle(),2));
    }
}
