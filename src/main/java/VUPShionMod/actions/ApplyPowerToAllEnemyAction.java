package VUPShionMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.function.Supplier;

public class ApplyPowerToAllEnemyAction extends AbstractGameAction {
    private Supplier<AbstractPower> powerToApply;

    public ApplyPowerToAllEnemyAction(Supplier<AbstractPower> powerToApply) {
        this.powerToApply = powerToApply;
    }

    @Override
    public void update() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                if (!monster.isDeadOrEscaped()) {
                    AbstractPower power = powerToApply.get();
                    power.owner = monster;
                    addToTop(new ApplyPowerAction(monster, AbstractDungeon.player, power));
                }
            }
        }
        isDone = true;
    }
}
