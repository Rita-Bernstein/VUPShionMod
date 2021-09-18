package VUPShionMod.patches;

import VUPShionMod.events.Newborn;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;

import java.util.ArrayList;

public class SpecialCombatPatches {
    @SpirePatch(
            clz = ProceedButton.class,
            method = "goToTrueVictoryRoom"
    )
    public static class ProceedButtonPatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(ProceedButton _instance) {
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && !AbstractDungeon.bossKey.equals(Champ.ID)) {
                if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion && AbstractDungeon.id.equals("TheEnding")) {
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
    }


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
    }
}
