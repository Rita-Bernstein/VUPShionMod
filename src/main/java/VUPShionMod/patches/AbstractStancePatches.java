package VUPShionMod.patches;

import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.stances.AbstractVUPShionStance;
import VUPShionMod.stances.SpiritStance;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AbstractStancePatches {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class PatchRelicOnInflictDamage {
        @SpireInsertPatch(rloc = 84, localvars = "damageAmount")
        public static void Insert(AbstractPlayer _instance, DamageInfo info, int damageAmount) {
            if(AbstractDungeon.player.stance instanceof AbstractVUPShionStance){
                ((AbstractVUPShionStance) AbstractDungeon.player.stance).onInflictDamage(info, damageAmount, _instance);
            }
        }


    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "onVictory"
    )
    public static class OnVictoryPatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(AbstractPlayer _instance) {
            if(AbstractDungeon.player.stance instanceof AbstractVUPShionStance){
                ((AbstractVUPShionStance) AbstractDungeon.player.stance).onVictory();
            }


            return SpireReturn.Continue();
        }
    }
}
