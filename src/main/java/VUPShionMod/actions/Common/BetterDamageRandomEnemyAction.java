package VUPShionMod.actions.Common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BetterDamageRandomEnemyAction extends AbstractGameAction {
    private final DamageInfo info;
    private boolean fast = false;

    public BetterDamageRandomEnemyAction(DamageInfo info, AbstractGameAction.AttackEffect effect, boolean fast) {
        this.info = info;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = effect;
        this.fast = fast;
    }

    public BetterDamageRandomEnemyAction(DamageInfo info, AbstractGameAction.AttackEffect effect) {
        this(info, effect, false);
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.addToTop(new DamageAction(this.target, this.info, this.attackEffect, this.fast));
        }

        this.isDone = true;
    }
}
