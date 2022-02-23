package VUPShionMod.patches;

import VUPShionMod.events.Contact;
import VUPShionMod.events.Newborn;
import VUPShionMod.monsters.PlagaAMundo;
import VUPShionMod.powers.GravitoniumOverPower;
import VUPShionMod.relics.AnastasiaNecklace;
import VUPShionMod.relics.TrackingBeacon;
import basemod.CustomEventRoom;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.TinyChest;
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
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && !AbstractDungeon.bossKey.equals(PlagaAMundo.ID)) {
                if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion && (AbstractDungeon.actNum >= 4 || AbstractDungeon.id.equals("TheEnding"))) {
                    goToShionEvent(_instance, Newborn.ID);
                    return SpireReturn.Return(null);
                }
            }

            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && !AbstractDungeon.bossKey.equals(PlagaAMundo.ID)) {
                if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan && (AbstractDungeon.actNum >= 4 || AbstractDungeon.id.equals("TheEnding"))) {
                    goToShionEvent(_instance, Contact.ID);
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }

    private static void goToShionEvent(ProceedButton __instance, String eventId) {
        RoomEventDialog.optionList.clear();

        AbstractDungeon.eventList.add(0, eventId);

        MapRoomNode cur = AbstractDungeon.currMapNode;
        MapRoomNode node = new MapRoomNode(cur.x, cur.y);
        node.room = new CustomEventRoom();

        ArrayList<MapEdge> curEdges = cur.getEdges();
        for (MapEdge edge : curEdges) {
            node.addEdge(edge);
        }

        AbstractDungeon.player.releaseCard();
        AbstractDungeon.overlayMenu.hideCombatPanels();
        AbstractDungeon.previousScreen = null;
        AbstractDungeon.dynamicBanner.hide();
        AbstractDungeon.dungeonMapScreen.closeInstantly();
        AbstractDungeon.closeCurrentScreen();
        AbstractDungeon.topPanel.unhoverHitboxes();
        AbstractDungeon.fadeIn();
        AbstractDungeon.effectList.clear();
        AbstractDungeon.topLevelEffects.clear();
        AbstractDungeon.topLevelEffectsQueue.clear();
        AbstractDungeon.effectsQueue.clear();
        AbstractDungeon.dungeonMapScreen.dismissable = true;
        AbstractDungeon.nextRoom = node;
        AbstractDungeon.setCurrMapNode(node);
        AbstractDungeon.getCurrRoom().onPlayerEntry();
        AbstractDungeon.scene.nextRoom(node.room);
        AbstractDungeon.rs = node.room.event instanceof AbstractImageEvent ? AbstractDungeon.RenderScene.EVENT : AbstractDungeon.RenderScene.NORMAL;
    }


    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "render"
    )
    public static class CGRenderPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractDungeon _instance, SpriteBatch sb) {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof AnastasiaNecklace) {
                    ((AnastasiaNecklace) r).renderAbove(sb);
                }

                if (r instanceof TrackingBeacon) {
                    ((TrackingBeacon) r).renderAbove(sb);
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
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && !AbstractDungeon.bossKey.equals(PlagaAMundo.ID) && Loader.isModLoaded("actlikeit")) {
                if ((AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion || AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan)
                        && AbstractDungeon.actNum >= 4 && Settings.isStandardRun()) {
//                    goToShionEvent(__instance);
                    return SpireReturn.Return(null);
                }
            }
            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class ShionHelperUnDeadPatch {
        @SpireInsertPatch(rloc = 124)
        public static SpireReturn<Void> Insert(AbstractPlayer _instance, DamageInfo info) {
            if (_instance.hasPower(GravitoniumOverPower.POWER_ID) && _instance.currentHealth < 1) {
                _instance.halfDead = true;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = EventHelper.class,
            method = "roll",
            paramtypez = {Random.class}
    )
    public static class RollEventPatch {
        @SpireInsertPatch(rloc = 8)
        public static SpireReturn<EventHelper.RoomResult> Insert(Random eventRng) {
            if (AbstractDungeon.actNum >= 4 || AbstractDungeon.id.equals("TheEnding"))
                if ((AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion
                        || AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan)) {
                    AbstractRelic r = AbstractDungeon.player.getRelic(TinyChest.ID);
                    r.counter--;
                }

            return SpireReturn.Continue();
        }
    }
}
