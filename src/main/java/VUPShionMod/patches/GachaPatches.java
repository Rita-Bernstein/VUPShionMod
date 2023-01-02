package VUPShionMod.patches;

import VUPShionMod.actions.Unique.GachaAction;
import VUPShionMod.minions.MinionGroup;
import VUPShionMod.monsters.Story.PlagaAMundoMinion;
import VUPShionMod.powers.AbstractShionPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.MonsterStartTurnAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class GachaPatches {
    @SpirePatch(
            clz = MonsterStartTurnAction.class,
            method = "update"
    )
    public static class PatchMonsterStartTurnAction {
        @SpireInsertPatch(rloc = 2)
        public static SpireReturn<Void> Insert(MonsterStartTurnAction _instance) {
            boolean hasMonster = false;
            if (!(AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    if (!mo.isDeadOrEscaped() && mo.id.equals(PlagaAMundoMinion.ID)) {
                        hasMonster = true;
                    }
                }
            }

            if (hasMonster)
                AbstractDungeon.actionManager.addToBottom(new GachaAction());


            AbstractPlayerPatches.AddFields.playerMinions.get(AbstractDungeon.player).applyPreTurnLogic();

            return SpireReturn.Continue();
        }
    }
}
