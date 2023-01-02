package VUPShionMod.patches;

import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.stances.AbstractVUPShionStance;
import VUPShionMod.stances.SpiritStance;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;

public class AbstractStancePatches {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class PatchRelicOnInflictDamage {
        @SpireInsertPatch(rloc = 84, localvars = "damageAmount")
        public static void Insert(AbstractPlayer _instance, DamageInfo info, int damageAmount) {
            if (AbstractDungeon.player.stance instanceof AbstractVUPShionStance) {
                ((AbstractVUPShionStance) AbstractDungeon.player.stance).onInflictDamage(info, damageAmount, _instance);
            }
        }


    }

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "heal",
            paramtypez = {int.class, boolean.class}
    )
    public static class OnHealPatch {
        @SpireInsertPatch(rloc = 21)
        public static void Insert(AbstractCreature _instance, @ByRef int[] healAmount, boolean showEffect) {
            AbstractStance stance = AbstractDungeon.player.stance;
            if (stance instanceof AbstractVUPShionStance) {
                healAmount[0] = ((AbstractVUPShionStance) stance).onHeal(healAmount[0]);
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
            if (AbstractDungeon.player.stance instanceof AbstractVUPShionStance) {
                ((AbstractVUPShionStance) AbstractDungeon.player.stance).onVictory();
            }


            return SpireReturn.Continue();
        }
    }
}
