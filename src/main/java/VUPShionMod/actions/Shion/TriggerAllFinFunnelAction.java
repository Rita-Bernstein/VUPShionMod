package VUPShionMod.actions.Shion;

import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.character.Shion;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.InvestigationFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.Shion.BleedingPower;
import VUPShionMod.powers.Shion.GravitoniumPower;
import VUPShionMod.powers.Shion.PursuitPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class TriggerAllFinFunnelAction extends AbstractGameAction {
    private AbstractMonster target;
    private boolean random;
    private AbstractPlayer p = AbstractDungeon.player;


    public TriggerAllFinFunnelAction(AbstractMonster target) {
        this.target = target;
        this.random = false;
        this.duration = 1.0f;
    }

    public TriggerAllFinFunnelAction(boolean random) {
        this.random = random;
    }


    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() || AbstractPlayerPatches.AddFields.finFunnelManager.get(p).finFunnelList.isEmpty()) {
            this.isDone = true;
            return;
        }


//        初始化敌人数组，浮游炮数组。一些玩家power情况

        ArrayList<AbstractMonster> monsters = new ArrayList<>();
        ArrayList<AbstractFinFunnel> availableFinFunnel = new ArrayList<>();

//        获取敌人数组，浮游炮数组的具体信息

        for (AbstractFinFunnel f : AbstractPlayerPatches.AddFields.finFunnelManager.get(p).finFunnelList) {
            if (f.level > 0) {
                availableFinFunnel.add(f);
                if (!random) {
                    if (target == null)
                        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.miscRng);
                    monsters.add(target);
                } else {
                    AbstractMonster abstractMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.miscRng);
                    if (abstractMonster != null)
                        monsters.add(abstractMonster);
                }
            }
        }


        if (!availableFinFunnel.isEmpty() && !monsters.isEmpty()) {
//            结算被动效果
            for (int i = 0; i < availableFinFunnel.size(); i++) {
                availableFinFunnel.get(i).powerToApply(monsters.get(i));

            }
        }

        this.isDone = true;
    }
}