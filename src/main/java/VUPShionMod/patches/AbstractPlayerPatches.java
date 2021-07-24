package VUPShionMod.patches;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AbstractPlayerPatches {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = SpirePatch.CLASS
    )
    public static class AddFields {
        public static SpireField<List<AbstractFinFunnel>> finFunnelList = new SpireField<>(ArrayList::new);
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnRelics"
    )
    public static class PatchApplyStartOfTurnRelics {
        public static void Postfix(AbstractPlayer player) {
            for (AbstractFinFunnel funnel : AddFields.finFunnelList.get(player)) {
                funnel.atTurnStart();
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "update"
    )
    public static class PatchUpdate {
        public static void Postfix(AbstractPlayer player) {
            for (AbstractFinFunnel funnel : AddFields.finFunnelList.get(player)) {
                funnel.update();
            }
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "render"
    )
    public static class PatchRender {
        public static void Postfix(AbstractPlayer player, SpriteBatch sb) {
            for (AbstractFinFunnel funnel : AddFields.finFunnelList.get(player)) {
                funnel.render(sb);
            }
        }
    }
}
