package VUPShionMod.relics.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class InfiniteSushi extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(InfiniteSushi.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/InfiniteSushi.png";
    private static final String OUTLINE_PATH = "img/relics/outline/InfiniteSushi.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    private boolean gainedEnergy = false;

    public InfiniteSushi() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
        this.pulse = false;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atPreBattle() {
        if (!this.pulse) {
            beginPulse();
            this.pulse = true;
            this.gainedEnergy = false;
        }
    }

    @Override
    public void onLoseEnergy(int e, int energyUsed) {
        if (!this.gainedEnergy && EnergyPanel.totalCount - e <= 0) {
            this.gainedEnergy = true;
            this.pulse = false;
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void onTrigger() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
}
