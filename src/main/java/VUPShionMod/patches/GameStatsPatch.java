package VUPShionMod.patches;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.util.FinFunnelCharge;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GameStatsPatch {
    public static int lastDamageDeal = 0;
    public static int shionDeathCount = 0;
    public static int wangchuanDeathCount = 0;

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage"
    )
    public static class OnUnblockDamagePatch {
        @SpireInsertPatch(rloc = 61,localvars = {"damageAmount"})
        public static SpireReturn<Void> Insert(AbstractMonster _instance, DamageInfo info,int damageAmount) {
            lastDamageDeal = damageAmount;
            return SpireReturn.Continue();
        }
    }

    //    ========================================重置
    public static void turnBaseReset() {

    }

    public static void combatBaseReset() {
        lastDamageDeal = 0;

        for(AbstractFinFunnel finFunnel : FinFunnelManager.getFinFunnelList()){
            finFunnel.resetLevelCombat();
        }

        FinFunnelCharge.getFinFunnelCharge().resetCount();

    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "preBattlePrep"
    )
    public static class PreBattlePrepPatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(AbstractPlayer _instance) {
            combatBaseReset();
            turnBaseReset();
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "onVictory"
    )
    public static class OnVictoryPatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(AbstractPlayer _instance) {
            combatBaseReset();
            turnBaseReset();
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "resetPlayer"
    )
    public static class ResetPlayerPatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert() {
            combatBaseReset();
            turnBaseReset();
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = GameActionManager.class,
            method = "getNextAction"
    )
    public static class GetNextActionPatch {
        @SpireInsertPatch(rloc = 245)
        public static SpireReturn<Void> Insert(GameActionManager _instance) {
            turnBaseReset();
            return SpireReturn.Continue();
        }
    }

}
