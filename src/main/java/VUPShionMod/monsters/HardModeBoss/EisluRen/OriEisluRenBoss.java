package VUPShionMod.monsters.HardModeBoss.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.monsters.AbstractVUPShionBoss;
import VUPShionMod.powers.Liyezhu.CrimsonDelugePower;
import VUPShionMod.powers.Monster.BossEisluRen.AutoShieldPower;
import VUPShionMod.powers.Monster.BossEisluRen.CoverPower;
import VUPShionMod.powers.Monster.BossEisluRen.LightArmorPower;
import VUPShionMod.powers.Monster.BossShion.PotentialOutbreakPower;
import VUPShionMod.powers.Monster.PlagaAMundo.StrengthenPower;
import VUPShionMod.skins.SkinManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class OriEisluRenBoss extends AbstractVUPShionBoss {
    public static final String ID = VUPShionMod.makeID(OriEisluRenBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;


    private int moveCount = 1;
    private static String currentIdle = "Idle";
    private boolean stateChanged = false;


    private int randomDamageResult = 0;

    public OriEisluRenBoss() {
        super(NAME, ID, 88, 0.0F, -5.0F, 420.0F, 400.0F, null, 5.0F, -7.0f);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(400);
        } else {
            setHp(350);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 24));
            this.damage.add(new DamageInfo(this, 36));
            this.damage.add(new DamageInfo(this, 48));
            this.damage.add(new DamageInfo(this, 3));
        } else {
            this.damage.add(new DamageInfo(this, 24));
            this.damage.add(new DamageInfo(this, 36));
            this.damage.add(new DamageInfo(this, 48));
            this.damage.add(new DamageInfo(this, 3));
        }


        this.type = EnemyType.BOSS;
        this.dialogX = -70.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;


        loadAnimation(SkinManager.getSkin(3, 0).atlasURL,
                SkinManager.getSkin(3, 0).jsonURL,
                SkinManager.getSkin(3, 0).renderScale);


        this.state.setAnimation(0, "idle", true);
        this.state.setAnimation(1, "wings_main_in", false);
        this.state.addAnimation(1, "wings_normal_make_up", false, 0.0f).setTimeScale(2.0f);
        this.state.addAnimation(1, "wings_normal_idle", true, 0.0f);

        this.flipHorizontal = true;


    }


    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();

        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_CITY");


        addToBot(new GainShieldAction(this, 21));
        addToBot(new ApplyPowerAction(this, this, new DemonFormPower(this, 1)));
        addToBot(new ApplyPowerAction(this, this, new AutoShieldPower(this, 10)));
        addToBot(new ApplyPowerAction(this, this, new CoverPower(this)));
