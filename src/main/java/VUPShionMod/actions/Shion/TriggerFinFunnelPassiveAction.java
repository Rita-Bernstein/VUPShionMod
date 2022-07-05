package VUPShionMod.actions.Shion;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.Shion.ReleaseFormMinamiPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class TriggerFinFunnelPassiveAction extends AbstractGameAction {
    private AbstractMonster target;
    private boolean random;
    private String forceFinFunnel = "None";
    private int loops = 1;

    private boolean isDoubleDamage = false;
    private boolean isMultiDamage = false;
    private boolean isApplyBleeding = false;
    private boolean isGainBlock = false;

    public TriggerFinFunnelPassiveAction(AbstractMonster target) {
        this.target = target;
        this.random = false;
    }

    public TriggerFinFunnelPassiveAction(AbstractMonster target, int loops) {
        this(target);
        this.loops = loops;
    }

    public TriggerFinFunnelPassiveAction(boolean random) {
        this.random = random;

    }

    public TriggerFinFunnelPassiveAction(boolean random, int loops) {
        this(random);
        this.loops = loops;
    }

    public TriggerFinFunnelPassiveAction(AbstractMonster target, String forceFinFunnel) {
        this.target = target;
        this.random = false;
        this.forceFinFunnel = forceFinFunnel;
    }

    public TriggerFinFunnelPassiveAction(AbstractMonster target, String forceFinFunnel, int loops) {
        this(target, forceFinFunnel);
        this.loops = loops;
    }

    public TriggerFinFunnelPassiveAction(String forceFinFunnel, boolean random) {
        this.random = random;
        this.forceFinFunnel = forceFinFunnel;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }


//        初始化敌人数组， 初始化浮游炮数组并获取。一些玩家power情况
        AbstractPlayer p = AbstractDungeon.player;
        AbstractFinFunnel f = AbstractPlayerPatches.AddFields.finFunnelManager.get(p).selectedFinFunnel;

//        强制转换浮游炮

        if (!forceFinFunnel.equals("None"))
            f = AbstractPlayerPatches.AddFields.finFunnelManager.get(p).getFinFunnel(forceFinFunnel);


//        计算循环
        if (p.hasPower(ReleaseFormMinamiPower.POWER_ID))
            this.loops *= p.getPower(ReleaseFormMinamiPower.POWER_ID).amount + 1;

        ArrayList<AbstractMonster> monsters = new ArrayList<>();


//            获取敌人
        for (int i = 0; i < this.loops; i++) {

            if (random || this.target == null) {
                AbstractMonster abstractMonster = AbstractDungeon.getRandomMonster();
                if (abstractMonster != null)
                    this.target = abstractMonster;
            }
            monsters.add(target);
        }


        for (int i = 0; i < this.loops; i++) {
            if (f != null && f.getLevel() >= 0) {
                f.powerToApply(this.target);
            } else {
                this.isDone = true;
                return;
            }
        }

        this.isDone = true;
    }
}
