package VUPShionMod.actions;

import VUPShionMod.cards.minami.ReleaseFormMinami;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.ReleaseFormMinamiPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TriggerAllFinFunnelAction extends AbstractGameAction {
    private AbstractMonster target;
    private boolean random;

    public TriggerAllFinFunnelAction(AbstractMonster target) {
        this.target = target;
        this.random = false;
    }

    public TriggerAllFinFunnelAction(Boolean random) {
        this.random = random;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        for (AbstractFinFunnel f : AbstractPlayerPatches.AddFields.finFunnelList.get(p)) {
            if (f.level >= 0 && this.target != null) {
                if (this.target.isDeadOrEscaped())
                    if (p.hasPower(ReleaseFormMinamiPower.POWER_ID))
                        f.fire(this.target, f.level, DamageInfo.DamageType.THORNS, p.getPower(ReleaseFormMinamiPower.POWER_ID).amount);
                    else
                        f.fire(this.target, f.level, DamageInfo.DamageType.THORNS);
            }
        }

        this.isDone = true;
    }
}
