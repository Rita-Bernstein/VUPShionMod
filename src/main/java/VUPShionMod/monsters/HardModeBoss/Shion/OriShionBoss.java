package VUPShionMod.monsters.HardModeBoss.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.CustomWaitAction;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.actions.Unique.BossFinFunnelMinionAction;
import VUPShionMod.actions.Unique.BossTriggerDimensionSplitterAction;
import VUPShionMod.actions.Unique.RemovePlayerBuffAction;
import VUPShionMod.actions.Unique.TurnTriggerAllBossFinFunnelAction;
import VUPShionMod.minions.AbstractPlayerMinion;
import VUPShionMod.minions.MinionGroup;
import VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels.*;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.powers.Monster.BossShion.*;
import VUPShionMod.powers.Shion.PursuitPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
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
import com.megacrit.cardcrawl.vfx.combat.LaserBeamEffect;

public class OriShionBoss extends AbstractShionBoss {
    public static final String ID = VUPShionMod.makeID(OriShionBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;


    private int moveCount = 1;
    private boolean stateChanged = false;
    private boolean strike = false;

    public OriShionBoss() {
        super(NAME, ID, 88, 0.0F, -5.0F, 520.0F, 460.0F, null, 5.0F, -7.0f);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(400);
        } else {
            setHp(350);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 30));
            this.damage.add(new DamageInfo(this, 1));
            this.damage.add(new DamageInfo(this, 1));
            this.damage.add(new DamageInfo(this, 15));
        } else {
            this.damage.add(new DamageInfo(this, 30));
            this.damage.add(new DamageInfo(this, 1));
            this.damage.add(new DamageInfo(this, 1));
            this.damage.add(new DamageInfo(this, 15));
        }


        this.type = EnemyType.BOSS;
        this.dialogX = -70.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;


        loadAnimation(SkinManager.getSkin(0, 0).atlasURL,
                SkinManager.getSkin(0, 0).jsonURL,
                SkinManager.getSkin(0, 0).renderScale);


        this.state.setAnimation(0, "idle", true);
        this.state.setAnimation(4, "wing_idle", true);
        this.flipHorizontal = true;

        if (bossFinFunnels.isEmpty()) {
            bossFinFunnels.add(new BossMatrixFinFunnel(2, this, 0));
            bossFinFunnels.add(new BossPursuitFinFunnel(2, this, 0));
            bossFinFunnels.add(new BossGravityFinFunnel(2, this, 0));
            bossFinFunnels.add(new BossDissectingFinFunnel(2, this, 0));
        }


    }


    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();

        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BOTTOM");


        addToBot(new ApplyPowerAction(this, this, new ConcordSnipePower(this, 1)));
//        addToBot(new ApplyPowerAction(this, this, new PotentialOutbreakPower(this, (int) (this.maxHealth * 0.5f), "Strike")));

    }


    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                addToBot(new VFXAction(new LaserBeamEffect(this.hb.cX, this.hb.cY + 60.0F * Settings.scale), 1.5F));
                this.damage.get(0).type = DamageInfo.DamageType.THORNS;
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE, true));

                break;
            case 2:
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractBossFinFunnel finFunnel = bossFinFunnels.get(1);
                        DamageInfo info = new DamageInfo(OriShionBoss.this, finFunnel.getFinalDamage(), DamageInfo.DamageType.THORNS);
                        finFunnel.activeFire(AbstractDungeon.player, info, true, 4);
                        isDone = true;
                    }
                });
                break;
            case 3:
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        AbstractBossFinFunnel finFunnel = bossFinFunnels.get(2);
                        DamageInfo info = new DamageInfo(OriShionBoss.this, finFunnel.getFinalDamage(), DamageInfo.DamageType.THORNS);
                        finFunnel.activeFire(AbstractDungeon.player, info, true, 4);
                        isDone = true;
                    }
                });
                break;
            case 4:
                addToBot(new TalkAction(this, DIALOG[0], 1.5F, 1.5F));
                addToBot(new GainShieldAction(this, 80));
                break;
            case 5:
                this.strike = false;
                addToBot(new TalkAction(this, DIALOG[1], 1.5F, 1.5F));
                addToBot(new BossTriggerDimensionSplitterAction());
                break;
        }

        addToBot(new RollMoveAction(this));
    }


    @Override
    protected void getMove(int num) {
        switch (this.moveCount) {
            default:
                setMove((byte) 1, Intent.ATTACK, this.damage.get(0).base);
                this.moveCount++;
                break;
            case 2:
                setMove((byte) 2, Intent.ATTACK_DEBUFF, this.damage.get(1).base,4,true);
                this.moveCount++;
                break;
            case 3:
                setMove((byte) 3, Intent.ATTACK_DEFEND, this.damage.get(2).base,4,true);
                this.moveCount = 2;
                break;
            case 4:
                setMove((byte) 4, Intent.DEFEND);
                this.moveCount++;
                break;
            case 5:
                setMove((byte) 5, Intent.ATTACK, this.damage.get(3).base);
                this.moveCount = 2;
                break;
        }


    }

    @Override
    public void changeState(String stateName) {
        switch (stateName) {
            case "Strike":
                this.moveCount = 4;
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
        if (this.strike) {
            if (!MinionGroup.areMinionsBasicallyDead()) {
                AbstractPlayerMinion minion = MinionGroup.getCurrentMinion();
                tmp += minion.maxHealth * 0.5f;

            } else
                tmp += target.maxHealth * 0.5f;
        }


        for (AbstractPower p : this.powers) {
            tmp = p.atDamageGive(tmp, DamageInfo.DamageType.THORNS);
        }


        for (AbstractPower p : target.powers) {
            tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.THORNS);
        }

        if (!MinionGroup.areMinionsBasicallyDead())
            for (AbstractPower p : MinionGroup.getCurrentMinion().powers) {
                tmp = p.atDamageReceive(tmp, DamageInfo.DamageType.THORNS);

            }

        tmp = AbstractDungeon.player.stance.atDamageReceive(tmp, DamageInfo.DamageType.THORNS);


        for (AbstractPower p : this.powers) {
            tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.THORNS);
        }


        for (AbstractPower p : target.powers) {
            tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.THORNS);
        }

        if (!MinionGroup.areMinionsBasicallyDead())
            for (AbstractPower p : MinionGroup.getCurrentMinion().powers) {
                tmp = p.atDamageFinalReceive(tmp, DamageInfo.DamageType.THORNS);
            }

        dmg = MathUtils.floor(tmp);
        if (dmg < 0) {
            dmg = 0;
        }

        ReflectionHacks.setPrivate(this, AbstractMonster.class, "intentDmg", dmg);
    }

    @Override
    public void die() {
        useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        super.die();
        AbstractDungeon.scene.fadeInAmbiance();
        onBossVictoryLogic();
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);

        if (this.currentHealth < this.maxHealth / 2 && !stateChanged) {
            this.stateChanged = true;
            this.strike = true;
            this.moveCount = 4;
            rollMove();
            createIntent();
            applyPowers();
        }

    }
}
