package VUPShionMod.patches;

import VUPShionMod.powers.EnhancedSupportPower;
import VUPShionMod.powers.SupportArmamentPower;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;


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
            if (AbstractDungeon.player.hasPower(EnhancedSupportPower.POWER_ID) && source != null && source.isPlayer && target != source && powerToApply.ID.equals(SupportArmamentPower.POWER_ID)) {
                AbstractDungeon.player.getPower(EnhancedSupportPower.POWER_ID).flash();
                powerToApply.amount += AbstractDungeon.player.getPower(EnhancedSupportPower.POWER_ID).amount;
                amount[0] += AbstractDungeon.player.getPower(EnhancedSupportPower.POWER_ID).amount;
            }


            return SpireReturn.Continue();
        }
    }

}


