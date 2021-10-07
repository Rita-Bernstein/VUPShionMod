package VUPShionMod.monsters;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.GachaAction;
import VUPShionMod.actions.SummonMundoMinionAction;
import VUPShionMod.effects.FinFunnelSelectedEffect;
import VUPShionMod.effects.ShionBossBackgroundEffect;
import VUPShionMod.powers.DefectPower;
import VUPShionMod.powers.ShockPower;
import VUPShionMod.powers.StrengthenPower;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
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

public class PlagaAMundo extends CustomMonster {
    public static final String ID = VUPShionMod.makeID("PlagaAMundo");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    private boolean isFirstMove = true;
    private int baseAttackTimes = 13;
    private boolean isGunMode = false;
    private boolean isFirstGunMode = true;

    public PlagaAMundo() {
        super(NAME, ID, 88, -15.0F, 160.0F, 420.0F, 320.0F, null, 0.0F, -20.0F);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(2500);
        } else {
            setHp(2000);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 3));
            this.damage.add(new DamageInfo(this, 150));
        } else {
            this.damage.add(new DamageInfo(this, 3));
            this.damage.add(new DamageInfo(this, 150));
        }

        if (AbstractDungeon.ascensionLevel >= 4)
            baseAttackTimes += 2;


        this.type = EnemyType.BOSS;
        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;


        loadAnimation("VUPShionMod/img/monsters/PlagaAMundo/Idle_BOSS.atlas", "VUPShionMod/img/monsters/PlagaAMundo/Idle_BOSS.json", 2.0f);


        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.flipHorizontal = false;
    }


    public void usePreBattleAction() {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("VUPShionMod:Boss_Phase1");
        (AbstractDungeon.getCurrRoom()).cannotLose = true;
        if (AbstractDungeon.ascensionLevel >= 19)
            addToBot(new ApplyPowerAction(this, this, new DefectPower(this, 2)));
        else
            addToBot(new ApplyPowerAction(this, this, new DefectPower(this, 1)));

        if (AbstractDungeon.ascensionLevel >= 5)
            addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, (int) Math.floor(AbstractDungeon.player.maxHealth * 0.75f)));
        else
            addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.maxHealth));


        AbstractDungeon.effectList.add(new ShionBossBackgroundEffect());
    }


    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                for (int i = 0; i < baseAttackTimes; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE, true));
                break;
            case 3:
                addToBot(new GainBlockAction(this, this, 50));
                break;
            case 4:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE));
                break;
            case 5:
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 50), 50));
                break;
            case 98:
                addToBot(new SummonMundoMinionAction(this, new PlagaAMundoMinion(-400, -20, 1.0f), 1));
                addToBot(new SummonMundoMinionAction(this, new PlagaAMundoMinion(-60, 30, 1.3f), 2));
                addToBot(new SummonMundoMinionAction(this, new PlagaAMundoMinion(220, -80, 0.8f), 3));
                addToBot(new ChangeStateAction(this, "Die"));
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        addToBot(new GachaAction());
                        isDone = true;
                    }
                });
                break;
            case 99:
                addToBot(new ApplyPowerAction(this, this, new StrengthenPower(this, 3)));
                break;
        }

        addToBot(new RollMoveAction(this));

    }


    protected void getMove(int num) {
        if (this.isFirstMove) {
            setMove((byte) 99, Intent.BUFF);
            this.isFirstMove = false;
            return;
        }

        if (isGunMode) {
            if (lastMove((byte) 4)) {
                setMove((byte) 5, Intent.BUFF);
                return;
            }
            setMove((byte) 4, Intent.ATTACK, this.damage.get(1).base);
        } else {
            setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base, baseAttackTimes, true);
        }
    }

    @Override
    public void die() {
        if (!(AbstractDungeon.getCurrRoom()).cannotLose)
            super.die();
//        if(AbstractDungeon.getMonsters().areMonstersBasicallyDead()){
//            AbstractDungeon.scene.fadeInAmbiance();
//            CardCrawlGame.music.fadeOutTempBGM();
//        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);

//        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0 && currentHealth > 0) {
//            this.state.setAnimation(0, "Hit", false);
//            this.state.addAnimation(0, "Idle", true, 0.0F);
//        }

        if ((this.currentHealth < 1000 || this.currentHealth < 1500 && AbstractDungeon.ascensionLevel >= 7) && !isGunMode) {
            this.isGunMode = true;
            if (isFirstGunMode) {
                setMove((byte) 99, Intent.BUFF);
                this.isFirstGunMode = false;
            } else {
                setMove((byte) 4, Intent.ATTACK, this.damage.get(1).base);
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

            addToTop(new ClearCardQueueAction());

            this.powers.clear();

            setMove((byte) 98, Intent.UNKNOWN);
            createIntent();
            addToBot(new SetMoveAction(this, (byte) 98, AbstractMonster.Intent.UNKNOWN));
            applyPowers();

        }
    }

    public void changeState(String stateName) {
        switch (stateName) {
            case "Die":
                this.halfDead = false;
                this.die(true);
                break;
        }
    }

}
