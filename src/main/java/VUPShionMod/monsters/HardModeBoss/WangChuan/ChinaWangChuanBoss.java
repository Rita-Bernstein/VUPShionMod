package VUPShionMod.monsters.HardModeBoss.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.CustomWaitAction;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.monsters.AbstractVUPShionBoss;
import VUPShionMod.monsters.HardModeBoss.EisluRen.ElfKnight;
import VUPShionMod.monsters.HardModeBoss.EisluRen.HighElf;
import VUPShionMod.powers.Monster.BossEisluRen.AutoShieldPower;
import VUPShionMod.powers.Monster.BossEisluRen.CoverPower;
import VUPShionMod.powers.Monster.BossEisluRen.LightArmorPower;
import VUPShionMod.powers.Monster.BossShion.PotentialOutbreakPower;
import VUPShionMod.powers.Monster.BossWangChuan.MagicFlyingBladePower;
import VUPShionMod.powers.Monster.PlagaAMundo.StrengthenPower;
import VUPShionMod.powers.Wangchuan.ImmuneDamagePower;
import VUPShionMod.powers.Wangchuan.MorsLibraquePower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import VUPShionMod.vfx.Common.PortraitWindyPetalEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.DemonFormPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class ChinaWangChuanBoss extends AbstractVUPShionBoss {
    public static final String ID = VUPShionMod.makeID(ChinaWangChuanBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;


    private int moveCount = 1;
    private boolean stateChanged = false;

    public ChinaWangChuanBoss() {
        super(NAME, ID, 88, 0.0F, -5.0F, 420.0F, 400.0F, null, 5.0F, -7.0f);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(600);
        } else {
            setHp(500);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 30));
            this.damage.add(new DamageInfo(this, 6));

        } else {
            this.damage.add(new DamageInfo(this, 30));
            this.damage.add(new DamageInfo(this, 6));
        }


        this.type = EnemyType.BOSS;
        this.dialogX = -70.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;


        loadAnimation(SkinManager.getSkin(1, 3).atlasURL,
                SkinManager.getSkin(1, 3).jsonURL,
                SkinManager.getSkin(1, 3).renderScale);

        this.state.setAnimation(0, "idle", true);
        this.state.setAnimation(1, "weapon_idle", true);

        this.flipHorizontal = true;


    }


    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();

        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");


        addToBot(new GainShieldAction(this, 100));
        addToBot(new ApplyPowerAction(this, this, new MagicFlyingBladePower(this, 4)));
        addToBot(new ApplyPowerAction(this, this, new PotentialOutbreakPower(this, (int) (this.maxHealth * 0.5f), "Heat")));
        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 4)));
    }


    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                addToBot(new VFXAction(new AbstractAtlasGameEffect("Fire 043 Right Transition", Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f,
                        96.0f, 54.0f, 10.0f * Settings.scale, 2, false,true)));
                addToBot(new CustomWaitAction(1.5f));
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE, true));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 3, true)));
                break;
            case 2:
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 2)));
                addToBot(new GainBlockAction(this, 30));
                break;
            case 3:
                for (int i = 0; i < 4; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE, true));
                addToBot(new GainBlockAction(this, 30));
                break;
            case 4:
                addToBot(new TalkAction(this, DIALOG[0], 1.5F, 1.5F));
                for (int i = 0; i < 4; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE, true));
                addToBot(new ApplyPowerAction(this,this,new ImmuneDamagePower(this)));
                break;
            case 5:
                addToBot(new TalkAction(this, DIALOG[1], 1.5F, 1.5F));
                addToBot(new VFXAction(new AbstractAtlasGameEffect("Smoke 037 Radial Transition", Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f,
                        96.0f, 54.0f, 10.0f * Settings.scale, 2, false,true)));
                addToBot(new VFXAction(new PortraitWindyPetalEffect("MorsLibraque",true),1.0f));
                addToBot(new CustomWaitAction(1.5f));
                addToBot(new ApplyPowerAction(AbstractDungeon.player,this,new MorsLibraquePower(AbstractDungeon.player,3)));
                addToBot(new ApplyPowerAction(this,this,new ImmuneDamagePower(this)));
                break;
        }

        addToBot(new RollMoveAction(this));
    }


    @Override
    protected void getMove(int num) {
        switch (this.moveCount) {
            default:
                setMove((byte) 1, Intent.ATTACK_DEBUFF, this.damage.get(0).base);
                this.moveCount++;
                break;
            case 2:
                setMove((byte) 2, Intent.DEFEND_BUFF);
                this.moveCount++;
                break;
            case 3:
                setMove((byte) 3, Intent.ATTACK_DEFEND, this.damage.get(1).base, 4, true);
                this.moveCount++;
                break;
            case 4:
                setMove((byte) 4, Intent.ATTACK_BUFF, this.damage.get(1).base, 4, true);
                this.moveCount = 1;
                break;
            case 5:
                setMove(MOVES[0],(byte) 5, Intent.STRONG_DEBUFF);
                this.moveCount = 3;
                break;
            case 6:
                setMove((byte) 3, Intent.ATTACK_DEFEND, this.damage.get(1).base, 4, true);
                break;
        }


    }

    @Override
    public void changeState(String stateName) {
        switch (stateName) {
            case "Heat":
                this.moveCount = 5;
                rollMove();
                createIntent();
                applyPowers();
                CardCrawlGame.sound.play("STANCE_ENTER_WRATH");
                break;
        }
    }

    @Override
    public void die() {
        useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);
        super.die();
        AbstractDungeon.scene.fadeInAmbiance();
        onBossVictoryLogic();
        onFinalBossVictoryLogic();
    }

}
