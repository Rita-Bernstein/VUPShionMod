package VUPShionMod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

@SuppressWarnings("unused")
public class EnergyPanelPatches {
    public static int energyUsedThisTurn = 0;
    public static final Color ENERGY_TEXT_COLOR = new Color(0.86F, 0.86F, 0.86F, 1.0F);

    @SpirePatch(
            clz = EnergyPanel.class,
            method = "useEnergy"
    )
    public static class PatchUseEnergy {
        public static void Prefix(int e) {
            energyUsedThisTurn += Math.min(e, EnergyPanel.totalCount);
        }
    }

    @SpirePatch(
            clz = EnergyPanel.class,
            method = "render"
    )
    public static class PatchRender {
        public static void Postfix(EnergyPanel panel, SpriteBatch sb) {
            AbstractDungeon.player.getEnergyNumFont().getData().setScale(EnergyPanel.fontScale * 0.7F);
            FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), Integer.toString(energyUsedThisTurn), panel.current_x, panel.current_y - 32.0F * Settings.scale, ENERGY_TEXT_COLOR);
        }
    }
}
