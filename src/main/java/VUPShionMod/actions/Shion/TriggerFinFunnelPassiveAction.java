package VUPShionMod.actions.Shion;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.Shion.AttackOrderSpecialPower;
import VUPShionMod.powers.Shion.ReleaseFormMinamiPower;
import VUPShionMod.powers.Shion.WideAreaLockingPower;
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
    private boolean isCard = true;

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

    public TriggerFinFunnelPassiveAction(AbstractMonster target, String forceFinFunnel,boolean isCard) {
        this.target = target;
        this.random = false;
        this.forceFinFunnel = forceFinFunnel;
        this.isCard = isCard;
    }

    public TriggerFinFunnelPassiveAction(AbstractMonster target, String forceFinFunnel, int loops,boolean isCard) {
        this(target, forceFinFunnel,isCard);
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
//        if (p.hasPower(ReleaseFormMinamiPower.POWER_ID))
//            this.loops *= p.getPower(ReleaseFormMinamiPower.POWER_ID).amount + 1;
        float effect = 1.0f;
        if (AbstractDungeon.player.hasPower(ReleaseFormMinamiPower.POWER_ID) && this.isCard) {
            effect += AbstractDungeon.player.getPower(ReleaseFormMinamiPower.POWER_ID).amount * 0.5f;
        }

        ArrayList<AbstractMonster> monsters = new ArrayList<>();


//            获取敌人

        if ((AbstractDungeon.player.hasPower(WideAreaLockingPower.POWER_ID) && f.id.equals(PursuitFinFunnel.ID))){
            for (int i = 0; i < this.loops; i++){
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    f.powerToApply(mo);
                }
            }
        }else {
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
                    f.powerToApply(this.target, effect, false);
                } else {
                    this.isDone = true;
                    return;
                }
            }
        }

        this.isDone = true;
    }
}
