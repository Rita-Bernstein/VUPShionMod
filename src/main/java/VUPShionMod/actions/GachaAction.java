package VUPShionMod.actions;

import VUPShionMod.monsters.PlagaAMundoMinion;
import VUPShionMod.vfx.GachaEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.FrailPower;

public class GachaAction extends AbstractGameAction {
    private int block = 0;
    private int healing = 0;
    private int artifact = 0;
    private int frail = 0;
    private boolean applyPower = false;

    public GachaAction() {
        this.duration = this.startDuration = 3.0f;
    }


    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (!(AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    if (!mo.isDeadOrEscaped() && mo.id.equals(PlagaAMundoMinion.ID)) {
                        int count = AbstractDungeon.monsterRng.random(0, 3);
                        switch (count) {
                            case 0:
                                this.block++;
                                break;
                            case 1:
                                this.healing++;
                                break;
                            case 2:
                                this.artifact++;
                                break;
                            default:
                                this.frail++;
                        }
                        AbstractDungeon.effectList.add(new GachaEffect(mo, count));
                    }
                }
            }
        }

        if (this.duration <= 0.5f && !this.applyPower) {
            this.applyPower = true;
            applyBlock();
            applyHealing();
            applyArtifact();
            applyFrail();
        }

        tickDuration();
    }


    private void applyBlock() {
        if (this.block <= 0) return;

        int amount = 0;
        switch (this.block) {
            case 1:
                amount = 30;
                break;
            case 2:
                amount = 40;
                break;
            default:
                amount = 70;
                break;
        }

        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!mo.isDeadOrEscaped() && mo.id.equals(PlagaAMundoMinion.ID)) {
                addToBot(new GainBlockAction(mo, mo, amount));
            }
        }
    }

    private void applyHealing() {
        if (this.healing <= 0) return;
        int amount = 0;
        switch (this.healing) {
            case 1:
                amount = 20;
                break;
            case 2:
                amount = 30;
                break;
            default:
                amount = 50;
                break;
        }

        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            if (!mo.isDeadOrEscaped() && mo.id.equals(PlagaAMundoMinion.ID)) {
                addToBot(new HealAction(mo, mo, amount));
            }
        }

    }

    private void applyArtifact() {
        if (this.artifact <= 0) return;
        int amount = 0;
        switch (this.artifact) {
            case 1:
                amount = 1;
                break;
            case 2:
                amount = 2;
                break;
            default:
                amount = 3;
                break;
        }

        if (amount > 0)
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (!mo.isDeadOrEscaped() && mo.id.equals(PlagaAMundoMinion.ID)) {
                    addToBot(new ApplyPowerAction(mo, mo, new ArtifactPower(mo, amount)));
                }
            }

    }

    private void applyFrail() {
        if (this.frail <= 0) return;
        int amount = 0;
        switch (this.frail) {
            case 1:
                amount = 0;
                break;
            case 2:
                amount = 1;
                break;
            default:
                amount = 3;
                break;
        }

        if (amount > 0)
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, amount, true)));

    }


}
