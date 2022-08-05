package VUPShionMod.patches;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.relics.Event.Warlike;
import VUPShionMod.ui.FinFunnelCharge;
import VUPShionMod.ui.SwardCharge;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class GameStatsPatch {
    public static int lastDamageDeal = 0;
    public static int shionDeathCount = 0;
    public static int wangchuanDeathCount = 0;
    public static int wingShieldDamageReduceThisCombat = 0;
    public static int constrictedApplyThisCombat = 0;

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage"
    )
    public static class OnUnblockDamagePatch {
        @SpireInsertPatch(rloc = 61, localvars = {"damageAmount"})
        public static SpireReturn<Void> Insert(AbstractMonster _instance, DamageInfo info, int damageAmount) {
            lastDamageDeal = damageAmount;
            return SpireReturn.Continue();
        }
    }

    //    ========================================重置
    public static void turnBaseReset() {
        SwardCharge.getSwardCharge().resetCount();
    }

    public static void combatBaseReset() {
        lastDamageDeal = 0;
        wingShieldDamageReduceThisCombat = 0;
        constrictedApplyThisCombat = 0;

        for (AbstractFinFunnel finFunnel : FinFunnelManager.getFinFunnelList()) {
            finFunnel.resetLevelCombat();
        }

        FinFunnelCharge.getFinFunnelCharge().resetCount();

        SwardCharge.getSwardCharge().resetUpgrade();

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

    //
    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {AbstractCreature.class, AbstractCreature.class, AbstractPower.class, int.class, boolean.class, AbstractGameAction.AttackEffect.class}

    )
    public static class PowerStackPatch {
        @SpireInsertPatch(rloc = 24, localvars = {"amount", "duration"})
        public static SpireReturn<Void> Insert(ApplyPowerAction _instance,
                                               AbstractCreature target,
                                               AbstractCreature source,
                                               AbstractPower powerToApply,
                                               int stackAmount,
                                               boolean isFast,
                                               AbstractGameAction.AttackEffect effect, @ByRef int[] amount, @ByRef float[] duration) {
//            中毒相关


            if (powerToApply.ID.equals(ConstrictedPower.POWER_ID)) {
                constrictedApplyThisCombat += powerToApply.amount;
            }

            if (target.isPlayer)
                if (AbstractDungeon.player.hasRelic(Warlike.ID) &&
                        (powerToApply.ID.equals(StrengthPower.POWER_ID) || powerToApply.ID.equals(DexterityPower.POWER_ID))) {
                    AbstractDungeon.player.getRelic(Warlike.ID).flash();
                    powerToApply.amount += 1;
                    amount[0] = amount[0] + 1;
                }


            return SpireReturn.Continue();
        }
    }

}
