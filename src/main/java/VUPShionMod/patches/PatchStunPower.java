package VUPShionMod.patches;

import VUPShionMod.monsters.PlagaAMundo;
import VUPShionMod.monsters.PlagaAMundoMinion;
import VUPShionMod.powers.DestroyPower;
import VUPShionMod.powers.ShockPower;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch(
        clz = StunMonsterPower.class,
        method = "onRemove"
)
public class PatchStunPower {
    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(StunMonsterPower __instance, byte ___moveByte) {
        if (__instance.owner instanceof PlagaAMundo) {
            AbstractMonster m = (AbstractMonster) __instance.owner;
            if ((___moveByte == 0 || ___moveByte == 99) && m.hasPower(ShockPower.POWER_ID)) {
                m.setMove((byte) 4, AbstractMonster.Intent.ATTACK, 150);
                m.createIntent();
                m.applyPowers();
            }
            return SpireReturn.Return(null);
        }

        if (__instance.owner instanceof PlagaAMundoMinion) {
            AbstractMonster m = (AbstractMonster) __instance.owner;
            if (___moveByte <= 2 && m.hasPower(DestroyPower.POWER_ID)) {
                m.setMove((byte) 4, AbstractMonster.Intent.ATTACK, 100);
                m.createIntent();
                m.applyPowers();
            }
            return SpireReturn.Return(null);
        }

        return SpireReturn.Continue();
    }
}