//        addToBot(new ApplyPowerAction(this, this, new PotentialOutbreakPower(this, (int) (this.maxHealth * 0.5f), "Full")));

    }


    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                addToBot(new SpawnMonsterAction(new HighElf(), false));
                break;
            case 2:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(this.randomDamageResult), AbstractGameAction.AttackEffect.FIRE));
                break;
            case 3:
                addToBot(new TalkAction(this, DIALOG[0], 1.5F, 1.5F));
                addToBot(new ChangeStateAction(this, "LotusOfWarAnimation"));
                addToBot(new GainShieldAction(this, 100));
                addToBot(new ApplyPowerAction(this, this, new StrengthenPower(this, 1)));

                break;
            case 4:
                addToBot(new TalkAction(this, DIALOG[0], 1.5F, 1.5F));
                addToBot(new ChangeStateAction(this, "SpiralBladeAnimation"));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 5)));
                break;
            case 5:
                for (int i = 0; i < 6; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(3), AbstractGameAction.AttackEffect.FIRE));
                break;
            case 6:
                addToBot(new GainShieldAction(this, 25));
                break;
            case 7:
                addToBot(new TalkAction(this, DIALOG[1], 1.5F, 1.5F));
                addToBot(new ChangeStateAction(this, "Full7"));
                addToBot(new GainShieldAction(this, 100));
                addToBot(new ApplyPowerAction(this, this, new AutoShieldPower(this, 30)));
                break;
            case 8:
                addToBot(new TalkAction(this, DIALOG[1], 1.5F, 1.5F));
                addToBot(new ChangeStateAction(this, "Full8"));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 5)));
                addToBot(new GainShieldAction(this, 30));
                addToBot(new ApplyPowerAction(this, this, new LightArmorPower(this, 50)));
                break;
            case 9:
                addToBot(new TalkAction(this, DIALOG[1], 1.5F, 1.5F));
                addToBot(new ChangeStateAction(this, "Full9"));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 10)));
                break;
            case 10:
                addToBot(new TalkAction(this, DIALOG[2], 1.5F, 1.5F));
                AbstractMonster m = new ElfKnight();
                addToBot(new SpawnMonsterAction(m, false));
                addToBot(new ApplyPowerAction(m,this,new IntangiblePlayerPower(m,2)));
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
                randomDamageResult = AbstractDungeon.aiRng.random(0, 2);
                setMove((byte) 2, Intent.ATTACK, this.damage.get(randomDamageResult).base);
                this.moveCount = AbstractDungeon.aiRng.random(3, 4);
                break;
            case 3:
                setMove((byte) 3, Intent.DEFEND_BUFF);
                this.moveCount = 5;
                break;
            case 4:
                setMove((byte) 4, Intent.DEFEND_BUFF);
                this.moveCount = 5;
                break;
            case 5:
                setMove((byte) 5, Intent.ATTACK, this.damage.get(3).base, 6, true);
                this.moveCount = 6;
                break;
            case 6:
                setMove((byte) 6, Intent.DEFEND);
                this.moveCount = 5;
                break;
            case 7:
                setMove((byte) 7, Intent.DEFEND_BUFF);
                this.moveCount = 10;
                break;
            case 8:
                setMove((byte) 8, Intent.DEFEND_BUFF);
                this.moveCount = 10;
                break;
            case 9:
                setMove((byte) 9, Intent.BUFF);
                this.moveCount = 10;
                break;
            case 10:
                setMove((byte) 10, Intent.UNKNOWN);
                this.moveCount = 5;
                break;
        }


    }

    @Override
    public void changeState(String stateName) {
        switch (stateName) {
            case "LotusOfWarAnimation":
                this.state.setAnimation(1, "wings_normal_relieves", false).setTimeScale(2.0f);
                this.state.addAnimation(1, "wings_Lotus_of_war_make_up", false, 0.0f).setTimeScale(2.0f);
                this.state.addAnimation(1, "wings_Lotus_of_war_idle", true, 0.0f);
                currentIdle = "LotusOfWarAnimation";
                break;
            case "SpiralBladeAnimation":
                this.state.setAnimation(1, "wings_normal_relieves", false).setTimeScale(2.0f);
                this.state.addAnimation(1, "wings_Spiral_knife_make_up", false, 0.0f).setTimeScale(2.0f);
                this.state.addAnimation(1, "wings_Spiral_knife_idle", true, 0.0f);
                currentIdle = "SpiralBladeAnimation";
                break;
            case "Full7":
                closeWing();
                this.state.addAnimation(1, "wings_Ruins_guard_make_up", false, 0.0f).setTimeScale(2.0f);
                this.state.addAnimation(1, "wings_Ruins_guard_idle", true, 0.0f);
            break;
            case "Full8":
                closeWing();
                this.state.addAnimation(1, "wings_Light_armor_make_up", false, 0.0f).setTimeScale(2.0f);
                this.state.addAnimation(1, "wings_Light_armor_idle", true, 0.0f);
                break;
            case "Full9":
                closeWing();
                this.state.addAnimation(1, "wings_Thousand_heavy_blade_make_up", false, 0.0f).setTimeScale(2.0f);
                this.state.addAnimation(1, "wings_Thousand_heavy_blade_idle", true, 0.0f);
                break;
        }
    }

    private void closeWing(){
        switch (currentIdle) {
            case "Idle":
                this.state.setAnimation(1, "wings_normal_relieves", false).setTimeScale(2.0f);
                break;
            case "LotusOfWarAnimation":
                this.state.setAnimation(1, "wings_Lotus_of_war_relieves", false).setTimeScale(2.0f);
                break;
            case "SpiralBladeAnimation":
                this.state.setAnimation(1, "wings_Spiral_knife_relieves", false).setTimeScale(2.0f);
                break;
        }
    }


    @Override
    public void damage(DamageInfo info) {
        super.damage(info);

        if (this.currentHealth <= this.maxHealth / 2 && !this.stateChanged) {
            this.stateChanged = true;
            this.moveCount = AbstractDungeon.aiRng.random(7, 9);
            rollMove();
            createIntent();
            applyPowers();

        }

    }

    @Override
    public void die() {
        useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        super.die();
        AbstractDungeon.scene.fadeInAmbiance();
        onBossVictoryLogic();

    }

}
