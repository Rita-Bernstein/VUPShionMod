package VUPShionMod.actions;

import VUPShionMod.powers.SinPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

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

        if(AbstractDungeon.player.hasPower(SinPower.POWER_ID))
        AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player,AbstractDungeon.player.getPower(SinPower.POWER_ID).amount, DamageInfo.DamageType.HP_LOSS));
    }
}


