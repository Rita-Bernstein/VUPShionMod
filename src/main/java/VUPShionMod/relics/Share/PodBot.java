package VUPShionMod.relics.Share;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ApplyPowerToAllEnemyAction;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.function.Supplier;

public class PodBot extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(PodBot.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/PodBot.png";
    private static final String OUTLINE_PATH = "img/relics/outline/PodBot.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));


    public PodBot() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        flash();
        Supplier<AbstractPower> powerToApply = () -> new WeakPower(null, 1, false);
        addToBot(new ApplyPowerToAllEnemyAction(powerToApply));

    }

    @Override
    public boolean canSpawn() {
        return EnergyPanelPatches.isShionModChar();
    }
}
