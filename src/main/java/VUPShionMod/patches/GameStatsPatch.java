package VUPShionMod.patches;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.monsters.Rita.RitaShop;
import VUPShionMod.relics.Event.Warlike;
import VUPShionMod.ui.FinFunnelCharge;
import VUPShionMod.ui.SwardCharge;
import VUPShionMod.ui.WingShield;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;

public class GameStatsPatch {
    public static int lastAttackDamageDeal = 0;
    public static int shionDeathCount = 0;
    public static int wangchuanDeathCount = 0;
    public static int wingShieldDamageReduceThisCombat = 0;
    public static int constrictedApplyThisCombat = 0;
    public static int corGladiiLoseThisTurn = 0;
    public static int loadingCardTriggerCombat = 0;
    public static ArrayList<String> returnToHandList = new ArrayList<>();

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage"
    )
    public static class OnUnblockDamagePatch {
        @SpireInsertPatch(rloc = 61, localvars = {"damageAmount"})
        public static SpireReturn<Void> Insert(AbstractMonster _instance, DamageInfo info, int damageAmount) {
            if (info.type == DamageInfo.DamageType.NORMAL)
                lastAttackDamageDeal = damageAmount;
            return SpireReturn.Continue();
        }
    }

    //    ========================================重置
    public static void turnBaseReset() {
        SwardCharge.getSwardCharge().resetCount();
        WingShield.getWingShield().atStartOfTurn();

        corGladiiLoseThisTurn = 0;

        returnToHandList.clear();
    }

    public static void combatBaseReset() {
        lastAttackDamageDeal = 0;
        wingShieldDamageReduceThisCombat = 0;
        constrictedApplyThisCombat = 0;
        loadingCardTriggerCombat = 0;

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
    public static class PowerExtraStackPatch {
        @SpireInsertPatch(rloc = 24, localvars = {"amount", "duration"})
        public static SpireReturn<Void> Insert(ApplyPowerAction _instance,
                                               AbstractCreature target,
                                               AbstractCreature source,
                                               AbstractPower powerToApply,
                                               int stackAmount,
                                               boolean isFast,
                                               AbstractGameAction.AttackEffect effect, @ByRef int[] amount, @ByRef float[] duration) {
//            中毒相关


            if (powerToApply != null && powerToApply.ID.equals(ConstrictedPower.POWER_ID)) {
                constrictedApplyThisCombat += powerToApply.amount;
            }


            if (target != null && target.isPlayer)
                if (AbstractDungeon.player.hasRelic(Warlike.ID) &&
                        powerToApply != null && (powerToApply.ID.equals(StrengthPower.POWER_ID) || powerToApply.ID.equals(DexterityPower.POWER_ID))) {
                    if(powerToApply.amount >0) {
                        AbstractDungeon.player.getRelic(Warlike.ID).flash();
                        powerToApply.amount += 1;
                        amount[0] = amount[0] + 1;
                    }
                }


            if (target instanceof RitaShop && powerToApply != null) {
                if (!target.isDeadOrEscaped())
                    if (powerToApply.ID.equals(StunMonsterPower.POWER_ID)) {
                        AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(target, CardCrawlGame.languagePack.getUIString("ApplyPowerAction").TEXT[1]));
                        duration[0] -= Gdx.graphics.getDeltaTime();
                    }
            }


            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = "update"
    )
    public static class PowerStackStatsPatch {
        @SpireInsertPatch(rloc = 70)
        public static SpireReturn<Void> Insert(ApplyPowerAction _instance) {
            AbstractPower powerToApply = ReflectionHacks.getPrivate(_instance, ApplyPowerAction.class, "powerToApply");


            if (_instance.target != null && _instance.source != null)
                if (!_instance.target.isDeadOrEscaped() && _instance.source.isPlayer && _instance.target != _instance.source)
                    if (powerToApply.ID.equals(ConstrictedPower.POWER_ID)) {
                        constrictedApplyThisCombat += powerToApply.amount;
                    }

            return SpireReturn.Continue();
        }
    }

}
