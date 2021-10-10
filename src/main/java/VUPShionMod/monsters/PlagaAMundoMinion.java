package VUPShionMod.monsters;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.CustomWaitAction;
import VUPShionMod.powers.*;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DemonFormPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PlagaAMundoMinion extends CustomMonster {
    public static final String ID = VUPShionMod.makeID("PlagaAMundoMinion");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    private int baseAttackTimes = 15;
    private boolean isGunMode = false;
    private boolean isFirstGunMode = true;

    public PlagaAMundoMinion(float x, float y, float scale) {
        super(NAME, ID, 88, -15.0F, 160.0F / scale, 300.0F / scale, 280.0F / scale, null, x, y);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(3000);
        } else {
            setHp(2500);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 4));
            this.damage.add(new DamageInfo(this, 5));
            this.damage.add(new DamageInfo(this, 6));
            this.damage.add(new DamageInfo(this, 100));
        } else {
            this.damage.add(new DamageInfo(this, 4));
            this.damage.add(new DamageInfo(this, 5));
            this.damage.add(new DamageInfo(this, 6));
            this.damage.add(new DamageInfo(this, 100));
        }

//        if (AbstractDungeon.ascensionLevel >= 4)
//            baseAttackTimes += 2;


        this.type = EnemyType.BOSS;
        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;


        loadAnimation("VUPShionMod/img/monsters/PlagaAMundo/Idle_BOSS.atlas", "VUPShionMod/img/monsters/PlagaAMundo/Idle_BOSS.json", 2.5f * scale);


        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.flipHorizontal = false;
    }


    public void usePreBattleAction() {
//        CardCrawlGame.music.unsilenceBGM();
//        AbstractDungeon.scene.fadeOutAmbiance();
//        AbstractDungeon.getCurrRoom().playBgmInstantly("fight");
        (AbstractDungeon.getCurrRoom()).cannotLose = true;
        addToBot(new ApplyPowerAction(this, this, new LifeLinkPower(this)));
        addToBot(new ApplyPowerAction(this, this, new IterativePower(this)));

        if (AbstractDungeon.ascensionLevel >= 19)
            addToBot(new ApplyPowerAction(this, this, new DefectPower(this, 2)));
        else
            addToBot(new ApplyPowerAction(this, this, new DefectPower(this, 1)));

        addToBot(new ApplyPowerAction(this, this, new StrengthenPower(this, 3)));

    }


    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, "Attack2"));
                for (int i = 0; i < baseAttackTimes; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE, true));
                break;
            case 1:
                addToBot(new ChangeStateAction(this, "Attack2"));
                for (int i = 0; i < baseAttackTimes; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE, true));
                break;
            case 2:
                addToBot(new ChangeStateAction(this, "Attack2"));
                for (int i = 0; i < baseAttackTimes; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.FIRE, true));
                break;
            case 4:
                addToBot(new ChangeStateAction(this, "Attack1"));
                addToBot(new CustomWaitAction(1.0f));
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.FIRE));
                break;
            case 5:
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 25), 25));
                break;
            case 98:
                addToBot(new HealAction(this, this, this.maxHealth));
                addToBot(new ChangeStateAction(this, "REVIVE"));
                addToBot(new ApplyPowerAction(this, this, new LifeLinkPower(this)));
                addToBot(new ApplyPowerAction(this, this, new IterativePower(this)));
                if (AbstractDungeon.ascensionLevel >= 19)
                    addToBot(new ApplyPowerAction(this, this, new DefectPower(this, 2)));
                else
                    addToBot(new ApplyPowerAction(this, this, new DefectPower(this, 1)));

                addToBot(new ApplyPowerAction(this, this, new StrengthenPower(this, 3)));
                break;
            case 99:
                break;
        }

        addToBot(new RollMoveAction(this));

    }


    protected void getMove(int num) {
        if (isGunMode) {
            if (lastMove((byte) 4)) {
                setMove((byte) 5, Intent.BUFF);
                return;
            }
            setMove((byte) 4, Intent.ATTACK, this.damage.get(3).base);
        } else {
            if (num < 33) {
                if (lastTwoMoves((byte) 0))
                    setMove((byte) 1, Intent.ATTACK, this.damage.get(1).base, this.baseAttackTimes, true);
                setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base, this.baseAttackTimes, true);
            } else if (num < 67) {
                if (lastTwoMoves((byte) 1))
                    setMove((byte) 2, Intent.ATTACK, this.damage.get(2).base, this.baseAttackTimes, true);
                setMove((byte) 1, Intent.ATTACK, this.damage.get(1).base, this.baseAttackTimes, true);
            } else {
                if (lastTwoMoves((byte) 2))
                    setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base, this.baseAttackTimes, true);
                setMove((byte) 2, Intent.ATTACK, this.damage.get(2).base, this.baseAttackTimes, true);
            }
        }
    }


    public void die() {
        if (!(AbstractDungeon.getCurrRoom()).cannotLose)
            super.die();
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            onBossVictoryLogic();
            if (!AbstractDungeon.player.hasPower(AttackOrderSpecialPower.POWER_ID)) {
                VUPShionMod.fightSpecialBossWithout = true;
            } else {
                VUPShionMod.fightSpecialBoss = true;
            }

        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);

