package VUPShionMod.patches;

import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.SubLunaPower;
import VUPShionMod.ui.SwardCharge;
import VUPShionMod.util.ShionImageHelper;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

public class AbstractPowerPatches {

    @SpirePatch(
            clz = CardCrawlGame.class,
            method = "create"
    )
    public static class initializePatch {
        @SpireInsertPatch(locator = TabNameLocator.class)
        public static SpireReturn<Void> Insert() {
            AbstractShionPower.initialize();
            return SpireReturn.Continue();
        }


        private static class TabNameLocator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "initialize");
                return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
            }
        }
    }


    @SpirePatch(
            clz = EmptyDeckShuffleAction.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PatchEmptyDeckShuffleAction {
        @SpirePostfixPatch
        public static void Postfix(EmptyDeckShuffleAction _instance) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof AbstractShionPower)
                    ((AbstractShionPower) p).onShuffle();
            }

            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                    if (!monster.isDeadOrEscaped()) {
                        for (AbstractPower p : monster.powers) {
                            if (p instanceof AbstractShionPower)
                                ((AbstractShionPower) p).onShuffle();
                        }
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz = ShuffleAllAction.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class PatchShuffleAllAction {
        @SpirePostfixPatch
        public static void Postfix(ShuffleAllAction _instance) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof AbstractShionPower)
                    ((AbstractShionPower) p).onShuffle();
            }

            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                    if (!monster.isDeadOrEscaped()) {
                        for (AbstractPower p : monster.powers) {
                            if (p instanceof AbstractShionPower)
                                ((AbstractShionPower) p).onShuffle();
                        }
                    }
                }
            }

        }
    }

    @SpirePatch(
            clz = ShuffleAction.class,
            method = "update"
    )
    public static class PatchShuffleAction {
        @SpirePrefixPatch
        public static void Prefix(ShuffleAction _instance) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof AbstractShionPower)
                    ((AbstractShionPower) p).onShuffle();
            }

            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                    if (!monster.isDeadOrEscaped()) {
                        for (AbstractPower p : monster.powers) {
                            if (p instanceof AbstractShionPower)
                                ((AbstractShionPower) p).onShuffle();
                        }
                    }
                }
            }

        }
    }


    @SpirePatch(
            clz = GameActionManager.class,
            method = "getNextAction"
    )
    public static class PreEndOfRoundPatch {
        @SpireInsertPatch(rloc = 215)
        public static SpireReturn<Void> Insert(GameActionManager _instance) {
            for (AbstractPower power : AbstractDungeon.player.powers) {
                if (power instanceof AbstractShionPower)
                    ((AbstractShionPower) power).preEndOfRound();
            }


            if (AbstractDungeon.getMonsters() != null)
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                        if (!monster.isDeadOrEscaped()) {
                            for (AbstractPower p : monster.powers) {
                                if (p instanceof AbstractShionPower)
                                    ((AbstractShionPower) p).preEndOfRound();
                            }
                        }
                    }
                }
            return SpireReturn.Continue();
        }
    }

//    @SpirePatch(
//            clz = UseCardAction.class,
//            method = SpirePatch.CONSTRUCTOR
//    )
//    public static class OnUseCardPatch {
//        @SpireInsertPatch(rloc = 15, localvars = {"targetCard"})
//        public static SpireReturn<Void> Insert(UseCardAction _instance, @ByRef AbstractCard[] targetCard) {
//            SwardCharge.getSwardCharge().onUseCard(targetCard[0],_instance);
//
//            return SpireReturn.Continue();
//        }
//    }

    @SpirePatch(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class OnAfterUseCardPatch {
        @SpireInsertPatch(rloc = 1, localvars = {"targetCard"})
        public static SpireReturn<Void> Insert(UseCardAction _instance, @ByRef AbstractCard[] targetCard) {
            SwardCharge.getSwardCharge().onAfterUseCard(targetCard[0], _instance);

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = AbstractCreature.class,
            method = "loseBlock",
            paramtypez = {int.class, boolean.class}
    )
    public static class OnLoseBlockPatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(AbstractCreature _instance, int amount, boolean noAnimation) {
            for (AbstractPower power : _instance.powers) {
                if (power instanceof AbstractShionPower) {
                    amount = ((AbstractShionPower) power).onLoseBlock(amount);
                }
            }


            return SpireReturn.Continue();
        }
    }


    @SpirePatch(
            clz = ReducePowerAction.class,
            method = "update"
    )
    public static class NotLosePowerPowerPatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(ReducePowerAction _instance) {
            String powerID = ReflectionHacks.getPrivate(_instance, ReducePowerAction.class, "powerID");
            AbstractPower powerInstance = ReflectionHacks.getPrivate(_instance, ReducePowerAction.class, "powerInstance");

            if (powerID != null) {
                if (powerID.equals(CorGladiiPower.POWER_ID) && AbstractDungeon.player.hasPower(SubLunaPower.POWER_ID)) {
                    AbstractDungeon.player.getPower(SubLunaPower.POWER_ID).flash();
                    _instance.isDone = true;
                    return SpireReturn.Return();
                }
            }

            if (powerInstance != null) {
                if (powerInstance.ID.equals(CorGladiiPower.POWER_ID) && AbstractDungeon.player.hasPower(SubLunaPower.POWER_ID)) {
                    AbstractDungeon.player.getPower(SubLunaPower.POWER_ID).flash();
                    _instance.isDone = true;
                    return SpireReturn.Return();
                }
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(
            clz = RemoveSpecificPowerAction.class,
            method = "update"
    )
    public static class NotRemovePowerPowerPatch {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Insert(RemoveSpecificPowerAction _instance) {
            String powerToRemove = ReflectionHacks.getPrivate(_instance, RemoveSpecificPowerAction.class, "powerToRemove");
            AbstractPower powerInstance = ReflectionHacks.getPrivate(_instance, RemoveSpecificPowerAction.class, "powerInstance");

            if (powerToRemove != null) {
                if (powerToRemove.equals(CorGladiiPower.POWER_ID) && AbstractDungeon.player.hasPower(SubLunaPower.POWER_ID)) {
                    AbstractDungeon.player.getPower(SubLunaPower.POWER_ID).flash();
                    _instance.isDone = true;
                    return SpireReturn.Return();
                }
            }

            if (powerInstance != null) {
                if (powerInstance.ID.equals(CorGladiiPower.POWER_ID) && AbstractDungeon.player.hasPower(SubLunaPower.POWER_ID)) {
                    AbstractDungeon.player.getPower(SubLunaPower.POWER_ID).flash();
                    _instance.isDone = true;
                    return SpireReturn.Return();
                }
            }

            return SpireReturn.Continue();
        }
    }

}
