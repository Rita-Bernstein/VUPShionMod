package VUPShionMod.actions.Common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class LoseMaxHPAction extends AbstractGameAction {
    private static final float DURATION = 0.33F;

    public LoseMaxHPAction(AbstractCreature target, AbstractCreature source, int amount) {
        this(target, source, amount, AbstractGameAction.AttackEffect.NONE);
    }


    public LoseMaxHPAction(AbstractCreature target, AbstractCreature source, int amount, AbstractGameAction.AttackEffect effect) {
        setValues(target, source, amount);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = Settings.ACTION_DUR_XFAST;
    }


    public void update() {
        if(this.target.maxHealth <=1){
            this.isDone =true;
            return;
        }

        if (this.duration == Settings.ACTION_DUR_XFAST && this.target.currentHealth > 0) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
        }

        tickDuration();

        if (this.isDone) {

            this.target.decreaseMaxHealth(this.amount);


            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            if (!Settings.FAST_MODE)
                addToTop(new WaitAction(0.1F));
        }
    }
}


