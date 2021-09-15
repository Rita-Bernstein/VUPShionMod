package VUPShionMod.actions;

import VUPShionMod.powers.HyperdimensionalLinksPower;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GainHyperdimensionalLinksAction extends AbstractGameAction {
    int amount = 0;

    public GainHyperdimensionalLinksAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        addToTop(new VFXAction(new AbstractAtlasGameEffect("Sparks 086 Impact Up MIX", AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY + 125.0f * Settings.scale,
                125.0f, 125.0f, 3.0f * Settings.scale, 2, false)));
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new HyperdimensionalLinksPower(AbstractDungeon.player, amount)));
        isDone = true;
    }
}
