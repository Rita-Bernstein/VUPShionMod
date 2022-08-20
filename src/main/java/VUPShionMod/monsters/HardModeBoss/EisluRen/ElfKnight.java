package VUPShionMod.monsters.HardModeBoss.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.GainRefundChargeAction;
import VUPShionMod.monsters.AbstractVUPShionBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class ElfKnight extends AbstractVUPShionBoss {
    public static final String ID = VUPShionMod.makeID(ElfKnight.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public ElfKnight() {
        super(NAME, ID, 88, 0.0F, -30.0F, 140.0F, 200.0F, null, -380.0F, 30.0f);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(30);
        } else {
            setHp(30);
        }


        if (AbstractDungeon.ascensionLevel >= 4) {
            this.damage.add(new DamageInfo(this, 20));
        } else {
            this.damage.add(new DamageInfo(this, 20));
        }


        this.type = EnemyType.NORMAL;
        this.dialogX = -70.0F * Settings.scale;
        this.dialogY = 100.0F * Settings.scale;

        loadAnimation("VUPShionMod/characters/EisluRen/Elf/break_zhaohuanjingling.atlas",
                "VUPShionMod/characters/EisluRen/Elf/break_zhaohuanjingling.json", 8.0f);

        this.stateData.setMix("idle_jingling", "idle_qishi", 0.0f);
        this.state.setAnimation(0, "idle_qishi", true);

        this.flipHorizontal = true;


    }


    @Override
    public void usePreBattleAction() {
        addToBot(new ApplyPowerAction(this, this, new IntangiblePlayerPower(this, 2)));
    }


    @Override
    public void takeTurn() {
        addToBot(new AnimateFastAttackAction(this));
        addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE, true));
        addToBot(new GainRefundChargeAction(2));

        addToBot(new RollMoveAction(this));
    }


    @Override
    protected void getMove(int num) {
        setMove((byte) 0, Intent.ATTACK, this.damage.get(0).base);
    }
}
