package VUPShionMod.actions.Liyezhu;

import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.Liyezhu.SinPower;
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
                    if(monster.hasPower(SinPower.POWER_ID))
                        monster.damage(new DamageInfo(monster,monster.getPower(SinPower.POWER_ID).amount, DamageInfo.DamageType.HP_LOSS));
                }
            }
        }

        if(AbstractDungeon.player.hasPower(SinPower.POWER_ID) && !AbstractDungeon.player.stance.ID.equals(SpiritStance.STANCE_ID))
        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player,AbstractDungeon.player.getPower(SinPower.POWER_ID).amount, DamageInfo.DamageType.HP_LOSS));

        if(!AbstractDungeon.player.isDeadOrEscaped()){
            for(AbstractPower power : AbstractDungeon.player.powers){
                if(power instanceof AbstractShionPower)
                    ((AbstractShionPower) power).onDuelSin();
            }

            for(AbstractCard card : AbstractDungeon.player.drawPile.group){
                if(card instanceof AbstractVUPShionCard){
                    ((AbstractVUPShionCard) card).onDuelSin();
                }
            }

            for(AbstractCard card : AbstractDungeon.player.hand.group){
                if(card instanceof AbstractVUPShionCard){
                    ((AbstractVUPShionCard) card).onDuelSin();
                }
            }

            for(AbstractCard card : AbstractDungeon.player.discardPile.group){
                if(card instanceof AbstractVUPShionCard){
                    ((AbstractVUPShionCard) card).onDuelSin();
                }
            }

            for(AbstractCard card : AbstractDungeon.player.exhaustPile.group){
                if(card instanceof AbstractVUPShionCard){
                    ((AbstractVUPShionCard) card).onDuelSin();
                }
            }

        }



        isDone =true;
    }
}


