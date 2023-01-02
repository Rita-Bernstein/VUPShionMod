package VUPShionMod.actions.Unique;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.monsters.HardModeBoss.Shion.AbstractShionBoss;
import VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels.AbstractBossFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.Shion.AttackOrderAlphaPower;
import VUPShionMod.powers.Shion.AttackOrderBetaPower;
import VUPShionMod.powers.Shion.AttackOrderDeltaPower;
import VUPShionMod.powers.Shion.AttackOrderGammaPower;
import VUPShionMod.vfx.Monster.Boss.AllBossFinFunnelBeamEffect;
import VUPShionMod.vfx.Shion.AllFinFunnelBeamEffect;
import VUPShionMod.vfx.Shion.AllFinFunnelSmallLaserEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;

import java.util.ArrayList;

public class TurnTriggerAllBossFinFunnelAction extends AbstractGameAction {
    private final AbstractShionBoss boss;


    public TurnTriggerAllBossFinFunnelAction(AbstractShionBoss boss) {
        this.boss = boss;
        this.duration = 1.0f;
    }


    @Override
    public void update() {
        if (this.boss.bossFinFunnels.isEmpty()) {
            this.isDone = true;
            return;
        }


        ArrayList<AbstractBossFinFunnel> availableFinFunnel = new ArrayList<>();


        for (AbstractBossFinFunnel f : this.boss.bossFinFunnels) {
            if (f.getLevel() > 0) {
                availableFinFunnel.add(f);
            }
        }

        if (availableFinFunnel.size() > 0) {
            addToBot(new VFXAction(new AllBossFinFunnelBeamEffect(availableFinFunnel, this.boss.flipHorizontal), 0.4f));


            for (AbstractBossFinFunnel f : availableFinFunnel) {
                addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(this.boss, f.getFinalDamage(), DamageInfo.DamageType.THORNS), AttackEffect.FIRE));
                f.powerToApply(AbstractDungeon.player);
            }


        }

        this.isDone = true;
    }
}