//        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0 && currentHealth > 0) {
//            this.state.setAnimation(0, "Hit", false);
//            this.state.addAnimation(0, "Idle", true, 0.0F);
//        }

        if ((this.currentHealth < 1500 || this.currentHealth < 2000 && AbstractDungeon.ascensionLevel >= 7) && !isGunMode) {
            this.isGunMode = true;
            if (isFirstGunMode) {
                setMove((byte) 99, Intent.UNKNOWN);
                this.isFirstGunMode = false;
            } else {
                setMove((byte) 4, Intent.ATTACK, this.damage.get(3).base);
            }
            addToBot(new RemoveSpecificPowerAction(this, this, DefectPower.POWER_ID));
            addToBot(new RemoveSpecificPowerAction(this, this, StrengthenPower.POWER_ID));
            addToBot(new RemoveSpecificPowerAction(this, this, StrengthPower.POWER_ID));
            if (AbstractDungeon.ascensionLevel >= 19)
                addToBot(new ApplyPowerAction(this, this, new ShockPower(this, 2)));
            else
                addToBot(new ApplyPowerAction(this, this, new ShockPower(this, 1)));
            createIntent();
        }

        if (this.currentHealth <= 0 && !this.halfDead) {
            this.halfDead = true;

            for (AbstractPower p : this.powers) {
                p.onDeath();
            }

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onMonsterDeath(this);
            }


            this.powers.clear();

            boolean allDead = true;
            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                if (m.id.equals(this.id) && !m.halfDead) {
                    allDead = false;
                }
            }

            if (!allDead) {
                if (otherHasLink()) {
                    setMove((byte) 98, Intent.UNKNOWN);
                    createIntent();
                    addToBot(new SetMoveAction(this, (byte) 98, AbstractMonster.Intent.UNKNOWN));
                } else {
                    this.halfDead = false;
                    this.die();
                }
            } else {
                (AbstractDungeon.getCurrRoom()).cannotLose = false;
                this.halfDead = false;

                for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                    m.halfDead = false;
                    m.die();
                }
            }
        }
    }

    private boolean otherHasLink() {
        boolean otherHasLink = false;
        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
            if (m.hasPower(LifeLinkPower.POWER_ID))
                otherHasLink = true;
        }

        return otherHasLink;
    }

    public void changeState(String stateName) {
        switch (stateName) {
            case "REVIVE":
                this.halfDead = false;
                this.isGunMode = false;
                break;
            case "Attack1":
                this.state.setAnimation(0, "armor1_fire", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                break;
            case "Attack2":
                this.state.setAnimation(0, "armor2_fire", false);
                this.state.addAnimation(0, "idle", true, 0.0F);
                break;
        }
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
    }

    @Override
    public void applyStartOfTurnPowers() {
        super.applyStartOfTurnPowers();
    }
}
