package VUPShionMod.actions.Liyezhu;

import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.Liyezhu.DivineJudgmentPower;
import VUPShionMod.powers.Liyezhu.ExemptionPower;
import VUPShionMod.powers.Liyezhu.SinPower;
import VUPShionMod.stances.JudgeStance;
import VUPShionMod.stances.SpiritStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DuelSinAction extends AbstractGameAction {

    public DuelSinAction() {
    }

    public void update() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                if (!monster.isDeadOrEscaped()) {
                    if (monster.hasPower(SinPower.POWER_ID))
                        monster.damage(new DamageInfo(monster, monster.getPower(SinPower.POWER_ID).amount, DamageInfo.DamageType.THORNS));
                }
            }
        }

        if (AbstractDungeon.player.hasPower(SinPower.POWER_ID) && !AbstractDungeon.player.stance.ID.equals(SpiritStance.STANCE_ID)) {
            int num = AbstractDungeon.player.getPower(SinPower.POWER_ID).amount;

            AbstractPower divine = AbstractDungeon.player.getPower(DivineJudgmentPower.POWER_ID);
            if (divine != null) {
                divine.flash();
                num *= 2;
            }

            if (AbstractDungeon.player.stance.ID.equals(JudgeStance.STANCE_ID)) {
                num /= 2;
            }

            if (num > 0) {

                AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, num, DamageInfo.DamageType.THORNS));

                AbstractPower exp = AbstractDungeon.player.getPower(ExemptionPower.POWER_ID);
                if (exp != null) {
                    exp.flash();
                    AbstractDungeon.player.heal((int) (num * 1.2f), true);
                }
            }
        }

        if (!AbstractDungeon.player.isDeadOrEscaped()) {
            for (AbstractPower power : AbstractDungeon.player.powers) {
                if (power instanceof AbstractShionPower)
                    ((AbstractShionPower) power).onDuelSin();
            }

            for (AbstractCard card : AbstractDungeon.player.drawPile.group) {
                if (card instanceof AbstractVUPShionCard) {
                    ((AbstractVUPShionCard) card).onDuelSin();
                }
            }

            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractVUPShionCard) {
                    ((AbstractVUPShionCard) card).onDuelSin();
                }
            }

            for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                if (card instanceof AbstractVUPShionCard) {
                    ((AbstractVUPShionCard) card).onDuelSin();
                }
            }

            for (AbstractCard card : AbstractDungeon.player.exhaustPile.group) {
                if (card instanceof AbstractVUPShionCard) {
                    ((AbstractVUPShionCard) card).onDuelSin();
                }
            }

        }


        isDone = true;
    }
}


