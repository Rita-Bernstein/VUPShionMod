package VUPShionMod.actions.Unique;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.monsters.HardModeBoss.Shion.AbstractShionBoss;
import VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels.AbstractBossFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.Shion.ReleaseFormMinamiPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class TriggerAllBossFinFunnelPassiveAction extends AbstractGameAction {
    private final AbstractShionBoss boss;

    public TriggerAllBossFinFunnelPassiveAction(AbstractShionBoss boss) {
        this.boss = boss;
        this.target = AbstractDungeon.player;
        this.duration = 1.0f;
    }


    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() || this.boss.bossFinFunnels.isEmpty()) {
            this.isDone = true;
            return;
        }

        ArrayList<AbstractBossFinFunnel> availableFinFunnel = new ArrayList<>();

//        获取敌人数组，浮游炮数组的具体信息

        for (AbstractBossFinFunnel f : this.boss.bossFinFunnels) {
            if (f.getLevel() > 0) {
                availableFinFunnel.add(f);
            }
        }


        float effect = 1.0f;
        if (!availableFinFunnel.isEmpty()) {
            for (AbstractBossFinFunnel abstractBossFinFunnel : availableFinFunnel) {
                abstractBossFinFunnel.powerToApply(this.target, effect, false);
            }
        }

        this.isDone = true;
    }
}
