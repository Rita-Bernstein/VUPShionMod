package VUPShionMod.patches;

import VUPShionMod.VUPShionMod;
import VUPShionMod.util.ShionNpc;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
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
            if (EnergyPanelPatches.isShionEXChar())
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
            if (EnergyPanelPatches.isShionEXChar()) {
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
            if (EnergyPanelPatches.isShionEXChar()) {
                String finalString = "";
                String[] TEXT = CardCrawlGame.languagePack.getCharacterString("Neow Event").TEXT;

                for (int i = 0; i < 4; i++)
                    if (msg.equals(TEXT[i])) {
                        finalString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("NeowEventTalkPatch")).TEXT[0];
                        break;
                    }

                for (int i = 4; i < 8; i++)
                    if (msg.equals(TEXT[i])) {
                        finalString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("NeowEventTalkPatch")).TEXT[1];
                    }

                if (msg.equals(TEXT[8]) || msg.equals(TEXT[9])) {
                    finalString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("NeowEventTalkPatch")).TEXT[2];
                }


                AbstractDungeon.effectList.add(new InfiniteSpeechBubble(
                        1500.0F * Settings.xScale, AbstractDungeon.floorY + 270.0F * Settings.yScale, finalString));
                return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }
    }
}
