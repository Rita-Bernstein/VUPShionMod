package VUPShionMod.relics.Event;

import VUPShionMod.VUPShionMod;
import VUPShionMod.relics.AbstractShionRelic;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class OpticalCamouflage extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID("OpticalCamouflage");
    public static final String IMG_PATH = "img/relics/OpticalCamouflage.png";
    private static final String OUTLINE_PATH = "img/relics/outline/OpticalCamouflage.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public OpticalCamouflage() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new IntangiblePlayerPower(AbstractDungeon.player,1)));
    }
}
