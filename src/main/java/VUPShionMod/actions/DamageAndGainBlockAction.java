package VUPShionMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class DamageAndGainBlockAction extends AbstractGameAction {
    private DamageInfo info;
    private float blockScale;

    public DamageAndGainBlockAction(AbstractCreature target, DamageInfo info, float blockScale) {
        this.info = info;
        setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.blockScale = blockScale;
    }


    public void update() {
        if (shouldCancelAction()) {
            this.isDone = true;

            return;
        }
        tickDuration();

        if (this.isDone) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.FIRE, false));

            this.target.damage(this.info);
            if (this.target.lastDamageTaken > 0) {
                addToTop(new GainBlockAction(this.source, (int) Math.floor(this.target.lastDamageTaken * this.blockScale)));
//                if (this.target.hb != null) {
//                    addToTop(new VFXAction(new WallopEffect(this.target.lastDamageTaken, this.target.hb.cX, this.target.hb.cY)));
//                }
            }

            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            } else {
                addToTop(new WaitAction(0.1F));
            }
        }
    }
}


