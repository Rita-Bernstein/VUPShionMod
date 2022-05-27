package VUPShionMod.patches;

import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.relics.AbstractShionRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class AbstractRelicPatches {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class PatchRelicOnInflictDamage {
        @SpireInsertPatch(rloc = 84, localvars = "damageAmount")
        public static void Insert(AbstractPlayer _instance, DamageInfo info, int damageAmount) {
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof AbstractShionRelic)
                    ((AbstractShionRelic) r).onInflictDamage(info, damageAmount, _instance);
            }
        }
    }
}
