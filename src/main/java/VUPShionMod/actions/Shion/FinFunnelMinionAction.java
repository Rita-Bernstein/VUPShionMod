package VUPShionMod.actions.Shion;

import VUPShionMod.cards.ShionCard.shion.MatrixAmplify;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.powers.Shion.MatrixAmplifyPower;
import VUPShionMod.powers.Shion.WideAreaLockingPower;
import VUPShionMod.util.FinFunnelCharge;
import VUPShionMod.vfx.Shion.FinFunnelMinionEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;

public class FinFunnelMinionAction extends AbstractGameAction {
    private boolean createFinFunnelMini = false;
    private ArrayList<AbstractFinFunnel> finFunnels = new ArrayList<>();
    private boolean isAoe = false;

    private boolean fullPower = false;

    public FinFunnelMinionAction(AbstractMonster target) {
        this.target = target;
        this.duration = 1.0f;
        for (int i = 0; i < 8; i++)
            finFunnels.add(FinFunnelManager.getFinFunnelMini());

        this.fullPower = AbstractDungeon.player.hasPower(MatrixAmplifyPower.POWER_ID);
    }

    private boolean isAoe(AbstractFinFunnel finFunnel) {
        if (finFunnel != null && finFunnel.id.equals(PursuitFinFunnel.ID)) {
            return AbstractDungeon.player.hasPower(WideAreaLockingPower.POWER_ID);
        }

        return false;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            isDone = true;
            return;
        }

        if (!createFinFunnelMini) {
            this.createFinFunnelMini = true;


            for (int i = 0; i < 8; i++) {
                AbstractDungeon.effectList.add(new FinFunnelMinionEffect(finFunnels.get(i), this.target, i, isAoe(finFunnels.get(i))));
            }
        }

        tickDuration();

        if (this.isDone) {
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SKY));
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    FinFunnelCharge.getFinFunnelCharge().loseCharge(16);
                    isDone = true;
                }
            });

            for (int i = 7; i >= 0; i--) {
                if (!isAoe(finFunnels.get(i))) {

                    finFunnels.get(i).powerToApply(this.target, this.fullPower ? 2.0f : 1.0f, true);
                    addToTop(new DamageAction(this.target,
                            new DamageInfo(null,
                                    this.fullPower ? finFunnels.get(i).getFinalDamage() * 2 : finFunnels.get(i).getFinalDamage(),
                                    DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE, true));
                } else {

                    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                        finFunnels.get(i).powerToApply(mo, this.fullPower ? 2.0f : 1.5f, true);
                    }

                    addToTop(new DamageAllEnemiesAction(null,
                            DamageInfo.createDamageMatrix(this.fullPower ? finFunnels.get(i).getFinalDamage() * 2 : finFunnels.get(i).getFinalDamage(),
                                    true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));

                }
            }
        }
    }
}
