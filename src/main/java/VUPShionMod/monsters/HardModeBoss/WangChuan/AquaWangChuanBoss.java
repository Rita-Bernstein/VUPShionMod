package VUPShionMod.monsters.HardModeBoss.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.actions.Unique.VersusEffectAction;
import VUPShionMod.monsters.AbstractVUPShionBoss;
import VUPShionMod.powers.Monster.BossShion.PotentialOutbreakPower;
import VUPShionMod.powers.Monster.BossWangChuan.StunPlayerPower;
import VUPShionMod.powers.Monster.PlagaAMundo.StrengthenPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.skins.sk.Shion.OriShion;
import VUPShionMod.skins.sk.WangChuan.AquaWangChuan;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class AquaWangChuanBoss extends AbstractVUPShionBoss {
    public static final String ID = VUPShionMod.makeID(AquaWangChuanBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;


    private int moveCount = 1;
    private boolean stateChanged = false;

    public AquaWangChuanBoss() {
        super(NAME, ID, 88, 0.0F, -5.0F, 420.0F, 400.0F, null, 5.0F, -7.0f);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(400);
        } else {
            setHp(350);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 7));
            this.damage.add(new DamageInfo(this, 8));
            this.damage.add(new DamageInfo(this, 3));

        } else {
            this.damage.add(new DamageInfo(this, 7));
            this.damage.add(new DamageInfo(this, 8));
            this.damage.add(new DamageInfo(this, 3));
        }


        this.type = EnemyType.BOSS;
        this.dialogX = -70.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;


        loadAnimation(SkinManager.getSkin(1, 2).atlasURL,
                SkinManager.getSkin(1, 2).jsonURL,
                SkinManager.getSkin(1, 2).renderScale);

        this.state.setAnimation(0, "idle", true);
        this.state.setAnimation(1, "idle_wing", true);

        this.flipHorizontal = true;
    }


    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();

        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_CITY");

        addToBot(new VersusEffectAction(AquaWangChuan.ID));
        addToBot(new GainShieldAction(this, 50));
        addToBot(new ApplyPowerAction(this, this, new PotentialOutbreakPower(this, (int) (maxHealth * 0.5f), "Infiniti")));
    }


    @Override
    public void takeTurn() {
        int temp;
        switch (this.nextMove) {
            case 1:
                attackAction(this);
                temp = 6;
                for (int i = 0; i < temp; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                addToBot(new GainBlockAction(this, 14));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1)));
                break;
            case 2:
                attackAction(this);
                temp = 3;
                for (int i = 0; i < temp; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                addToBot(new GainBlockAction(this, 21));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 2)));
                break;
            case 3:
                addToBot(new TalkAction(this, DIALOG[0], 1.5F, 1.5F));
                attackAction(this);
                temp = 8;
                for (int i = 0; i < temp; i++)
                    addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.SLASH_HEAVY));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new StunPlayerPower(AbstractDungeon.player)));
                break;
        }

        addToBot(new RollMoveAction(this));
    }


    @Override
    protected void getMove(int num) {
        switch (this.moveCount) {
            default:
                setMove((byte) 1, Intent.ATTACK_BUFF, this.damage.get(0).base, 6, true);
                if (!this.stateChanged)
                    this.moveCount++;
                else
                    this.moveCount = 1;
                break;
            case 2:
                setMove((byte) 2, Intent.ATTACK_BUFF, this.damage.get(1).base, 3, true);
                this.moveCount = 1;
                break;
            case 3:
                setMove((byte) 3, Intent.ATTACK_DEBUFF, this.damage.get(2).base, 8, true);
                this.moveCount = 1;
                break;
        }


    }

    @Override
    public void changeState(String stateName) {
        switch (stateName) {
            case "Infiniti":
                this.moveCount = 3;
                this.stateChanged = true;
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

    }

}
