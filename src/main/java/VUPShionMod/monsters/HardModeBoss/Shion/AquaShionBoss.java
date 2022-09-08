package VUPShionMod.monsters.HardModeBoss.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.CustomWaitAction;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.actions.Unique.BossFinFunnelMinionAction;
import VUPShionMod.actions.Unique.RemovePlayerBuffAction;
import VUPShionMod.actions.Unique.TurnTriggerAllBossFinFunnelAction;
import VUPShionMod.minions.AbstractPlayerMinion;
import VUPShionMod.minions.MinionGroup;
import VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels.BossDissectingFinFunnel;
import VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels.BossGravityFinFunnel;
import VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels.BossMatrixFinFunnel;
import VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels.BossPursuitFinFunnel;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.powers.Monster.BossShion.*;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class AquaShionBoss extends AbstractShionBoss {
    public static final String ID = VUPShionMod.makeID(AquaShionBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    private AbstractAtlasGameEffect dissectionEffect;

    private int moveCount = 1;

    public AquaShionBoss() {
        super(NAME, ID, 88, 0.0F, -5.0F, 520.0F, 460.0F, null, 5.0F, -7.0f);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(1200);
        } else {
            setHp(1000);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 11));
        } else {
            this.damage.add(new DamageInfo(this, 11));
        }


        this.type = EnemyType.BOSS;
        this.dialogX = -70.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;


        loadAnimation(SkinManager.getSkin(0, 2).atlasURL,
                SkinManager.getSkin(0, 2).jsonURL,
                SkinManager.getSkin(0, 2).renderScale);


        this.state.setAnimation(0, "idle", true);
        this.state.setAnimation(4, "wing_idle", true);
        this.flipHorizontal = true;

        if (bossFinFunnels.isEmpty()) {
            bossFinFunnels.add(new BossMatrixFinFunnel(22, this, 2));
            bossFinFunnels.add(new BossPursuitFinFunnel(7, this, 2));
            bossFinFunnels.add(new BossGravityFinFunnel(22, this, 2));
            bossFinFunnels.add(new BossDissectingFinFunnel(7, this, 2));
        }


    }


    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();

        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_ENDING");

        if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion
                || AbstractDungeon.player.chosenClass == AbstractPlayerEnum.Liyezhu
                || AbstractDungeon.player.chosenClass == AbstractPlayerEnum.EisluRen) {
            this.dissectionEffect = new AbstractAtlasGameEffect("Warning 13", Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f,
                    500.0f, 300.0f, 1.0f * Settings.scale, 2, false);

        } else if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan) {
            this.dissectionEffect = new AbstractAtlasGameEffect("Warning 06", Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f,
                    550.0f, 300.0f, 1.0f * Settings.scale, 2, false);

        } else {
            this.dissectionEffect = new AbstractAtlasGameEffect("Warning 20", Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f,
                    450.0f, 400.0f, 1.0f * Settings.scale, 2, false);
        }


        addToBot(new ApplyPowerAction(this, this, new BossChainPursuitPower(this, 10)));
        addToBot(new ApplyPowerAction(this, this, new BossAttackLoaderPower(this, 1)));
        addToBot(new ApplyPowerAction(this, this, new BossAvatarSpawnPower(this, 1)));
        addToBot(new ApplyPowerAction(this, this, new PotentialOutbreakPower(this, (int) (this.maxHealth * 0.3f), "Time")));

    }


    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                addToBot(new SFXAction("SHION_30"));
                addToBot(new TalkAction(this, DIALOG[0], 1.5F, 1.5F));
                break;
            case 2:
                addToBot(new SFXAction("SHION_24"));
                addToBot(new TalkAction(this, DIALOG[1], 1.5F, 1.5F));
                addToTop(new VFXAction(new AbstractAtlasGameEffect("Circle 15", AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY,
                        192.0f, 108.0f, 2.5f * Settings.scale, 2, false)));
                addToBot(new RemovePlayerBuffAction());
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 5, true)));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 5, true)));
                break;
            case 3:
                addToBot(new SFXAction("SHION_29"));
                addToBot(new TalkAction(this, DIALOG[2], 1.5F, 1.5F));
                addToBot(new VFXAction(this.dissectionEffect));
                addToBot(new CustomWaitAction(4.0f));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new SystemHackPower(AbstractDungeon.player)));

                break;
            case 4:
                addToBot(new SFXAction("SHION_19"));
                addToBot(new TalkAction(this, DIALOG[3], 2.5F, 2.5F));
                addToBot(new VFXAction(this.dissectionEffect));
                addToBot(new CustomWaitAction(5.5f));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new MagicDisorderPower(AbstractDungeon.player)));
                break;
            case 5:
                addToBot(new SFXAction("SHION_22"));
                addToBot(new TalkAction(this, DIALOG[4], 1.5F, 1.5F));
                addToBot(new VFXAction(this.dissectionEffect));
                addToBot(new CustomWaitAction(3.5f));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new ActionBlockadePower(AbstractDungeon.player)));
                break;
            case 6:
                addToBot(new GainShieldAction(this, 100));
                break;
            case 7:
                addToBot(new BossFinFunnelMinionAction(this));
                break;
            case 8:
                addToBot(new SFXAction("SHION_28"));
                addToBot(new TalkAction(this, DIALOG[5], 1.5F, 1.5F));
                addToBot(new GainShieldAction(this, 200));
                addToBot(new BossFinFunnelMinionAction(this));
                break;
            case 9:
                addToBot(new SFXAction("SHION_21"));
                addToBot(new TalkAction(this, DIALOG[6], 1.5F, 1.5F));
                addToBot(new GainShieldAction(this, 600));
                break;
            case 10:
                addToBot(new SFXAction("SHION_26"));
                addToBot(new TalkAction(this, DIALOG[7], 1.5F, 1.5F));
                addToBot(new ApplyPowerAction(this, this, new IntangiblePlayerPower(this, 2)));
                addToBot(new TurnTriggerAllBossFinFunnelAction(this));
                break;
        }

        addToBot(new RollMoveAction(this));
    }


    @Override
    protected void getMove(int num) {
        switch (this.moveCount) {
            default:
                setMove((byte) 1, Intent.UNKNOWN);
                this.moveCount++;
                break;
            case 2:
                setMove((byte) 2, Intent.STRONG_DEBUFF);
                if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion
                        || AbstractDungeon.player.chosenClass == AbstractPlayerEnum.Liyezhu
                        || AbstractDungeon.player.chosenClass == AbstractPlayerEnum.EisluRen) {
                    this.moveCount = 3;
                    return;
                }

                if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan

                ) {
                    this.moveCount = 4;
                    return;
                }

                this.moveCount = 5;
                break;
            case 3:
                setMove(MOVES[0], (byte) 3, Intent.STRONG_DEBUFF);
                this.moveCount = 6;
                break;
            case 4:
                setMove(MOVES[1], (byte) 4, Intent.STRONG_DEBUFF);
                this.moveCount = 6;
                break;
            case 5:
                setMove(MOVES[2], (byte) 5, Intent.STRONG_DEBUFF);
                this.moveCount = 6;
                break;
            case 6:
                setMove((byte) 6, Intent.DEFEND);
                this.moveCount = 7;
                break;
            case 7:
                setMove((byte) 7, Intent.ATTACK, damage.get(0).base, 8, true);
                this.moveCount = 6;
                break;
            case 8:
                setMove((byte) 8, Intent.ATTACK_DEFEND, damage.get(0).base, 8, true);
                this.moveCount = AbstractDungeon.aiRng.random(6, 7);
                break;
            case 9:
                setMove((byte) 9, Intent.DEFEND);
                this.moveCount = AbstractDungeon.aiRng.random(6, 7);
                break;
            case 10:
                setMove(MOVES[3], (byte) 10, Intent.ATTACK_DEBUFF, this.damage.get(0).base, 4, true);
                this.moveCount = AbstractDungeon.aiRng.random(6, 7);
                break;
        }


    }

    @Override
    public void changeState(String stateName) {
        switch (stateName) {
            case "Time":
                this.moveCount = 8;
                rollMove();
                createIntent();
                applyPowers();
                CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
                addToBot(new ApplyPowerAction(this, this, new PotentialOutbreakPower(this, (int) (this.maxHealth * 0.2f), "Defence")));
                break;
            case "Defence":
                this.moveCount = 9;
                rollMove();
                createIntent();
                applyPowers();
                CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
                addToBot(new ApplyPowerAction(this, this, new PotentialOutbreakPower(this, (int) (this.maxHealth * 0.2f), "OverLoad")));
                break;
            case "OverLoad":
                this.moveCount = 10;
                rollMove();
                createIntent();
                applyPowers();
                CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
                break;
        }
    }

    @SpireOverride
    protected void calculateDamage(int dmg) {
        AbstractPlayer target = AbstractDungeon.player;
        float tmp = dmg;


        for (AbstractPower p : this.powers) {
            tmp = p.atDamageGive(tmp, DamageInfo.DamageType.THORNS);
        }


        for (AbstractPower p : target.powers) {
            tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.THORNS);
        }

        if (!MinionGroup.areMinionsBasicallyDead()) {
            AbstractPlayerMinion minion = MinionGroup.getCurrentMinion();
            if(minion !=null)
            for (AbstractPower p : minion.powers) {
                tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.THORNS);
            }
        }

        tmp = AbstractDungeon.player.stance.atDamageReceive(tmp, DamageInfo.DamageType.THORNS);


        for (AbstractPower p : this.powers) {
            tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.THORNS);
        }


        for (AbstractPower p : target.powers) {
            tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.THORNS);
        }

        if (!MinionGroup.areMinionsBasicallyDead()) {
            AbstractPlayerMinion minion = MinionGroup.getCurrentMinion();
            if(minion !=null)
            for (AbstractPower p : minion.powers) {
                tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.THORNS);
            }
        }

        dmg = MathUtils.floor(tmp);
        if (dmg < 0) {
            dmg = 0;
        }

        ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentDmg", dmg);
    }

    @Override
    public void die() {
        if (!(AbstractDungeon.getCurrRoom()).cannotLose) {
            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            super.die();
            onBossVictoryLogic();
            onFinalBossVictoryLogic();
            CardCrawlGame.stopClock = true;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (this.dissectionEffect.atlas != null) {
            this.dissectionEffect.dispose();
        }
    }
}
