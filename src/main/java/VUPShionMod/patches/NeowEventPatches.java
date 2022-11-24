package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import VUPShionMod.events.ShionSpireHeart;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.util.SaveHelper;
import VUPShionMod.util.ShionNpc;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.vfx.InfiniteSpeechBubble;

public class NeowEventPatches {
    @SpirePatch(
            clz = NeowEvent.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {boolean.class}
    )
    public static class NPCPatch {
        @SpirePostfixPatch
        public static SpireReturn<Void> Postfix(NeowEvent _instance, boolean isDone) {
            if (shouldShionTalk())
                ReflectionHacks.setPrivate(_instance, NeowEvent.class, "npc", new ShionNpc());

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = NeowEvent.class,
            method = "playSfx"
    )
    public static class PlaySfxPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(NeowEvent _instance) {
            if (shouldShionTalk()) {
                return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = NeowEvent.class,
            method = "talk"
    )
    public static class TalkPatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(NeowEvent _instance, String msg) {
            if (shouldShionTalk()) {
                String finalString = "";
                String[] TEXT = CardCrawlGame.languagePack.getCharacterString("Neow Event").TEXT;
                String soundKey = "";
                for (int i = 0; i < 4; i++)
                    if (msg.equals(TEXT[i])) {
                        soundKey = "SHION_23";
                        finalString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("NeowEventTalkPatch")).TEXT[0];
                        break;
                    }

                for (int i = 4; i < 8; i++)
                    if (msg.equals(TEXT[i])) {
                        soundKey = "SHION_25";
                        finalString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("NeowEventTalkPatch")).TEXT[1];
                    }

                if (msg.equals(TEXT[8]) || msg.equals(TEXT[9])) {
                    soundKey = "SHION_31";
                    finalString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("NeowEventTalkPatch")).TEXT[2];
                }

                CardCrawlGame.sound.play(soundKey);
                AbstractDungeon.effectList.add(new InfiniteSpeechBubble(
                        1500.0F * Settings.xScale, AbstractDungeon.floorY + 270.0F * Settings.yScale, finalString));
                return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = VictoryRoom.class,
            method = "onPlayerEntry"
    )
    public static class VictoryRoomOnPlayerEntryPatch {
        @SpireInsertPatch(rloc =  6)
        public static SpireReturn<Void> Insert(VictoryRoom _instance) {
            if (shouldShionTalk()) {
                _instance.event = new ShionSpireHeart();

            }

            return SpireReturn.Continue();
        }
    }


    public static boolean shouldShionTalk() {
        return AbstractDungeon.player.chosenClass == AbstractPlayerEnum.EisluRen
                || AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion && SkinManager.getSkinCharacter(0).reskinCount ==3
                || SaveHelper.isTrainingMod;
    }
}
