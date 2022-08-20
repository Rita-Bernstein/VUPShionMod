package VUPShionMod.monsters.HardModeBoss.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.monsters.AbstractVUPShionBoss;
import VUPShionMod.powers.Liyezhu.CrimsonDelugePower;
import VUPShionMod.powers.Monster.BossLiyezhu.CrimsonDelugeBossPower;
import VUPShionMod.powers.Monster.BossShion.*;
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
import com.megacrit.cardcrawl.powers.*;

public class OriLiyezhuBoss extends AbstractVUPShionBoss {
    public static final String ID = VUPShionMod.makeID(OriLiyezhuBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;


    private int moveCount = 1;

    public OriLiyezhuBoss() {
        super(NAME, ID, 88, 0.0F, -5.0F, 420.0F, 400.0F, null, 5.0F, -7.0f);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(500);
        } else {
            setHp(400);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 5));
            this.damage.add(new DamageInfo(this, 3));
        } else {
            this.damage.add(new DamageInfo(this, 5));
            this.damage.add(new DamageInfo(this, 3));
        }


        this.type = EnemyType.BOSS;
        this.dialogX = -70.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;


        loadAnimation(SkinManager.getSkin(2, 0).atlasURL,
                SkinManager.getSkin(2, 0).jsonURL,
                SkinManager.getSkin(2, 0).renderScale);


        this.state.setAnimation(0, "idle_normal", true);
        this.state.setAnimation(1, "idle_wings", true);
        this.state.setAnimation(2, "idle_xiaobingpian", true);
        this.state.setAnimation(3, "change_xiaobingpian_off", false);
        this.flipHorizontal = true;


    }


    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();

        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BOTTOM");


        addToBot(new ApplyPowerAction(this, this, new CrimsonDelugeBossPower(this, 5)));
        addToBot(new ApplyPowerAction(this, this, new ThornsPower(this, 1)));
        addToBot(new ApplyPowerAction(this, this, new PotentialOutbreakPower(this, (int) (this.maxHealth * 0.5f), "Judge")));

    }


    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                addToBot(new GainBlockAction(this, 20));
                addToBot(new ApplyPowerAction(this, this, new VulnerablePower(this, 2, true)));
                break;
            case 2:
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1)));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true)));
                break;
            case 3:
                for (int i = 0; i < 5; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));

                addToBot(new HealAction(this, this, 10));

                break;
            case 4:
                addToBot(new TalkAction(this, DIALOG[0], 1.5F, 1.5F));
                addToBot(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, 10)));
                addToBot(new RemoveDebuffsAction(this));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 3)));
                addToBot(new ApplyPowerAction(this, this, new CombustPower(this, 1, 5) {
                    @Override
                    public void atEndOfTurn(boolean isPlayer) {
                        flash();
                        addToBot(new LoseHPAction(owner, owner, 1, AbstractGameAction.AttackEffect.FIRE));
                        addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(owner, 5, DamageInfo.DamageType.THORNS),
                                AbstractGameAction.AttackEffect.FIRE));
                    }
                }, 5));
                break;
            case 5:
                for (int i = 0; i < 4; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.FIRE));
                break;
        }

        addToBot(new RollMoveAction(this));
    }


    @Override
    protected void getMove(int num) {
        switch (this.moveCount) {
            default:
                setMove((byte) 1, Intent.DEFEND);
                this.moveCount++;
                break;
            case 2:
                setMove((byte) 2, Intent.DEBUFF);
                this.moveCount++;
                break;
            case 3:
                setMove((byte) 3, Intent.ATTACK_BUFF, damage.get(0).base, 5, true);
                this.moveCount = 1;
                break;
            case 4:
                setMove((byte) 4, Intent.BUFF);
                this.moveCount++;
                break;
            case 5:
                setMove((byte) 3, Intent.ATTACK_BUFF, damage.get(1).base, 4, true);
                break;

        }


    }

    @Override
    public void changeState(String stateName) {
        switch (stateName) {
            case "Judge":
                this.moveCount = 4;
                rollMove();
                createIntent();
                applyPowers();
                this.state.setAnimation(4, "change_bajian", false);
                this.state.setAnimation(3, "change_xiaobingpian_on", false);
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
    }

}
