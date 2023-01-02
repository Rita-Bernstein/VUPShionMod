package VUPShionMod.actions.Unique;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.monsters.HardModeBoss.Shion.AbstractShionBoss;
import VUPShionMod.monsters.HardModeBoss.Shion.AquaShionBoss;
import VUPShionMod.monsters.HardModeBoss.Shion.MinamiShionBoss;
import VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels.AbstractBossFinFunnel;
import VUPShionMod.powers.Shion.MatrixAmplifyPower;
import VUPShionMod.powers.Shion.WideAreaLockingPower;
import VUPShionMod.ui.FinFunnelCharge;
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

public class BossFinFunnelMinionAction extends AbstractGameAction {
    private boolean createFinFunnelMini = false;
    private final ArrayList<AbstractBossFinFunnel> finFunnels = new ArrayList<>();

    private final boolean fullPower = false;
    private int skinIndex = 0;

    public BossFinFunnelMinionAction(AbstractShionBoss boss) {
        this.duration = 1.0f;
        for (int i = 0; i < 8; i++)
            finFunnels.add(AbstractShionBoss.getFinFunnelMini(boss));

//        this.fullPower = AbstractDungeon.player.hasPower(MatrixAmplifyPower.POWER_ID);

        if (boss instanceof AquaShionBoss)
            this.skinIndex = 2;

        if (boss instanceof MinamiShionBoss)
            this.skinIndex = 3;
    }


    @Override
    public void update() {
        if (!createFinFunnelMini) {
            this.createFinFunnelMini = true;

            for (int i = 0; i < 8; i++) {
                AbstractDungeon.effectList.add(new FinFunnelMinionEffect(AbstractDungeon.player, this.skinIndex, i, true));
            }
        }

        tickDuration();

        if (this.isDone) {
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.SKY));


            for (int i = 7; i >= 0; i--) {
                finFunnels.get(i).powerToApply(AbstractDungeon.player, 1.0f, true);
                addToTop(new DamageAction(AbstractDungeon.player,
                        new DamageInfo(null,
                                finFunnels.get(i).getFinalDamage(),
                                DamageInfo.DamageType.THORNS), AttackEffect.FIRE, true));
            }
        }
    }
}
