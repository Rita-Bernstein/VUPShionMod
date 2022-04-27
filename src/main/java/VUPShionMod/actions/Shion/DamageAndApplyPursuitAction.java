package VUPShionMod.actions.Shion;

import VUPShionMod.powers.PursuitPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class DamageAndApplyPursuitAction extends AbstractGameAction {
    private DamageInfo info;
    private boolean applyPower;
    private boolean gainBlock;
    private int times;
    private int pursuitAmount;

    public DamageAndApplyPursuitAction(AbstractCreature target, DamageInfo info,int times, boolean applyPower,boolean gainBlock,int pursuitAmount) {
        this.info = info;
        setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.applyPower = applyPower;
        this.gainBlock = gainBlock;
        this.times = times;
        this.pursuitAmount = pursuitAmount;
    }

    public DamageAndApplyPursuitAction(AbstractCreature target, DamageInfo info,int times, boolean applyPower,int pursuitAmount){
        this.info = info;
        setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.applyPower = applyPower;
        this.gainBlock = false;
        this.times = times;
        this.pursuitAmount = pursuitAmount;
    }


    public void update() {
        if (shouldCancelAction()) {
            this.isDone = true;

            return;
        }
        tickDuration();

        if (this.isDone) {
            for(int i = 0;i <times;i++){
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.FIRE, false));
                this.target.damage(this.info);
            }

            if (this.target.lastDamageTaken > 0) {
                if(gainBlock)
                    addToTop(new GainBlockAction(this.source, (int) Math.floor(this.target.lastDamageTaken)));
                if (!this.target.isDeadOrEscaped() && applyPower && pursuitAmount >0) {
                    addToTop(new ApplyPowerAction(this.target, this.target, new PursuitPower(this.target, pursuitAmount)));
                }
            }

            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            } else {
                addToTop(new WaitAction(0.1F));
            }
        }
    }
}


