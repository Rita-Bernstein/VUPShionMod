package VUPShionMod.actions.Liyezhu;

import VUPShionMod.actions.Common.GainMaxHPAction;
import VUPShionMod.monsters.Story.Ouroboros;
import VUPShionMod.monsters.Story.PlagaAMundo;
import VUPShionMod.monsters.Story.PlagaAMundoMinion;
import VUPShionMod.monsters.Story.TimePortal;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.Event.AbyssalCrux;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.EnemyData;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class FlickeringTipAction extends AbstractGameAction {
    private final int magicNumber;
    private final DamageInfo info;
    private static final float DURATION = 0.1F;

    public FlickeringTipAction(AbstractCreature target, DamageInfo info, int magicNumber) {
        this.info = info;
        setValues(target, info);
        this.magicNumber = magicNumber;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
    }


    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.NONE));
            this.target.damage(this.info);

            if (target instanceof AbstractMonster) {
                AbstractMonster monster = (AbstractMonster) target;
                if ((monster.isDying || monster.currentHealth <= 0) && !monster.halfDead && monster.type != AbstractMonster.EnemyType.BOSS && !monster.hasPower(MinionPower.POWER_ID)) {
                    if (!notPlagaMonster(monster)) {
                        monster.isDying = false;
                        addToTop(new EscapeAction(monster));
                    }
                    if (!AbstractDungeon.player.hasRelic(AbyssalCrux.ID))
                        if (EnergyPanelPatches.PatchEnergyPanelField.canUseSans.get(AbstractDungeon.overlayMenu.energyPanel)) {
                            EnergyPanelPatches.PatchEnergyPanelField.sans.get(AbstractDungeon.overlayMenu.energyPanel).addSan(this.amount);
                        }
                    AbstractDungeon.player.increaseMaxHp(magicNumber, true);
                }
            }

            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }


        tickDuration();
    }


    public boolean notPlagaMonster(AbstractMonster mo) {
        return !mo.id.equals(TimePortal.ID)
                && !mo.id.equals(PlagaAMundo.ID)
                && !mo.id.equals(PlagaAMundoMinion.ID)
                && !mo.id.equals(Ouroboros.ID);
    }

}