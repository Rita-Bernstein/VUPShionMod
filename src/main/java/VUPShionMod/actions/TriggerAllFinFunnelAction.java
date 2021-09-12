package VUPShionMod.actions;

import VUPShionMod.character.Shion;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.InvestigationFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.*;
import VUPShionMod.vfx.AllFinFunnelBeamEffect;
import VUPShionMod.vfx.AllFinFunnelSmallLaserEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;

public class TriggerAllFinFunnelAction extends AbstractGameAction {
    private AbstractMonster target;
    private boolean random;
    private AbstractPlayer p = AbstractDungeon.player;
    private boolean isDoubleDamage = false;
    private boolean isMultiDamage = false;
    private boolean isApplyBleeding = false;
    private boolean isGainBlock = false;


    public TriggerAllFinFunnelAction(AbstractMonster target) {
        this.target = target;
        this.random = false;
        this.duration = 1.0f;
    }

    public TriggerAllFinFunnelAction(boolean random) {
        this.random = random;
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
        if (!(AbstractDungeon.player instanceof Shion)) {
            this.isDone = true;
            return;
        }

//        初始化敌人数组，浮游炮数组。一些玩家power情况
        getPower();
        ArrayList<AbstractMonster> monsters = new ArrayList<>();
        ArrayList<AbstractFinFunnel> availableFinFunnel = new ArrayList<>();

//        获取敌人数组，浮游炮数组的具体信息
        for (AbstractFinFunnel f : AbstractPlayerPatches.AddFields.finFunnelList.get(p)) {
            if (f.level > 0) {
                availableFinFunnel.add(f);
                if (!isMultiDamage) {
                    if (random || this.target == null) {
                        AbstractMonster abstractMonster = AbstractDungeon.getRandomMonster();
                        if (abstractMonster != null)
                            monsters.add(abstractMonster);
                    } else
                        monsters.add(target);
                }
            }
        }


        if (availableFinFunnel.size() > 0) {
//            特效部分
            if (isMultiDamage) {
                addToBot(new VFXAction(new AllFinFunnelBeamEffect(availableFinFunnel, p.flipHorizontal), 0.4f));
            } else {
                addToBot(new VFXAction(new AllFinFunnelSmallLaserEffect(availableFinFunnel, monsters), 0.3f));
                addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
            }

//攻击Action
            for (int i = 0; i < availableFinFunnel.size(); i++) {
                AbstractFinFunnel f = availableFinFunnel.get(i);

                if (isMultiDamage) {
                    addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(f.level, true),
                            DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));

                } else {
                    if (i < monsters.size()) {
                        AbstractMonster m = monsters.get(i);
                        if (isDoubleDamage)
                            addToBot(new DamageAction(m, new DamageInfo(p, f.level * 2, DamageInfo.DamageType.THORNS)));
                        else if (isGainBlock)
                            addToBot(new DamageAndGainBlockAction(m, new DamageInfo(p, f.level, DamageInfo.DamageType.THORNS), 1.0f));
                        else
                            addToBot(new DamageAction(m, new DamageInfo(p, f.level, DamageInfo.DamageType.THORNS)));
                    }
                }
            }

//            结算被动效果

            for (int i = 0; i < availableFinFunnel.size(); i++) {
                AbstractFinFunnel f = availableFinFunnel.get(i);
                if (isMultiDamage) {
                    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                        if (f instanceof GravityFinFunnel)
                            addToBot(new GainBlockAction(p, f.getFinalEffect(), true));
                        if (f instanceof InvestigationFinFunnel)
                            addToBot(new ApplyPowerAction(mo, p, new BleedingPower(mo, p, f.getFinalEffect())));
                        if (f instanceof PursuitFinFunnel)
                            addToBot(new ApplyPowerAction(mo, p, new PursuitPower(mo, f.getFinalEffect())));
                    }
                } else {
                    AbstractMonster m = monsters.get(i);
                    if (f instanceof GravityFinFunnel)
                        addToBot(new GainBlockAction(p, f.getFinalEffect(), true));

                    if (f instanceof InvestigationFinFunnel)
                        addToBot(new ApplyPowerAction(m, p, new BleedingPower(m, p, f.getFinalEffect())));

                    if (f instanceof PursuitFinFunnel)
                        addToBot(new ApplyPowerAction(m, p, new PursuitPower(m, f.getFinalEffect())));


//      额外给予流血效果


                    if (isApplyBleeding)
                        addToBot(new ApplyPowerAction(m, p, new BleedingPower(m, p, 2)));
                }

            }
        }

        this.isDone = true;
    }
}
