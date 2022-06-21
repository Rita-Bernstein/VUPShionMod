package VUPShionMod.monsters;


import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.CustomWaitAction;
import VUPShionMod.powers.Monster.RitaShop.DefenceMonsterPower;
import VUPShionMod.powers.Monster.RitaShop.ProbePower;
import VUPShionMod.powers.Monster.RitaShop.ReflectionPower;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.AnimateJumpAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.*;

public class RitaShop extends CustomMonster {
    public static final String ID = VUPShionMod.makeID("RitaShop");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    private int moveCount = 0;
    private int formAmount = 0;
    private boolean isFormChanged = false;
    private boolean isFirstMove = true;

    private int repuukenHitCount = 4;
    private int ExecutionHitCount = 5;
    private int DestructionHitCount = 4;
    private int CutterHitCount = 2;


    private int timeLimit = 4;


    public RitaShop() {
        super(NAME, ID, 160, 0.0F, -10.0F, 300.0F, 380.0F, null, 0.0F, 0.0F);
        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(180);
        } else {
            setHp(160);
        }


        if (AbstractDungeon.ascensionLevel >= 19) {
            this.timeLimit = 3;
        } else {
            this.timeLimit = 4;
        }

//进阶4加伤害
        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 28));
            this.damage.add(new DamageInfo(this, 10));
            this.damage.add(new DamageInfo(this, 30));
            this.damage.add(new DamageInfo(this, 5));
            this.damage.add(new DamageInfo(this, 25));
            this.damage.add(new DamageInfo(this, 67)); //5
            this.damage.add(new DamageInfo(this, 3));
            this.damage.add(new DamageInfo(this, 15));
            this.damage.add(new DamageInfo(this, 15));
            this.damage.add(new DamageInfo(this, 3));//9
        } else {
            this.damage.add(new DamageInfo(this, 20));
            this.damage.add(new DamageInfo(this, 7));
            this.damage.add(new DamageInfo(this, 24));
            this.damage.add(new DamageInfo(this, 5));
            this.damage.add(new DamageInfo(this, 25));
            this.damage.add(new DamageInfo(this, 67));//5
            this.damage.add(new DamageInfo(this, 3));
            this.damage.add(new DamageInfo(this, 12));
            this.damage.add(new DamageInfo(this, 12));
            this.damage.add(new DamageInfo(this, 3));//9
        }


        this.type = AbstractMonster.EnemyType.BOSS;
        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;


        loadAnimation("VUPShionMod/img/monsters/Rita/Rita.atlas", "VUPShionMod/img/monsters/Rita/Rita.json", 0.9F);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(0.8F);

        this.flipHorizontal = true;
    }


    //----------------------------------------------------------------------------------------------------

    public void usePreBattleAction() {
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();

        CardCrawlGame.music.playTempBGM(VUPShionMod.makeID("RitaFight1"));


        (AbstractDungeon.getCurrRoom()).cannotLose = true;
        addToBot(new ApplyPowerAction(this, this, new ProbePower(this, this.timeLimit + 1)));

        addToBot(new ApplyPowerAction(this, this, new DefenceMonsterPower(this, 135 - AbstractDungeon.ascensionLevel * 6)));

        addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.maxHealth));

        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, true, false, Settings.WIDTH * 0.2F, Settings.HEIGHT / 2.0F));
        addToBot(new MakeTempCardInDrawPileAction(new Slimed(), 1, true, true, false, Settings.WIDTH * 0.2F, Settings.HEIGHT / 2.0F));

        if (AbstractDungeon.ascensionLevel >= 19) {
            addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, true, true, false, Settings.WIDTH * 0.2F, Settings.HEIGHT / 2.0F));
        }

    }


    public void takeTurn() {
        int temp;
        AbstractPlayer p = AbstractDungeon.player;
//第一阶段------------------------------------------------------
        switch (this.nextMove) {
            case 2://震荡波
                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_Shockwave")));

                addToTop(new ChangeStateAction(this, "Intimidate"));

                addToBot(new VFXAction(this, new ShockWaveEffect(this.hb.cX, this.hb.cY, Settings.RED_TEXT_COLOR, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.2F));

                addToBot(new ApplyPowerAction(p, this, new VulnerablePower(p, AbstractDungeon.ascensionLevel >= 19 ? 5 : 3, true)));
                addToBot(new ApplyPowerAction(p, this, new WeakPower(p, AbstractDungeon.ascensionLevel >= 19 ? 5 : 3, true)));
                break;

            case 1://残杀
                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_Carnage")));
                addToBot(new VFXAction(new ViolentAttackEffect(p.hb.cX, p.hb.cY, Color.RED)));
                for (int i = 0; i < 5; i++) {
                    addToBot(new VFXAction(new StarBounceEffect(p.hb.cX, p.hb.cY)));
                }
                addToBot(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
                break;


            case 0://恶魔之焰
                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_FiendFire") + MathUtils.random(1)));
                for (temp = 0; temp < this.repuukenHitCount; temp++) {
                    addToBot(new DamageAction(p, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE, true));
                }

                break;


//第二阶段------------------------------------------------------
            case 10://黑暗屏障
                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_DarkBarrier") + MathUtils.random(1)));
                addToBot(new VFXAction(this, new FlameBarrierEffect(this.hb.cX, this.hb.cY), 0.5F));
                addToBot(new GainBlockAction(this, this, 50));
                addToBot(new ApplyPowerAction(this, this, new ArtifactPower(this, 2)));
                addToBot(new ApplyPowerAction(this, this, new ReflectionPower(this, AbstractDungeon.ascensionLevel >= 19 ? 3 : 2)));
                addToBot(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 5)));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 2)));
                break;

            case 11://陨石打击

                addToBot(new VFXAction(new WeightyImpactEffect(p.hb.cX, p.hb.cY)));
                addToBot(new WaitAction(0.8F));
                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_MeteorStrike")));
                addToBot(new DamageAction(p, this.damage.get(2), AbstractGameAction.AttackEffect.NONE));
                break;

            case 12://诸神之黄昏
                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_Ragnarok")));
                addToBot(new AnimateFastAttackAction(this));
                for (temp = 0; temp < this.ExecutionHitCount; temp++) {
                    addToBot(new SFXAction("THUNDERCLAP", 0.05F));
                    addToBot(new VFXAction(new LightningEffect(p.drawX, p.drawY), 0.05F));
                    addToBot(new DamageAction(p, this.damage.get(3), AbstractGameAction.AttackEffect.NONE, true));
                }

                break;
            //第三阶段------------------------------------------------------
            case 20://灭族切割
                addToBot(new VFXAction(new GoldenSlashEffect(p.hb.cX - 60.0F * Settings.scale, AbstractDungeon.player.hb.cY,
                        true), 0.0f));
                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_GenocideCutter") + MathUtils.random(1)));
                addToBot(new AnimateJumpAction(this));
                addToBot(new DamageAction(p, this.damage.get(4), AbstractGameAction.AttackEffect.NONE));
                addToBot(new VFXAction(new GoldenSlashEffect(p.hb.cX + 60.0F * Settings.scale, AbstractDungeon.player.hb.cY,
                        true), 0.0f));

                addToBot((new CustomWaitAction(0.1f)));
                addToBot(new DamageAction(p, this.damage.get(4), AbstractGameAction.AttackEffect.NONE));


                break;

            case 21://烈风燕破
                addToBot(new AnimateFastAttackAction(this));
                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_HellPress")));
                addToBot(new DamageAction(p, this.damage.get(5), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;

            case 22://灭除之刃
                addToBot(new SFXAction(VUPShionMod.makeID("RitaB_Expunger")));
                addToBot(new AnimateFastAttackAction(this));
                for (temp = 0; temp < this.DestructionHitCount; temp++) {
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            addToTop(new VFXAction(new AnimatedSlashEffect(
                                    AbstractDungeon.player.hb.cX,
                                    AbstractDungeon.player.hb.cY - 30.0F * Settings.scale,
                                    -500.0F, -500.0F, 135.0F, 4.0F,
                                    Color.VIOLET, Color.MAGENTA)));
                            addToTop(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.7F, true));
                            addToTop(new SFXAction("ATTACK_IRON_3", 0.2F));
                            isDone = true;
                        }
                    });
                    addToBot(new DamageAction(p, this.damage.get(6), AbstractGameAction.AttackEffect.NONE, true));
                }

                if (this.timeLimit <= 0) {
                    addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 50)));
                    this.timeLimit = 3;
                } else {
                    if (AbstractDungeon.ascensionLevel >= 19) {
                        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 10)));
                    } else {
                        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 8)));
                    }
                }

                break;


