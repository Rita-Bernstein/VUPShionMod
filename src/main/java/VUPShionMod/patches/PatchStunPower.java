package VUPShionMod.patches;

import VUPShionMod.monsters.Story.PlagaAMundo;
import VUPShionMod.monsters.Story.PlagaAMundoMinion;
import VUPShionMod.powers.Monster.PlagaAMundo.DestroyPower;
import VUPShionMod.powers.Monster.PlagaAMundo.ShockPower;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
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



            if (m.hasPower(ShockPower.POWER_ID)) {
                if (___moveByte == 5) {
                    m.setMove((byte) 5, AbstractMonster.Intent.BUFF);
                } else {
                    m.setMove((byte) 4, AbstractMonster.Intent.ATTACK, m.damage.get(1).base);
                }
            } else {
                switch (___moveByte) {
                    case 99:
                        m.setMove((byte) 99, AbstractMonster.Intent.BUFF);
                        break;
                    case 98:
                        m.setMove((byte) 98, AbstractMonster.Intent.UNKNOWN);
                        break;
                    case 97:
                        m.setMove((byte) 97, AbstractMonster.Intent.UNKNOWN);
                        break;
                    default:
                        if (AbstractDungeon.ascensionLevel >= 4)
                            m.setMove((byte) 0, AbstractMonster.Intent.ATTACK, m.damage.get(0).base, 15, true);
                        else
                            m.setMove((byte) 0, AbstractMonster.Intent.ATTACK, m.damage.get(0).base, 13, true);
                        break;
                }
            }

            if(GameActionManager.turn >= 12){
                m.setMove((byte) 98, AbstractMonster.Intent.UNKNOWN);
            }

            m.createIntent();
            m.applyPowers();

            return SpireReturn.Return(null);
        }

        if (__instance.owner instanceof PlagaAMundoMinion) {
            AbstractMonster m = (AbstractMonster) __instance.owner;
            if (m.hasPower(DestroyPower.POWER_ID)) {
                if (___moveByte == 5) {
                    m.setMove((byte) 5, AbstractMonster.Intent.UNKNOWN);
                } else {
                    m.setMove((byte) 4, AbstractMonster.Intent.ATTACK, m.damage.get(3).base);
                }
            } else {
                switch (___moveByte) {
                    case 1:
                        m.setMove((byte) 0, AbstractMonster.Intent.ATTACK, m.damage.get(1).base, 15, true);
                        break;
                    case 2:
                        m.setMove((byte) 2, AbstractMonster.Intent.ATTACK, m.damage.get(2).base, 15, true);
                        break;
                    case 98:
                        m.setMove((byte) 98, AbstractMonster.Intent.UNKNOWN);
                        break;
                    case 99:
                        m.setMove((byte) 99, AbstractMonster.Intent.UNKNOWN);
                        break;
                    default:
                        m.setMove((byte) 0, AbstractMonster.Intent.ATTACK, m.damage.get(0).base, 15, true);
                        break;
                }
            }

            m.createIntent();
            m.applyPowers();
            return SpireReturn.Return(null);
        }


//        if(__instance.owner instanceof RitaShop){
//            AbstractMonster m = (AbstractMonster)__instance.owner;
//
//            if(___moveByte == 99){
//                m.setMove((byte) 99, AbstractMonster.Intent.UNKNOWN);
//                m.createIntent();
//                m.applyPowers();
//                return SpireReturn.Return(null);
//            }
//
//
//        }

        return SpireReturn.Continue();
    }
}
