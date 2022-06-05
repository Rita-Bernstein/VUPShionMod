package VUPShionMod.patches;

import VUPShionMod.powers.AbstractShionPower;
import basemod.BaseMod;
import basemod.patches.com.megacrit.cardcrawl.characters.AbstractPlayer.OnPlayerDamagedHook;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

public class AbstractPowerPatches {
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

}
