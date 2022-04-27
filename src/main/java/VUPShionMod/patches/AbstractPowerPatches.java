package VUPShionMod.patches;

import VUPShionMod.powers.AbstractShionPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AbstractPowerPatches {
    @SpirePatch(
            clz = EmptyDeckShuffleAction.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PatchEmptyDeckShuffleAction {
        @SpirePostfixPatch
        public static void Postfix(EmptyDeckShuffleAction _instance) {
            for(AbstractPower p : AbstractDungeon.player.powers){
                if(p instanceof AbstractShionPower)
                    ((AbstractShionPower) p).onShuffle();
            }
        }
    }

    @SpirePatch(
            clz = ShuffleAllAction.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PatchShuffleAllAction {
        @SpirePostfixPatch
        public static void Postfix(ShuffleAllAction _instance) {
            for(AbstractPower p : AbstractDungeon.player.powers){
                if(p instanceof AbstractShionPower)
                    ((AbstractShionPower) p).onShuffle();
            }
        }
    }

    @SpirePatch(
            clz = ShuffleAction.class,
            method = "update"
    )
    public static class PatchShuffleAction {
        @SpirePrefixPatch
        public static void Prefix(ShuffleAction _instance) {
            for(AbstractPower p : AbstractDungeon.player.powers){
                if(p instanceof AbstractShionPower)
                    ((AbstractShionPower) p).onShuffle();
            }
        }
    }
}
