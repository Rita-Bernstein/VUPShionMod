package VUPShionMod.patches;

import VUPShionMod.ui.WingShield;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

public class WingShieldPatches {
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class DamagePatch {
        @SpireInsertPatch(rloc = 35, localvars = {"damageAmount"})
        public static void Insert(AbstractPlayer _instance, DamageInfo info, @ByRef int[] damageAmount) {
            damageAmount[0] = WingShield.getWingShield().onAttackedToChangeDamage(info, damageAmount[0]);
        }
    }
}
