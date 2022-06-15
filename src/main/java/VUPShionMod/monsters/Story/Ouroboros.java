package VUPShionMod.monsters.Story;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.CustomWaitAction;
import VUPShionMod.character.WangChuan;
import VUPShionMod.cutscenes.CGlayout;
import VUPShionMod.patches.SpecialCombatPatches;
import VUPShionMod.powers.Monster.TimePortal.*;
import VUPShionMod.powers.Shion.ReinsOfWarPower;
import VUPShionMod.relics.Event.FragmentsOfFaith;
import VUPShionMod.vfx.Monster.OuroborosLaserBeamEffect;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Bone;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;

public class Ouroboros extends CustomMonster {
    public static final String ID = VUPShionMod.makeID("Ouroboros");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    private boolean isDefect = false;
    private boolean cgDone = false;
    private int moveCount = 0;

    private CGlayout cg;

    protected Bone fireBone;
    private float waitingDeath = 6.0f;
    private boolean npc = false;
    private boolean charging = false;

    public Ouroboros() {
        this(false);
    }

    public Ouroboros(boolean isDefect) {
        super(NAME, ID, 88, -60.0F, 210.0F, 720.0F, 500.0F, null, 60.0F, -180.0f);

        if (AbstractDungeon.player.hasRelic(FragmentsOfFaith.ID))
            this.cg = new CGlayout("StoryBE");
        else
            this.cg = new CGlayout("StoryTE");

        this.cg.switchTimerMax = 5.0f;

        this.isDefect = isDefect;

        if (this.isDefect) {
            this.name = MOVES[0];
            if (AbstractDungeon.ascensionLevel >= 7) {
                setHp(6000);
            } else {
                setHp(5000);
            }
        } else {
            setHp(10000);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 1300));
        } else {
            this.damage.add(new DamageInfo(this, 1300));
        }


        this.type = EnemyType.BOSS;
        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;


        loadAnimation("VUPShionMod/img/monsters/Ouroboros/Stance_timebug_only.atlas",
                "VUPShionMod/img/monsters/Ouroboros/Stance_timebug_only.json", 1.0f);


        this.state.setAnimation(0, "timebug_out", false);
        this.state.addAnimation(0, "timebug_idle", true, 0.0f);

        if (!this.isDefect) {
            this.state.setAnimation(1, "lightwing_open", false);
            this.state.addAnimation(1, "lightwing_idle", true, 0.0f);
        }

        this.fireBone = this.skeleton.findBone("weapon1_bone");

        this.flipHorizontal = false;
    }


    public void usePreBattleAction() {

        addToBot(new ApplyPowerAction(this, this, new ContortTimePower(this, 20)));
        addToBot(new ApplyPowerAction(this, this, new StainTheMindPower(this, 2)));
        addToBot(new ApplyPowerAction(this, this, new HulkingPower(this, 2000)));
        addToBot(new ApplyPowerAction(this, this, new DemonOfTimePower(this, 1000)));
        addToBot(new ApplyPowerAction(this, this, new LabyrinthOfTimelinePower(this, 7)));
        addToBot(new ApplyPowerAction(this, this, new SubspacePursuerPower(this, 20)));


        addToBot(new ApplyPowerAction(this, this, new ArtifactPower(this, 3)));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractPower> powersToRemove = new ArrayList<>();
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (!p.ID.equals(ReinsOfWarPower.POWER_ID))
                        powersToRemove.add(p);
                }

                if (!powersToRemove.isEmpty())
                    addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, Ouroboros.this,
                            powersToRemove.get(AbstractDungeon.miscRng.random(powersToRemove.size() - 1))));
                isDone = true;
            }
        });

        addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new WeakPower(AbstractDungeon.player,99,true)));

        if(!this.isDefect)
        addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new VulnerablePower(AbstractDungeon.player,99,true)));

    }


    public void takeTurn() {
        if (this.npc)
            return;

        AbstractPlayer trueTarget = AbstractDungeon.player;
        if (AbstractDungeon.player instanceof WangChuan) {
            if (((WangChuan) AbstractDungeon.player).shionHelper != null)
                if (!((WangChuan) AbstractDungeon.player).shionHelper.halfDead)
                    trueTarget = ((WangChuan) AbstractDungeon.player).shionHelper;
        }

        switch (this.nextMove) {
            case 0:
                addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 3, true, true));
                addToBot(new ApplyPowerAction(this, this, new AnnihilatingCanonPower(this, 100)));
                addToBot(new ChangeStateAction(this, "Fire_Ready"));
                break;
            case 1:
                addToBot(new ChangeStateAction(this, "Fire_Attack"));
                addToBot(new CustomWaitAction(0.7f));
                addToBot(new VFXAction(new OuroborosLaserBeamEffect(this.hb.cX - 140.0f * Settings.scale, this.hb.cY + 220.0F * Settings.scale), 1.5F));
                addToBot(new DamageAction(trueTarget, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
                addToBot(new ChangeStateAction(this, "Fire_Attack2"));
                addToBot(new RemoveSpecificPowerAction(this, this, AnnihilatingCanonPower.POWER_ID));

                break;
            case 2:
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 100)));
                addToBot(new ApplyPowerAction(this, this, new ArtifactPower(this, 10)));
                break;
            case 3:
                break;
        }


        addToBot(new RollMoveAction(this));

    }


    protected void getMove(int num) {
        if (this.npc) {
            setMove((byte) 3, Intent.NONE);
            return;
        }

        switch (this.moveCount % 3) {
            case 0:
                setMove((byte) 0, Intent.DEBUFF);
                break;
            case 1:
                setMove(MOVES[1], (byte) 1, Intent.ATTACK, this.damage.get(0).base);
                break;
            case 2:
                if (this.isDefect)
                    setMove((byte) 3, Intent.UNKNOWN);
                else
                    setMove((byte) 2, Intent.BUFF);
                break;
        }

        this.moveCount++;
    }

    @Override
    public void die() {
        if (!(AbstractDungeon.getCurrRoom()).cannotLose) {
            super.die();

            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            onBossVictoryLogic();
            SpecialCombatPatches.victoryFightSpecialBoss();

        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);

        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0 && currentHealth > 0) {
            this.state.setAnimation(0, "timebug_hurt", false);
            this.state.addAnimation(0, "timebug_idle", true, 0.0F);
        }

        if (this.currentHealth <= 0 && !this.halfDead) {
            this.halfDead = true;

            addToTop(new ClearCardQueueAction());
            this.powers.clear();

            this.state.setAnimation(0, "timebug_dead", false);
            this.state.addAnimation(0, "timebug_dead_disapper", false, 0.0f);
            if (this.charging)
                this.state.setAnimation(3, "attack_fire_cold_down", false);

            hideHealthBar();
            this.npc = true;

            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                if (m.id.equals(TimePortal.ID)) {
                    m.changeState("DieAnimation");
                }
            }

            GameCursor.hidden = true;
            AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NO_INTERACT;
            AbstractDungeon.isScreenUp = true;
        }


    }

    //    0本体 ，1翅膀。2翅膀阴影，3能量球
    public void changeState(String stateName) {
        switch (stateName) {
            case "Fire_Ready":
                if (this.isDefect) {
                    this.state.setAnimation(1, "lightwing_open", false);
                    this.state.addAnimation(1, "lightwing_idle", true, 0.0f);
                }

                this.charging = true;
                this.state.setAnimation(3, "attack_ready", false);
                this.state.addAnimation(3, "attack_fire_wait", true, 0.0f);
                break;
            case "Fire_Attack":

                this.state.setAnimation(0, "attack_fire", false);
                this.state.addAnimation(0, "timebug_idle", true, 0.0f);

                this.charging = false;
                break;
            case "Fire_Attack2":
                this.state.setAnimation(3, "attack_fire_cold_down", false);


                if (this.isDefect)
                    this.state.setAnimation(1, "lightwing_drop", false);
                break;

        }
    }

    @Override
    public void renderTip(SpriteBatch sb) {
        this.tips.clear();

        if (this.intentAlphaTarget == 1.0F && !AbstractDungeon.player.hasRelic("Runic Dome") && this.intent != Intent.NONE) {
            this.tips.add(ReflectionHacks.getPrivate(this, AbstractMonster.class, "intentTip"));
        }


        ArrayList<AbstractPower> tipPowers = new ArrayList<>();

        for (AbstractPower p : this.powers) {
            if (p instanceof ContortTimePower ||
                    p instanceof StainTheMindPower ||
                    p instanceof HulkingPower ||
                    p instanceof DemonOfTimePower ||
                    p instanceof LabyrinthOfTimelinePower ||
                    p instanceof AnnihilatingCanonPower ||
                    p instanceof SubspacePursuerPower) {
                if (p.region48 != null) {
                    this.tips.add(new PowerTip(p.name, p.description, p.region48));
                    continue;
                }
                this.tips.add(new PowerTip(p.name, p.description, p.img));
            } else {
                tipPowers.add(p);
            }

        }


        for (AbstractPower p : tipPowers) {
            if (p.region48 != null) {
                this.tips.add(new PowerTip(p.name, p.description, p.region48));
                continue;
            }
            this.tips.add(new PowerTip(p.name, p.description, p.img));
        }


        if (!this.tips.isEmpty()) {
            if (this.hb.cX + this.hb.width / 2.0F < TIP_X_THRESHOLD) {
                TipHelper.queuePowerTips(this.hb.cX + this.hb.width / 2.0F + TIP_OFFSET_R_X, this.hb.cY +

                        TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
            } else {

                TipHelper.queuePowerTips(this.hb.cX - this.hb.width / 2.0F + TIP_OFFSET_L_X, this.hb.cY +

                        TipHelper.calculateAdditionalOffset(this.tips, this.hb.cY), this.tips);
            }
        }


    }

    @Override
    public void update() {
        super.update();

        if (this.halfDead) {
            this.waitingDeath -= Gdx.graphics.getDeltaTime();

            if (this.waitingDeath > 0.0f)
                return;

            this.cg.update();
        }

        if (cg.isDone && !cgDone) {
            this.cgDone = true;
            this.halfDead = false;

            GameCursor.hidden = false;
            AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
            AbstractDungeon.isScreenUp = false;

            (AbstractDungeon.getCurrRoom()).cannotLose = false;
            die();

            for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
                if (m.id.equals(TimePortal.ID)) {
                    m.die();
                }
            }
        }

    }


    @SpireOverride
    protected void renderIntentVfxBehind(SpriteBatch sb) {
        if (!this.npc)
            SpireSuper.call(sb);
    }

    @SpireOverride
    protected void renderIntent(SpriteBatch sb) {
        if (!this.npc)
            SpireSuper.call(sb);
    }

    @SpireOverride
    protected void renderIntentVfxAfter(SpriteBatch sb) {
        if (!this.npc)
            SpireSuper.call(sb);
    }


    public void renderAbove(SpriteBatch sb) {
        if (this.halfDead && this.waitingDeath <= 0.0f) {
            this.cg.renderAbove(sb);
        }
    }
}
