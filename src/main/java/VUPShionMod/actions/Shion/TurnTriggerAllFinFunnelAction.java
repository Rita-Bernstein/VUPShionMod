package VUPShionMod.actions.Shion;

import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.character.Shion;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.InvestigationFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.Shion.*;
import VUPShionMod.vfx.AllFinFunnelBeamEffect;
import VUPShionMod.vfx.AllFinFunnelSmallLaserEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;

public class TurnTriggerAllFinFunnelAction extends AbstractGameAction {
    private AbstractMonster target;
    private boolean random;
    private AbstractPlayer p = AbstractDungeon.player;
    private boolean isDoubleDamage = false;
    private boolean isMultiDamage = false;
    private boolean isApplyBleeding = false;
    private boolean isGainBlock = false;


//    public TurnTriggerAllFinFunnelAction(AbstractMonster target) {
//        this.target = target;
//        this.random = false;
//        this.duration = 1.0f;
//    }

    public TurnTriggerAllFinFunnelAction(boolean random) {
        this.random = random;
        this.duration = 1.0f;
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
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }

        if (!(AbstractDungeon.player instanceof Shion)) {
            this.isDone = true;
            return;
        }

//        初始化敌人数组，浮游炮数组。一些玩家power情况
        getPower();
        ArrayList<AbstractFinFunnel> availableFinFunnel = new ArrayList<>();

//        获取敌人数组，浮游炮数组的具体信息
        for (AbstractFinFunnel f : AbstractPlayerPatches.AddFields.finFunnelList.get(p)) {
            if (f.level > 0) {
                availableFinFunnel.add(f);
            }
        }

//            特效部分
        if (availableFinFunnel.size() > 0) {
            if (isMultiDamage) {
                addToBot(new VFXAction(new AllFinFunnelBeamEffect(availableFinFunnel, p.flipHorizontal), 0.4f));
            } else {
//                单体部分的实体代码在特效里面
                addToBot(new VFXAction(p,new AllFinFunnelSmallLaserEffect(availableFinFunnel), 0.3f,true));
                addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
            }

//攻击Action
            if (isMultiDamage)
                for (AbstractFinFunnel f : availableFinFunnel) {
                    addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(f.getFinalDamage(), true),
                            DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));

//            结算被动效果
                    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                        if (f instanceof GravityFinFunnel && !mo.isDeadOrEscaped()) {
                            if (p.hasPower(GravitoniumPower.POWER_ID))
                                addToBot(new GainShieldAction(p, f.getFinalEffect(), true));
                            else
                                addToBot(new GainBlockAction(p, f.getFinalEffect(), true));
                        }

                        if (f instanceof InvestigationFinFunnel)
                            addToBot(new ApplyPowerAction(mo, p, new BleedingPower(mo, p, f.getFinalEffect())));

                        if (f instanceof PursuitFinFunnel)
                            addToBot(new ApplyPowerAction(mo, p, new PursuitPower(mo, f.getFinalEffect())));
                    }
                }

            this.isDone = true;
        }
    }
}
