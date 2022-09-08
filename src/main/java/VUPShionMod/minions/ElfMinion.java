package VUPShionMod.minions;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.GainRefundChargeAction;
import VUPShionMod.powers.EisluRen.IronWallPower;
import VUPShionMod.powers.EisluRen.SpiritCloisterPower;
import VUPShionMod.powers.Monster.PlagaAMundo.FlyPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;

import java.util.Iterator;

public class ElfMinion extends AbstractPlayerMinion {
    public static final String ID = VUPShionMod.makeID(ElfMinion.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public int timesUpgraded = 0;
    public boolean cantSelected = false;

    public ElfMinion(int timesUpgraded) {
        super(NAME, ID, 88, 0.0F, -30.0F, 140.0F, 200.0F, null, 220.0F, 0.0f);


        this.timesUpgraded = timesUpgraded;

        this.damage.add(new DamageInfo(this, 3));
        this.damage.add(new DamageInfo(this, 15));
        this.damage.add(new DamageInfo(this, 20));

        this.type = MinionType.Elf;
        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;

        loadAnimation("VUPShionMod/characters/EisluRen/Elf/break_zhaohuanjingling.atlas",
                "VUPShionMod/characters/EisluRen/Elf/break_zhaohuanjingling.json", 8.0f);


        switch (timesUpgraded) {
            case 0:
                setHp(8);
                this.name = DIALOG[0];
                this.state.setAnimation(0, "idle_jingling", true);
                break;
            case 1:
                setHp(30);
                this.name = DIALOG[1];
                this.state.setAnimation(0, "idle_shangweijingling", true);
                break;
            case 2:
                setHp(30);
                this.name = DIALOG[2];
                this.state.setAnimation(0, "idle_qishi", true);
                break;
        }
        this.flipHorizontal = false;
    }

    private void reloadAnimation(int pre, int current) {
        if (pre >= current)
            return;

        if (pre == 0) {
            if (current == 1) {
                this.name = DIALOG[1];
                this.stateData.setMix("idle_jingling", "idle_shangweijingling", 0.0f);
                this.state.setAnimation(0, "idle_shangweijingling", true);
            } else {
                this.name = DIALOG[2];
                this.stateData.setMix("idle_jingling", "idle_qishi", 0.0f);
                this.state.setAnimation(0, "idle_qishi", true);
            }
        } else {
            this.name = DIALOG[2];
            this.stateData.setMix("idle_shangweijingling", "idle_qishi", 0.0f);
            this.state.setAnimation(0, "idle_qishi", true);
        }

    }


    @Override
    public void usePreBattleAction() {
        usePreBattleAction(this);
    }

    public void usePreBattleAction(ElfMinion minion) {
        switch (this.timesUpgraded) {
            case 1:
                addToBot(new ApplyPowerAction(this, this, new FlyPower(this, 2)));
                break;
            case 2:
                addToBot(new ApplyPowerAction(this, this, new IronWallPower(this) {
                    @Override
                    public void atStartOfTurn() {
                    }
                }));
                break;
        }
    }

    public void onSpiritCloisterPower(AbstractPower sp) {
        if (this.halfDead) {
            sp.flash();
            this.halfDead = false;
            this.currentHealth = 1;
            AbstractDungeon.effectsQueue.add(new HealEffect(this.hb.cX - this.animX, this.hb.cY, 1));
            healthBarUpdatedEvent();
            showHealthBar();

            rollMove();
            createIntent();
            applyPowers();
        }
    }

    public void summonElf(ElfMinion minion) {
        int pre = this.timesUpgraded;
        this.timesUpgraded = Math.max(minion.timesUpgraded, this.timesUpgraded);

        reloadAnimation(pre, this.timesUpgraded);

        increaseMaxHP(minion.maxHealth);

        rollMove();
        createIntent();
        applyPowers();

        healthBarUpdatedEvent();
        this.usePreBattleAction(minion);
    }


    public void increaseMaxHP(int amount) {
        this.maxHealth += amount;
        this.heal(amount, true);
    }

    @Override
    public void heal(int healAmount, boolean showEffect) {
        super.heal(healAmount, showEffect);
        this.cantSelected = false;
        if (this.halfDead) {
            this.halfDead = false;
            init();
            createIntent();
            applyPowers();
        }
    }

    @Override
    public void takeTurn() {
        if (this.halfDead) {
            return;
        }
        switch (this.nextMove) {
            case 0:
                if (this.targetMonster != null) {
                    addToBot(new AnimateFastAttackAction(this));
                    addToBot(new DamageAction(this.targetMonster, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE, true));
                    addToBot(new GainRefundChargeAction(1));
                }
                break;
            case 1:
                if (this.targetMonster != null) {
                    addToBot(new AnimateFastAttackAction(this));
                    addToBot(new DamageAction(this.targetMonster, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE, true));
                    addToBot(new GainRefundChargeAction(2));
                }
                break;
            case 2:
                addToBot(new AnimateFastAttackAction(this));
                addToBot(new DamageAllEnemiesAction(this, DamageInfo.createDamageMatrix(this.damage.get(2).base, false),
                        DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE, true));
                addToBot(new GainRefundChargeAction(3));
                break;
        }
    }

    @Override
    protected void getMove(int num) {
        if (this.halfDead) {
            setMove((byte) 3, MinionIntent.UNKNOWN);
            return;
        }
        switch (this.timesUpgraded) {
            default:
                setMove((byte) 0, MinionIntent.ATTACK_BUFF, this.damage.get(0).base);
                break;
            case 1:
                setMove((byte) 1, MinionIntent.ATTACK_BUFF, this.damage.get(1).base);
                break;
            case 2:
                setMove((byte) 2, MinionIntent.ATTACK_BUFF, this.damage.get(2).base);
                break;
        }
    }

    @Override
    public void changeState(String stateName) {
    }

    @Override
    public void die() {

    }


    @Override
    public void damage(DamageInfo info) {
        super.damage(info);

        if (this.currentHealth <= 0 && !this.halfDead) {
            for (AbstractPower p : this.powers) {
                p.onDeath();
            }

            for (Iterator<AbstractPower> s = this.powers.iterator(); s.hasNext(); ) {
                AbstractPower p = (AbstractPower) s.next();

                if (p.type == AbstractPower.PowerType.DEBUFF) {
                    s.remove();
                }
            }

            this.halfDead = true;
            setMove((byte) 3, MinionIntent.UNKNOWN);
            if (AbstractDungeon.player.hasPower(SpiritCloisterPower.POWER_ID)) {
                AbstractDungeon.player.getPower(SpiritCloisterPower.POWER_ID).flash();
                this.currentHealth = 1;
                this.halfDead = false;
                this.cantSelected = true;
                AbstractDungeon.effectsQueue.add(new HealEffect(this.hb.cX - this.animX, this.hb.cY, 1));
                healthBarUpdatedEvent();
                rollMove();
            }


            createIntent();
            applyPowers();
        }
    }
}
