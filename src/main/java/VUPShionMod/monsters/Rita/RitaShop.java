package VUPShionMod.monsters.Rita;


import VUPShionMod.VUPShionMod;
import VUPShionMod.monsters.AbstractVUPShionBoss;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RitaShop extends AbstractVUPShionBoss {
    public static final String ID = VUPShionMod.makeID(RitaShop.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;



    private int timeLimit = 4;



    public RitaShop() {
        super(NAME, ID, 160, 0.0F, 0.0F, 300.0F, 380.0F, "VUPShionMod/img/monsters/Rita/maid/MaidRita.png", 0.0F, 0.0F);
        if (AbstractDungeon.ascensionLevel >= 9) {
            setHp(630);
        } else {
            setHp(600);
        }

        this.type = AbstractMonster.EnemyType.BOSS;
        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;


//        loadAnimation("VUPShionMod/img/monsters/Rita/Rita.atlas", "VUPShionMod/img/monsters/Rita/Rita.json", 0.9F);
//
//        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
//        e.setTime(e.getEndTime() * MathUtils.random());
//        e.setTimeScale(0.8F);

        this.monsterIntent = new RitaBaseGameIntent(this);
        this.monsterIntent.initDamage();
    }


    //----------------------------------------------------------------------------------------------------

    public void usePreBattleAction() {
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.silenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();

        this.monsterIntent.usePreBattleAction();
    }


    public void takeTurn() {
        this.monsterIntent.takeTurn();
    }


    //死亡相关
    public void die() {
        CardCrawlGame.music.silenceTempBgmInstantly();
        CardCrawlGame.music.unsilenceBGM();
        addToBot(new ShoutAction(this, DIALOG[0]));
        super.die();

        useFastShakeAnimation(5.0F);
        CardCrawlGame.screenShake.rumble(4.0F);

        this.deathTimer += 1.5f;
        CardCrawlGame.sound.play(VUPShionMod.makeID("RitaB_Die"));

    }


    public void changeState(String stateName) {
        this.monsterIntent.changeState(stateName);
    }
//行动规律-----------------

    protected void getMove(int num) {
        this.monsterIntent.getMove(num);
    }


    public void damage(DamageInfo info) {
//结算伤害
        super.damage(info);

        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
//            this.state.setAnimation(0, "Hit", false);

            if (MathUtils.random(3) == 0 && this.lastDamageTaken > 30) {
                CardCrawlGame.sound.play(VUPShionMod.makeID("RitaB_Hit" + MathUtils.random(5)));
            }
        }
    }


    @Override
    public void update() {
        super.update();


    }
}

