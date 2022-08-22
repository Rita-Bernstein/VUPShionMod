package VUPShionMod.monsters.HardModeBoss.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.CustomWaitAction;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.monsters.AbstractVUPShionBoss;
import VUPShionMod.powers.Monster.BossShion.PotentialOutbreakPower;
import VUPShionMod.powers.Monster.BossWangChuan.MagicFlyingBladePower;
import VUPShionMod.powers.Monster.BossWangChuan.WhiteRosePower;
import VUPShionMod.powers.Wangchuan.ImmuneDamagePower;
import VUPShionMod.powers.Wangchuan.MorsLibraquePower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import VUPShionMod.vfx.Common.PortraitWindyPetalEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.*;

public class PurityWangChuanBoss extends AbstractVUPShionBoss {
    public static final String ID = VUPShionMod.makeID(PurityWangChuanBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;


    private int moveCount = 1;
    private boolean inThunder = false;

    public PurityWangChuanBoss() {
        super(NAME, ID, 88, 0.0F, -5.0F, 420.0F, 400.0F, null, 5.0F, -7.0f);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(600);
        } else {
            setHp(500);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 5));
            this.damage.add(new DamageInfo(this, 2));

        } else {
            this.damage.add(new DamageInfo(this, 5));
            this.damage.add(new DamageInfo(this, 2));
        }


        this.type = EnemyType.BOSS;
        this.dialogX = -70.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;


        loadAnimation(SkinManager.getSkin(1, 1).atlasURL,
                SkinManager.getSkin(1, 1).jsonURL,
                SkinManager.getSkin(1, 1).renderScale);

        this.state.setAnimation(0, "idle", true);

        this.flipHorizontal = true;


    }


    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();

        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");


        addToBot(new GainShieldAction(this, 100));
        addToBot(new ApplyPowerAction(this, this, new WhiteRosePower(this, 1)));
        addToBot(new ApplyPowerAction(this, this, new PotentialOutbreakPower(this, (int) (this.maxHealth * 0.5f), "Thunder")));
        addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 4)));
    }


    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY, true));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 3)));
                addToBot(new GainBlockAction(this, 10));
                break;
            case 2:
                for (int i = 0; i < 2; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY, true));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 2)));
                addToBot(new GainBlockAction(this, 40));
                addToBot(new ApplyPowerAction(this, this, new BlurPower(this, 4)));
                break;
            case 3:
                for (int i = 0; i < 4; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY, true));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1)));
                break;
            case 4:
                addToBot(new TalkAction(this, DIALOG[0], 1.5F, 1.5F));
                for (int i = 0; i < 7; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY, true));
                addToBot(new ApplyPowerAction(this, this, new IntangiblePlayerPower(this, 2)));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1)));
                break;
        }

        addToBot(new RollMoveAction(this));
    }


    @Override
    protected void getMove(int num) {
        if (inThunder) {
            if (num < 25) {
                if (lastMove((byte) 1))
                    setMove((byte) 2, Intent.ATTACK_DEFEND, this.damage.get(0).base, 2, true);
                else
                    setMove((byte) 1, Intent.ATTACK_DEFEND, this.damage.get(0).base);
            } else if (num < 50) {
                if (lastMove((byte) 2))
                    setMove((byte) 3, Intent.ATTACK_BUFF, this.damage.get(0).base, 4, true);
                else
                    setMove((byte) 2, Intent.ATTACK_DEFEND, this.damage.get(0).base, 2, true);
            } else if (num < 75) {
                if (lastMove((byte) 3))
                    setMove((byte) 4, Intent.ATTACK_BUFF, this.damage.get(1).base, 7, true);
                else
                    setMove((byte) 3, Intent.ATTACK_BUFF, this.damage.get(0).base, 4, true);
            } else {
                if (lastMove((byte) 4))
                    setMove((byte) 1, Intent.ATTACK_DEFEND, this.damage.get(0).base);
                else
                    setMove((byte) 4, Intent.ATTACK_BUFF, this.damage.get(1).base, 7, true);
            }

        } else {
            if (num < 33) {
                if (lastMove((byte) 1))
                    setMove((byte) 2, Intent.ATTACK_DEFEND, this.damage.get(0).base, 2, true);
                else
                    setMove((byte) 1, Intent.ATTACK_DEFEND, this.damage.get(0).base);
            } else if (num < 66) {
                if (lastMove((byte) 2))
                    setMove((byte) 3, Intent.ATTACK_BUFF, this.damage.get(0).base, 4, true);
                else
                    setMove((byte) 2, Intent.ATTACK_DEFEND, this.damage.get(0).base, 2, true);
            } else {
                if (lastMove((byte) 3))
                    setMove((byte) 1, Intent.ATTACK_DEFEND, this.damage.get(0).base);
                else
                    setMove((byte) 3, Intent.ATTACK_BUFF, this.damage.get(0).base, 4, true);
            }
        }


    }

    @Override
    public void changeState(String stateName) {
        switch (stateName) {
            case "Thunder":
                setMove((byte) 4, Intent.ATTACK_BUFF, this.damage.get(1).base, 7, true);
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


    @Override
    public boolean hasPower(String targetID) {
        if (targetID.equals(BarricadePower.POWER_ID)) {
            for (AbstractPower power : this.powers) {
                if (power.ID.equals(BlurPower.POWER_ID))
                    return true;
            }
        }

        return super.hasPower(targetID);
    }
}
