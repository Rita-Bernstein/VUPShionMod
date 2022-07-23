package VUPShionMod.monsters.Story;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.CustomWaitAction;
import VUPShionMod.actions.Unique.GachaAction;
import VUPShionMod.actions.Unique.SummonMinionAction;
import VUPShionMod.character.WangChuan;
import VUPShionMod.effects.ShionBossBackgroundEffect;
import VUPShionMod.powers.Monster.PlagaAMundo.*;
import VUPShionMod.powers.Monster.TimePortal.DespairPower;
import VUPShionMod.util.SaveHelper;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PlagaAMundo extends CustomMonster {
    public static final String ID = VUPShionMod.makeID(PlagaAMundo.class.getSimpleName());
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

        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(2500);
        } else {
            setHp(2000);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 3));
            this.damage.add(new DamageInfo(this, 233));
        } else {
            this.damage.add(new DamageInfo(this, 3));
            this.damage.add(new DamageInfo(this, 233));
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

        addToBot(new ApplyPowerAction(this, this, new PlagaAMundoCounterPower(this, 13)));

        if (AbstractDungeon.ascensionLevel >= 19)
            addToBot(new ApplyPowerAction(this, this, new DefectPower(this, 2)));
        else
            addToBot(new ApplyPowerAction(this, this, new DefectPower(this, 1)));

        if (AbstractDungeon.ascensionLevel >= 5)
            addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, (int) Math.floor(AbstractDungeon.player.maxHealth * 0.75f)));
        else
            addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.maxHealth));


        if (SaveHelper.isHardMod) {
            addToBot(new ApplyPowerAction(this, this, new FlyPower(this, 50)));
            addToBot(new ApplyPowerAction(this, this, new ArtifactPower(this, 10)));
        }


        AbstractDungeon.effectList.add(new ShionBossBackgroundEffect());

        addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new ChaoticPower(AbstractDungeon.player, 2)));
    }


    public void takeTurn() {
        AbstractPlayer trueTarget = AbstractDungeon.player;
        if (AbstractDungeon.player instanceof WangChuan) {
            if (((WangChuan) AbstractDungeon.player).shionHelper != null)
                if (!((WangChuan) AbstractDungeon.player).shionHelper.halfDead)
                    trueTarget = ((WangChuan) AbstractDungeon.player).shionHelper;
        }
        switch (this.nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, "Attack2"));

                for (int i = 0; i < baseAttackTimes; i++)
                    addToBot(new DamageAction(trueTarget, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE, true));
                break;
            case 3:
                addToBot(new GainBlockAction(this, this, 50));
                break;
            case 4:
                addToBot(new ChangeStateAction(this, "Attack1"));
                addToBot(new CustomWaitAction(1.0f));
                addToBot(new DamageAction(trueTarget, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE));
                break;
            case 5:
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 50), 50));
                break;
            case 98:
                if(SaveHelper.isHardMod) {
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new SiegePower(AbstractDungeon.player, 1)));
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new DespairPower(AbstractDungeon.player, 13)));
                }

                addToBot(new SummonMinionAction(this, new PlagaAMundoMinion(-400, -20, 1.0f), 1));
                addToBot(new SummonMinionAction(this, new PlagaAMundoMinion(-60, 30, 1.3f), 2));
                addToBot(new SummonMinionAction(this, new PlagaAMundoMinion(220, -80, 0.8f), 3));
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
            case 97:
                break;
        }

        addToBot(new RollMoveAction(this));

    }


    protected void getMove(int num) {
        if (GameActionManager.turn >= 12) {
            setMove((byte) 98, Intent.UNKNOWN);
            return;
        }

        if (this.isFirstMove) {
            setMove((byte) 99, Intent.BUFF);
            this.isFirstMove = false;
            return;
        }

        if (isGunMode) {
//            if (lastMove((byte) 4)) {
//                setMove((byte) 5, Intent.BUFF);
//                return;
//            }
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

        if ((this.currentHealth < 1000 || this.currentHealth < 1500 && AbstractDungeon.ascensionLevel >= 9) && !isGunMode) {
            this.isGunMode = true;
            if (isFirstGunMode) {
                setMove((byte) 97, Intent.UNKNOWN);
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
                this.currentHealth = 0;
                updateHealthBar();
                healthBarUpdatedEvent();
                this.die(true);
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

}