//------------------------------------------------------复活
            case 99:
                this.halfDead = false;

                switch (this.formAmount) {
                    case 1:

//复活：第二形态
                        if (AbstractDungeon.ascensionLevel >= 9) {
                            this.maxHealth = 340 + currentHealth;
                        } else {
                            this.maxHealth = 270 + currentHealth;
                        }

                        addToBot(new SFXAction(VUPShionMod.makeID("RitaB_CombatStart") + MathUtils.random(3)));

                        addToBot(new RemoveSpecificPowerAction(this, this, ProbePower.POWER_ID));
                        addToBot(new ApplyPowerAction(this, this, new UnawakenedPower(this)));
                        addToBot(new ApplyPowerAction(this, this, new BarricadePower(this)));

                        break;
//复活：第三形态
                    case 2:
                        this.timeLimit = 8;
                        this.maxHealth = 280;

                        CardCrawlGame.music.silenceTempBgmInstantly();
                        CardCrawlGame.music.playTempBGM(VUPShionMod.makeID("RitaFight2"));

                        (AbstractDungeon.getCurrRoom()).cannotLose = false;

                        addToBot(new SFXAction(VUPShionMod.makeID("RitaB_TrueMod")));
                        addToBot(new RemoveSpecificPowerAction(this, this, UnawakenedPower.POWER_ID));
                        break;


                }

                addToBot(new HealAction(this, this, this.maxHealth));
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isFirstMove = true;
                        isDone = true;
                    }
                });


                if (formAmount >= 3) {
                    (AbstractDungeon.getCurrRoom()).cannotLose = false;
                }
                break;


        }

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                timeLimit--;
                isFormChanged = false;

                isDone = true;
            }
        });
        addToBot(new RollMoveAction(this));
    }


    //死亡相关
    public void die() {
        if (!(AbstractDungeon.getCurrRoom()).cannotLose) {
            CardCrawlGame.music.silenceTempBgmInstantly();
            CardCrawlGame.music.unsilenceBGM();
            addToBot(new ShoutAction(this, DIALOG[0]));
            super.die();

            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);

            this.deathTimer += 1.5f;
            CardCrawlGame.sound.play(VUPShionMod.makeID("RitaB_Die"));
        }
    }


    public void changeState(String stateName) {
        switch (stateName) {
            case "Intimidate":
                this.state.setAnimation(0, "Intimidate", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
            case "Form2":
                this.state.setAnimation(0, "Idle", false);
                this.state.addAnimation(0, "Idle_3", true, 0.0F);
                break;
        }
    }
//行动规律-----------------

    protected void getMove(int num) {
        //第一阶段回合限制
        if (this.timeLimit == 0 && formAmount == 0 && !isFormChanged) {
            this.formAmount += 1;
            this.isFormChanged = true;
            addToTop(new ClearCardQueueAction());
            addToBot(new SetMoveAction(this, (byte) 99, AbstractMonster.Intent.UNKNOWN));
            return;
        }


        switch (formAmount) {
//第一阶段
            case 0:
                if (this.isFirstMove) {
                    setMove(MOVES[2], (byte) 2, AbstractMonster.Intent.STRONG_DEBUFF);
                    this.isFirstMove = false;
                    return;
                }

                switch (this.moveCount % 2) {
                    case 0:
                        if (AbstractDungeon.aiRng.randomBoolean()) {
                            setMove(MOVES[0], (byte) 0, AbstractMonster.Intent.ATTACK, this.damage.get(1).base, this.repuukenHitCount, true);
                            break;
                        }
                        setMove(MOVES[1], (byte) 1, AbstractMonster.Intent.ATTACK, this.damage.get(0).base);

                        break;


                    case 1:
                        if (!lastMove((byte) 1)) {
                            setMove(MOVES[1], (byte) 1, AbstractMonster.Intent.ATTACK, this.damage.get(0).base);
                            break;
                        }
                        setMove(MOVES[0], (byte) 0, AbstractMonster.Intent.ATTACK, this.damage.get(1).base, this.repuukenHitCount, true);

                        break;
                }

                this.moveCount++;
                break;


//第二阶段
            case 1:
                if (this.isFirstMove) {
                    setMove(MOVES[6], (byte) 12, AbstractMonster.Intent.ATTACK, this.damage.get(3).base, this.ExecutionHitCount, true);
                    this.isFirstMove = false;
                    return;
                }


                switch (this.moveCount % 3) {
                    case 0:
                        setMove(MOVES[4], (byte) 10, Intent.DEFEND_BUFF);
                        break;

                    case 1:
                        setMove(MOVES[5], (byte) 11, Intent.ATTACK, this.damage.get(2).base);
                        break;


                    default:
                        if (lastMove((byte) 12)) {
                            setMove(MOVES[4], (byte) 10, Intent.DEFEND_BUFF);
                            this.moveCount = 0;
                            break;
                        }
                        setMove(MOVES[6], (byte) 12, AbstractMonster.Intent.ATTACK, this.damage.get(3).base, this.ExecutionHitCount, true);
                        break;
                }

                this.moveCount++;
                break;


//第三阶段
            case 2:
//第一回合行动
                if (this.isFirstMove) {
                    setMove(MOVES[8], (byte) 20, AbstractMonster.Intent.ATTACK, this.damage.get(4).base, 2, true);
                    this.isFirstMove = false;
                    return;
                }


                switch (this.moveCount % 3) {
                    case 0:
                        setMove(MOVES[9], (byte) 21, AbstractMonster.Intent.ATTACK, this.damage.get(5).base);
                        break;

                    case 1:
                        setMove(MOVES[10], (byte) 22, Intent.ATTACK_BUFF, this.damage.get(6).base, DestructionHitCount, true);
                        break;


                    default:
                        if (lastMove((byte) 20)) {
                            setMove(MOVES[9], (byte) 21, AbstractMonster.Intent.ATTACK, this.damage.get(5).base);
                            this.moveCount = 0;
                            break;
                        }
                        setMove(MOVES[8], (byte) 20, AbstractMonster.Intent.ATTACK, this.damage.get(4).base, CutterHitCount, true);
                        break;
                }

                this.moveCount++;
                break;
        }

    }


    public void damage(DamageInfo info) {
        if (info.output > 0 && hasPower("Intangible")) {
            info.output = 1;
        }


//结算伤害
        super.damage(info);

        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);

//            if (this.formAmount >= 1 && nextMove != 99) {
//                this.state.addAnimation(0, "Idle_3", true, 0.0F);
//            } else {
//                this.state.addAnimation(0, "Idle", true, 0.0F);
//            }


            if (MathUtils.random(3) == 0) {
                CardCrawlGame.sound.play(VUPShionMod.makeID("RitaB_Hit" + MathUtils.random(5)));
            }
        }

//击杀濒死
        if (this.currentHealth <= 0 && !this.halfDead) {
            if ((AbstractDungeon.getCurrRoom()).cannotLose) {
                this.halfDead = true;
            }
            for (AbstractPower p : this.powers) {
                p.onDeath();
            }
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onMonsterDeath(this);
            }

            addToTop(new ClearCardQueueAction());

            addToBot(new RemoveDebuffsAction(this));
            addToBot(new RemoveSpecificPowerAction(this, this, ReflectionPower.POWER_ID));
            addToBot(new RemoveSpecificPowerAction(this, this, "IntangiblePlayer"));


//切阶段相关
//对话演出

            if (!this.isFormChanged) {
                setMove((byte) 99, AbstractMonster.Intent.UNKNOWN);
                createIntent();
                addToTop(new ClearCardQueueAction());


//切到复活意图
                addToBot(new SetMoveAction(this, (byte) 99, AbstractMonster.Intent.UNKNOWN));
                applyPowers();

                this.formAmount++;
                this.isFormChanged = true;
                this.moveCount = 0;
            }
        }
    }
}

