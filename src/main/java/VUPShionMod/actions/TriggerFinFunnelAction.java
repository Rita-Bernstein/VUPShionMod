package VUPShionMod.actions;

import VUPShionMod.character.Shion;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.ReleaseFormMinamiPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TriggerFinFunnelAction extends AbstractGameAction {
    private AbstractMonster target;
    private boolean random;
    private String forceFinFunnel = "None";

    public TriggerFinFunnelAction(AbstractMonster target) {
        this.target = target;
        this.random = false;
    }

    public TriggerFinFunnelAction(boolean random) {
        this.random = random;
    }

    public TriggerFinFunnelAction(AbstractMonster target, String forceFinFunnel) {
        this.target = target;
        this.random = false;
        this.forceFinFunnel = forceFinFunnel;
    }

    public TriggerFinFunnelAction(String forceFinFunnel, boolean random) {
        this.random = random;
        this.forceFinFunnel = forceFinFunnel;
    }

    @Override
    public void update() {
        if(!(AbstractDungeon.player instanceof Shion)){
            this.isDone = true;
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        if (random || this.target == null){
            AbstractMonster abstractMonster = AbstractDungeon.getRandomMonster();
            if(abstractMonster != null)
                this.target = abstractMonster;
        }


        AbstractFinFunnel f = AbstractPlayerPatches.AddFields.activatedFinFunnel.get(p);

        if(!forceFinFunnel.equals("None")){
            for (AbstractFinFunnel finFunnel : AbstractPlayerPatches.AddFields.finFunnelList.get(p)){
                if(finFunnel.id.equals(forceFinFunnel))
                    f = finFunnel;
            }
        }

        if (f.level >= 0 && this.target != null) {
            if (!this.target.isDeadOrEscaped())
                if (p.hasPower(ReleaseFormMinamiPower.POWER_ID))
                    f.fire(this.target, f.level, DamageInfo.DamageType.THORNS, p.getPower(ReleaseFormMinamiPower.POWER_ID).amount);
                else
                    f.fire(this.target, f.level, DamageInfo.DamageType.THORNS);
        }


        this.isDone = true;
    }
}
