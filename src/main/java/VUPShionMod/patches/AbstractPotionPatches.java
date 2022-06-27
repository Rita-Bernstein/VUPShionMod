package VUPShionMod.patches;

import VUPShionMod.potions.ShoppingVoucher;
import VUPShionMod.potions.TransitionGenerator;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;

import java.util.ArrayList;


public class AbstractPotionPatches {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "loseGold"
    )
    public static class PlayerLoseGoldPatch {
        @SpireInsertPatch(rloc = 9)
        public static SpireReturn<Void> Insert(AbstractPlayer _instance, int goldAmount) {
            if (AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.ShopRoom) {
                for (AbstractPotion potion : AbstractDungeon.player.potions) {
                    if (potion.ID.equals(ShoppingVoucher.POTION_ID)) {
                        potion.flash();
                        potion.use(AbstractDungeon.player);
                        AbstractDungeon.topPanel.destroyPotion(potion.slot);
                        return SpireReturn.Return();
                    }
                }
            }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = MapRoomNode.class,
            method = "wingedIsConnectedTo"
    )
    public static class WingedIsConnectedTo {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Boolean> Insert(MapRoomNode _instance, MapRoomNode node) {
            ArrayList<MapEdge> edges = _instance.getEdges();
            for (MapEdge edge : edges) {
                if (node.y == edge.dstY && AbstractDungeon.player.hasPotion(TransitionGenerator.POTION_ID)) {
                    return SpireReturn.Return(true);
                }
            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = MapRoomNode.class,
            method = "update"
    )
    public static class MapRoomNodeUpdate {
        @SpireInsertPatch(rloc = 75)
        public static SpireReturn<Boolean> Insert(MapRoomNode _instance) {
            if (AbstractDungeon.player.hasPotion(TransitionGenerator.POTION_ID) && (AbstractDungeon.player.getRelic("WingedGreaves")).counter > 0) {
                (AbstractDungeon.player.getRelic("WingedGreaves")).counter++;
            }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = MapRoomNode.class,
            method = "update"
    )
    public static class MapRoomNodeUpdate2 {
        @SpireInsertPatch(rloc = 79, localvars = {"normalConnection", "wingedConnection"})
        public static SpireReturn<Boolean> Insert(MapRoomNode _instance, boolean normalConnection, boolean wingedConnection) {
            if (!normalConnection && wingedConnection) {
                for (AbstractPotion potion : AbstractDungeon.player.potions) {
                    if(potion.ID.equals(TransitionGenerator.POTION_ID)) {
                        potion.flash();
                        potion.use(AbstractDungeon.player);
                        AbstractDungeon.topPanel.destroyPotion(potion.slot);
                        break;
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = DungeonMapScreen.class,
            method = "updateControllerInput"
    )
    public static class MapUpdateControllerInput {
        @SpireInsertPatch(rloc = 31, localvars = {"flightMatters"})
        public static SpireReturn<Boolean> Insert(DungeonMapScreen _instance, @ByRef boolean[] flightMatters) {
            if (AbstractDungeon.player.hasPotion(TransitionGenerator.POTION_ID))
                flightMatters[0] = true;
            return SpireReturn.Continue();
        }
    }
}
