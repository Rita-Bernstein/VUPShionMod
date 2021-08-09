package VUPShionMod.patches;

import VUPShionMod.powers.EnhancedWeaponPower;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ChangeApplyPowerPatch {

    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = {AbstractCreature.class, AbstractCreature.class, AbstractPower.class, int.class, boolean.class, AbstractGameAction.AttackEffect.class}

    )
    public static class ApplyPowerActionPatch {
        @SpireInsertPatch(rloc = 24, localvars = {"amount", "duration"})
        public static SpireReturn<Void> Insert(ApplyPowerAction _instance,
                                               AbstractCreature target,
                                               AbstractCreature source,
                                               AbstractPower powerToApply,
                                               int stackAmount,
                                               boolean isFast,
                                               AbstractGameAction.AttackEffect effect, @ByRef int[] amount, @ByRef float[] duration) {
//            强化支援
            if (AbstractDungeon.player.hasPower(EnhancedWeaponPower.POWER_ID) && source != null && source.isPlayer && target != source && powerToApply.ID.equals(HyperdimensionalLinksPower.POWER_ID)) {
                AbstractDungeon.player.getPower(EnhancedWeaponPower.POWER_ID).flash();
                powerToApply.amount += AbstractDungeon.player.getPower(EnhancedWeaponPower.POWER_ID).amount;
                amount[0] += AbstractDungeon.player.getPower(EnhancedWeaponPower.POWER_ID).amount;
            }


            return SpireReturn.Continue();
        }
    }

}


