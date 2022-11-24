package VUPShionMod.actions.Shion;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.Shion.*;
import VUPShionMod.vfx.Shion.AllFinFunnelBeamEffect;
import VUPShionMod.vfx.Shion.AllFinFunnelSmallLaserEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;

public class TurnTriggerFinFunnelsAction extends AbstractGameAction {
    private AbstractPlayer p = AbstractDungeon.player;
    private boolean isDoubleDamage = false;
    private boolean isMultiDamage = false;
    private boolean isApplyBleeding = false;
    private boolean isGainBlock = false;
    private boolean isCard = false;


    public TurnTriggerFinFunnelsAction(int amount,boolean isCard) {
        this.duration = 1.0f;
        this.amount = amount;
        this.isCard = isCard;
    }

    private void getPower() {
        if (AbstractDungeon.player.hasPower(AttackOrderAlphaPower.POWER_ID))
            isDoubleDamage = true;

        if (AbstractDungeon.player.hasPower(AttackOrderBetaPower.POWER_ID))
            isMultiDamage = true;

        if (AbstractDungeon.player.hasPower(AttackOrderGammaPower.POWER_ID))
            isApplyBleeding = true;

        if (AbstractDungeon.player.hasPower(AttackOrderDeltaPower.POWER_ID))
            isGainBlock = true;
    }


    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() || FinFunnelManager.getFinFunnelList().isEmpty()) {
            this.isDone = true;
            return;
        }


//        初始化敌人数组，浮游炮数组。一些玩家power情况
        getPower();
        ArrayList<AbstractFinFunnel> availableFinFunnelToAdd = new ArrayList<>();
        ArrayList<AbstractFinFunnel> availableFinFunnel = new ArrayList<>();

//        获取敌人数组，浮游炮数组的具体信息
        for (AbstractFinFunnel f : AbstractPlayerPatches.AddFields.finFunnelManager.get(p).finFunnelList) {
            if (f.getLevel() > 0) {
                availableFinFunnelToAdd.add(f);
            }
        }

        if (this.amount > availableFinFunnelToAdd.size()) {
            availableFinFunnel.addAll(availableFinFunnelToAdd);
        } else {
            for (int i = 0; i < this.amount; i++) {
                if (!availableFinFunnelToAdd.isEmpty()) {
                    int index = AbstractDungeon.cardRandomRng.random(availableFinFunnelToAdd.size() - 1);
                    availableFinFunnel.add(availableFinFunnelToAdd.get(index));
                    availableFinFunnelToAdd.remove(index);
                }
            }
        }


//            特效部分
        if (availableFinFunnel.size() > 0) {
            float effect = 1.0f;
            if (AbstractDungeon.player.hasPower(ReleaseFormMinamiPower.POWER_ID) && this.isCard) {
                effect += AbstractDungeon.player.getPower(ReleaseFormMinamiPower.POWER_ID).amount * 0.5f;
            }

            if (isMultiDamage) {
                addToBot(new VFXAction(new AllFinFunnelBeamEffect(availableFinFunnel, p.flipHorizontal), 0.4f));
            } else {
//                单体部分的实体代码在特效里面
                addToBot(new VFXAction(p, new AllFinFunnelSmallLaserEffect(availableFinFunnel,effect), 0.3f, true));
                addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
            }

//攻击Action
            if (isMultiDamage)
                for (AbstractFinFunnel f : availableFinFunnel) {
                    addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(f.getFinalDamage(), true),
                            DamageInfo.DamageType.THORNS, AttackEffect.FIRE, true));

//            结算被动效果
                    if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {

                            f.powerToApply(mo);
                        }
                }


        }

        this.isDone = true;
    }
}
