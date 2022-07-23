package VUPShionMod.relics.Wangchuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class Nebula extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(Nebula.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/Nebula.png";
    private static final String OUTLINE_PATH = "img/relics/outline/Nebula.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public Nebula() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        flash();
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 3));
        addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, StiffnessPower.POWER_ID, 3));
    }
}