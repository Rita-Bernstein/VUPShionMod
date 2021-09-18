package VUPShionMod.actions;

import VUPShionMod.character.Shion;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.InvestigationFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.*;
import VUPShionMod.vfx.FinFunnelBeamEffect;
import VUPShionMod.vfx.FinFunnelSmallLaserEffect;
import VUPShionMod.vfx.MonstersFinFunnelSmallLaserEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;

public class TriggerFinFunnelAction extends AbstractGameAction {
    private AbstractMonster target;
    private boolean random;
    private String forceFinFunnel = "None";
    private int loops = 1;

    private boolean isDoubleDamage = false;
    private boolean isMultiDamage = false;
    private boolean isApplyBleeding = false;
    private boolean isGainBlock = false;

    public TriggerFinFunnelAction(AbstractMonster target) {
        this.target = target;
        this.random = false;
    }

    public TriggerFinFunnelAction(AbstractMonster target, int loops) {
        this(target);
        this.loops = loops;
    }

    public TriggerFinFunnelAction(boolean random) {
        this.random = random;

    }

    public TriggerFinFunnelAction(boolean random, int loops) {
        this(random);
        this.loops = loops;
    }

    public TriggerFinFunnelAction(AbstractMonster target, String forceFinFunnel) {
        this.target = target;
        this.random = false;
        this.forceFinFunnel = forceFinFunnel;
    }

    public TriggerFinFunnelAction(AbstractMonster target, String forceFinFunnel, int loops) {
        this(target, forceFinFunnel);
        this.loops = loops;
    }

    public TriggerFinFunnelAction(String forceFinFunnel, boolean random) {
        this.random = random;
        this.forceFinFunnel = forceFinFunnel;
    }

    private void getPower() {
        if (AbstractDungeon.player.hasPower(AttackOrderAlphaPower.POWER_ID))
            isDoubleDamage = false;

        if (AbstractDungeon.player.hasPower(AttackOrderSpecialPower.POWER_ID))
            isMultiDamage = true;

        if (AbstractDungeon.player.hasPower(AttackOrderGammaPower.POWER_ID))
            isApplyBleeding = false;

        if (AbstractDungeon.player.hasPower(AttackOrderDeltaPower.POWER_ID))
            isGainBlock = false;
    }

    @Override
    public void update() {
        if (!(AbstractDungeon.player instanceof Shion)) {
            this.isDone = true;
            return;
        }

//        初始化敌人数组， 初始化浮游炮数组并获取。一些玩家power情况

        getPower();

        AbstractPlayer p = AbstractDungeon.player;
        AbstractFinFunnel f = AbstractPlayerPatches.AddFields.activatedFinFunnel.get(p);

//        强制转换浮游炮

        if (!forceFinFunnel.equals("None")) {
            for (AbstractFinFunnel finFunnel : AbstractPlayerPatches.AddFields.finFunnelList.get(p)) {
                if (finFunnel.id.equals(forceFinFunnel))
                    f = finFunnel;
            }
        }

//        计算循环
        if (p.hasPower(ReleaseFormMinamiPower.POWER_ID))
            this.loops *= p.getPower(ReleaseFormMinamiPower.POWER_ID).amount + 1;

        ArrayList<AbstractMonster> monsters = new ArrayList<>();

        for (int i = 0; i < this.loops; i++) {
//            获取敌人
            if (random || this.target == null) {
                AbstractMonster abstractMonster = AbstractDungeon.getRandomMonster();
                if (abstractMonster != null)
                    this.target = abstractMonster;
            }
            monsters.add(target);
        }

//                    特效单独弄
        if (f.level >= 0 && this.target != null) {
            if (!this.target.isDeadOrEscaped()) {
                if (isMultiDamage) {
                    addToBot(new VFXAction(AbstractDungeon.player, new FinFunnelBeamEffect(f, p.flipHorizontal), 0.4F));
                } else {
                    addToBot(new VFXAction(new MonstersFinFunnelSmallLaserEffect(f, monsters), 0.3F));
                    addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
                }
            }
        }


        for (int i = 0; i < this.loops; i++) {
            if (f.level >= 0 && this.target != null) {
                if (!this.target.isDeadOrEscaped()) {
//                    aoe的时候的造成伤害还有被动结算
                    if (isMultiDamage) {

                        addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(f.level, true),
                                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));

                        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                            if (f instanceof GravityFinFunnel)
                                addToBot(new GainBlockAction(p, f.getFinalEffect(), true));
                            if (f instanceof InvestigationFinFunnel)
                                addToBot(new ApplyPowerAction(mo, p, new BleedingPower(mo, p, f.getFinalEffect())));
                            if (f instanceof PursuitFinFunnel)
                                addToBot(new ApplyPowerAction(mo, p, new PursuitPower(mo, f.getFinalEffect())));
                        }

                    } else {

//                    对单的时候的造成伤害还有被动结算


                        if (isDoubleDamage)
                            addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, f.level * 2, DamageInfo.DamageType.THORNS)));
                        else if (isGainBlock)
                            addToBot(new DamageAndGainBlockAction(target, new DamageInfo(AbstractDungeon.player, f.level, DamageInfo.DamageType.THORNS), 1.0f));
                        else
                            addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, f.level, DamageInfo.DamageType.THORNS)));


                        if (f instanceof GravityFinFunnel)
                            addToBot(new GainBlockAction(p, f.getFinalEffect(), true));

                        if (f instanceof InvestigationFinFunnel)
                            addToBot(new ApplyPowerAction(target, p, new BleedingPower(target, p, f.getFinalEffect())));

                        if (f instanceof PursuitFinFunnel)
                            addToBot(new ApplyPowerAction(target, p, new PursuitPower(target, f.getFinalEffect())));

                        if (isApplyBleeding)
                            addToBot(new ApplyPowerAction(target, p, new BleedingPower(target, p, 2)));

                    }
                }
            } else {
                this.isDone = true;
                return;
            }
        }

        this.isDone = true;
    }
}
