package VUPShionMod.actions.Shion;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.Shion.*;
import VUPShionMod.vfx.Shion.AllFinFunnelBeamEffect;
import VUPShionMod.vfx.Shion.AllFinFunnelSmallLaserEffect;
import VUPShionMod.vfx.Shion.FinFunnelBeamEffect;
import VUPShionMod.vfx.Shion.FinFunnelSmallLaserEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;

public class TurnTriggerFinFunnelAction extends AbstractGameAction {
    private AbstractMonster target;
    private boolean random = false;
    private final AbstractPlayer p = AbstractDungeon.player;
    private boolean isMultiDamage = false;
    private String forceFinFunnel = "None";

    private boolean isCard = false;

    public TurnTriggerFinFunnelAction(boolean random) {
        this.random = random;
        this.duration = 1.0f;
    }

    public TurnTriggerFinFunnelAction(AbstractMonster target, boolean isCard) {
        this.target = target;
        this.duration = 1.0f;
    }

    public TurnTriggerFinFunnelAction(AbstractMonster target, String forceFinFunnel) {
        this.target = target;
        this.duration = 1.0f;
        this.forceFinFunnel = forceFinFunnel;
    }


    public TurnTriggerFinFunnelAction(AbstractMonster target, boolean ALLFinFunnels, boolean isCard) {
        this.target = target;
        this.duration = 1.0f;
        this.forceFinFunnel = "ALL";
        this.isCard = isCard;
    }

    public TurnTriggerFinFunnelAction(AbstractMonster target, String forceFinFunnel, boolean isCard) {
        this(target, forceFinFunnel);
        this.isCard = isCard;
    }

    private void getPower() {
        if (AbstractDungeon.player.hasPower(AttackOrderBetaPower.POWER_ID))
            isMultiDamage = true;
    }


    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() || FinFunnelManager.getFinFunnelList().isEmpty()) {
            this.isDone = true;
            return;
        }


//        初始化敌人数组，浮游炮数组。一些玩家power情况
        getPower();
        ArrayList<AbstractFinFunnel> availableFinFunnel = new ArrayList<>();

//        获取敌人数组，浮游炮数组的具体信息
        for (AbstractFinFunnel f : FinFunnelManager.getFinFunnelList()) {
            if (f.getLevel() > 0) {
                availableFinFunnel.add(f);
            }
        }

//            特效部分
        if (availableFinFunnel.size() > 0) {

            float effect = 1.0f;
            if (AbstractDungeon.player.hasPower(ReleaseFormMinamiPower.POWER_ID) && this.isCard) {
                effect += AbstractDungeon.player.getPower(ReleaseFormMinamiPower.POWER_ID).amount * 0.5f;
            }

            ArrayList<AbstractFinFunnel> finFunnels = new ArrayList<>();
            if (this.forceFinFunnel.equals("ALL")) {
                finFunnels = FinFunnelManager.getFinFunnelList();
            } else {
                if (this.forceFinFunnel.equals("None")) {
                    finFunnels.add(availableFinFunnel.get(AbstractDungeon.miscRng.random(availableFinFunnel.size() - 1)));
                } else
                    finFunnels = AbstractPlayerPatches.AddFields.finFunnelManager.get(AbstractDungeon.player).getFinFunnels(this.forceFinFunnel);
            }


            if (finFunnels == null || finFunnels.isEmpty()) {
                this.isDone = true;
                return;
            }

//攻击Action


            if (isMultiDamage) {
                for (AbstractFinFunnel f : finFunnels) {

//            结算被动效果
                    if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {

                            f.powerToApply(mo, effect, true);
                        }

                    addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(f.getFinalDamage(), true),
                            DamageInfo.DamageType.THORNS, AttackEffect.FIRE, true));

                }
            } else {
                for (AbstractFinFunnel f : finFunnels) {
                    f.powerToApply(this.target, effect, true);

                    addToTop(new DamageAction(this.target, new DamageInfo(null,
                            f.getFinalDamage(), DamageInfo.DamageType.THORNS), AttackEffect.FIRE));
                }
            }


            if (isMultiDamage) {
                for (AbstractFinFunnel f : finFunnels)
                    addToTop(new VFXAction(new FinFunnelBeamEffect(f, p.flipHorizontal), 0.4f));
            } else {
                for (AbstractFinFunnel f : finFunnels) {
                    addToTop(new VFXAction(new BorderFlashEffect(Color.SKY)));
                    addToTop(new VFXAction(p, new FinFunnelSmallLaserEffect(f, this.target), 0.3f, true));
                }

            }


        }

        this.isDone = true;
    }


    private void triggerEffect(AbstractFinFunnel finFunnel) {
    }
}
