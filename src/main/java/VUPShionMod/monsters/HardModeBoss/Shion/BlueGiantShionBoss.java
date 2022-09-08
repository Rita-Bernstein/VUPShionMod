package VUPShionMod.monsters.HardModeBoss.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.CustomWaitAction;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.actions.Unique.BossFinFunnelMinionAction;
import VUPShionMod.actions.Unique.RemovePlayerBuffAction;
import VUPShionMod.actions.Unique.TriggerAllBossFinFunnelPassiveAction;
import VUPShionMod.actions.Unique.TurnTriggerAllBossFinFunnelAction;
import VUPShionMod.minions.AbstractPlayerMinion;
import VUPShionMod.minions.MinionGroup;
import VUPShionMod.monsters.HardModeBoss.Shion.bossfinfunnels.*;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.powers.Monster.BossShion.*;
import VUPShionMod.powers.Shion.GravitoniumPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
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

public class BlueGiantShionBoss extends AbstractShionBoss {
    public static final String ID = VUPShionMod.makeID(BlueGiantShionBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;


    private int moveCount = 1;
    private boolean stateChanged = false;

    public BlueGiantShionBoss() {
        super(NAME, ID, 88, 0.0F, -5.0F, 520.0F, 460.0F, null, 5.0F, -7.0f);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(450);
        } else {
            setHp(350);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 3));
            this.damage.add(new DamageInfo(this, 4));
        } else {
            this.damage.add(new DamageInfo(this, 3));
            this.damage.add(new DamageInfo(this, 4));
        }


        this.type = EnemyType.BOSS;
        this.dialogX = -70.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;


        loadAnimation(SkinManager.getSkin(0, 1).atlasURL,
                SkinManager.getSkin(0, 1).jsonURL,
                SkinManager.getSkin(0, 1).renderScale);


        this.state.setAnimation(0, "idle", true);
        this.state.setAnimation(4, "wing_idle", true);
        this.flipHorizontal = true;

        if (bossFinFunnels.isEmpty()) {
            bossFinFunnels.add(new BossMatrixFinFunnel(6, this, 1));
            bossFinFunnels.add(new BossPursuitFinFunnel(6, this, 1));
            bossFinFunnels.add(new BossGravityFinFunnel(6, this, 1));
            bossFinFunnels.add(new BossDissectingFinFunnel(6, this, 1));
        }


    }


    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();

        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_CITY");


        addToBot(new GainShieldAction(this, 100));
        addToBot(new ApplyPowerAction(this, this, new GravitoniumPower(this)));
//        addToBot(new ApplyPowerAction(this, this, new PotentialOutbreakPower(this, (int) (this.maxHealth * 0.3f), "Time")));

    }


    @Override
    public void takeTurn() {
        int temp;
        switch (this.nextMove) {
            case 1:
                temp = 3;
                for (int i = 0; i < temp; i++)
                    addToBot(new TriggerAllBossFinFunnelPassiveAction(this));
                break;
            case 2:
                temp = 2;
                for (int i = 0; i < temp; i++)
                    addToBot(new TriggerAllBossFinFunnelPassiveAction(this));
                break;
            case 3:
                addToBot(new BossFinFunnelMinionAction(this));
                break;
            case 4:
                addToBot(new SFXAction("SHION_20"));
                addToBot(new TalkAction(this, DIALOG[0], 1.5F, 1.5F));
                addToBot(new GainShieldAction(this, 100));
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        for (AbstractBossFinFunnel finFunnel : bossFinFunnels) {
                            finFunnel.levelForCombat += 3;
                        }
                        isDone = true;
                    }
                });
                break;

        }

        addToBot(new RollMoveAction(this));
    }


    @Override
    protected void getMove(int num) {
        switch (this.moveCount) {
            default:
                setMove((byte) 1, Intent.STRONG_DEBUFF);
                this.moveCount++;
                break;
            case 2:
                setMove((byte) 2, Intent.DEBUFF);
                this.moveCount++;
                break;
            case 3:
                setMove((byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(this.stateChanged ? 1 : 0).base, 8, true);
                if (!this.stateChanged)
                    this.moveCount = 1;
                else
                    this.moveCount = 3;
                break;
            case 4:
                setMove((byte) 4, Intent.DEFEND_BUFF);
                this.moveCount = 3;
                break;
        }


    }

    @Override
    public void changeState(String stateName) {
        switch (stateName) {
            case "Matrix":
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
            this.moveCount = 4;
            rollMove();
            createIntent();
            applyPowers();
        }

    }
}
