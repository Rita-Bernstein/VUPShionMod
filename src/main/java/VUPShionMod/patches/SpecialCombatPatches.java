package VUPShionMod.patches;

import VUPShionMod.events.Newborn;
import VUPShionMod.monsters.PlagaAMundo;
import VUPShionMod.relics.AnastasiaNecklace;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.ui.panels.TopPanel;

import java.util.ArrayList;

public class SpecialCombatPatches {
    @SpirePatch(
            clz = ProceedButton.class,
            method = "goToTrueVictoryRoom"
    )
    public static class ProceedButtonPatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(ProceedButton _instance) {
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && !AbstractDungeon.bossKey.equals(PlagaAMundo.ID)) {
                if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion && AbstractDungeon.actNum >= 4 && Settings.isStandardRun()) {
                    goToShionEvent(_instance);
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }

    private static void goToShionEvent(ProceedButton __instance) {
//        CardCrawlGame.music.fadeOutBGM();
        CardCrawlGame.music.fadeOutTempBGM();
        AbstractDungeon.currMapNode.room = new EventRoom() {
            @Override
            public void onPlayerEntry() {
                AbstractDungeon.overlayMenu.proceedButton.hide();
                this.event = new Newborn();
                this.event.onEnterRoom();
            }
        };
        AbstractDungeon.getCurrRoom().onPlayerEntry();
        AbstractDungeon.rs = AbstractDungeon.RenderScene.EVENT;

        AbstractDungeon.combatRewardScreen.clear();
        AbstractDungeon.previousScreen = null;
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.nextRoomTransitionStart();
    }

/*
    @SpirePatch(
            clz = TheEnding.class,
            method = "generateSpecialMap"
    )
    public static class GenerateSpecialMapPatch {
        @SpireInsertPatch(rloc = 87)
        public static SpireReturn<Void> Insert(TheEnding _instance) {
            MapRoomNode shionBossCombatNode = new MapRoomNode(3, 5);
            shionBossCombatNode.room = new MonsterRoomBoss();
            ArrayList<MapRoomNode> row6 = new ArrayList<MapRoomNode>();
            row6.add(new MapRoomNode(0, 5));
            row6.add(new MapRoomNode(1, 5));
            row6.add(new MapRoomNode(2, 5));
            row6.add(shionBossCombatNode);
            row6.add(new MapRoomNode(4, 5));
            row6.add(new MapRoomNode(5, 5));
            row6.add(new MapRoomNode(6, 5));

            AbstractDungeon.map.add(row6);

            return SpireReturn.Continue();
        }
    }*/


    @SpirePatch(
            clz = TopPanel.class,
            method = "render"
    )
    public static class CGRenderPatch {
        @SpirePostfixPatch
        public static void Postfix(TopPanel _instance, SpriteBatch sb) {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof AnastasiaNecklace) {
                    ((AnastasiaNecklace) r).renderAbove(sb);
                }
            }
        }
    }

    @SpirePatch(
            cls = "actlikeit.patches.ContinueOntoHeartPatch",
            method = "Insert",
            optional = true
    )
    public static class ActLikeItContinueOntoHeartPatchPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(ProceedButton __instance) {
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && !AbstractDungeon.bossKey.equals(PlagaAMundo.ID)) {
                if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion && AbstractDungeon.actNum >= 4 && Settings.isStandardRun()) {
//                    goToShionEvent(__instance);
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }
}
