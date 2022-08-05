package VUPShionMod.minions;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.AddRefundChargeAction;
import VUPShionMod.powers.EisluRen.SpiritCloisterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;

public class ElfMinion extends AbstractPlayerMinion {
    public static final String ID = VUPShionMod.makeID(ElfMinion.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;

    public int timesUpgraded = 0;
    public boolean cannotSelected = false;

    public ElfMinion(int timesUpgraded) {
        super(NAME, ID, 88, 0.0F, -30.0F, 140.0F, 200.0F, null, 220.0F, 0.0f);


        this.timesUpgraded = timesUpgraded;

        this.damage.add(new DamageInfo(this, 5));

        this.type = MinionType.Elf;
        this.dialogX = -50.0F * Settings.scale;
        this.dialogY = 50.0F * Settings.scale;

        loadAnimation("VUPShionMod/characters/EisluRen/Elf/break_zhaohuanjingling.atlas",
                "VUPShionMod/characters/EisluRen/Elf/break_zhaohuanjingling.json", 8.0f);


        switch (timesUpgraded) {
            case 0:
                setHp(12);
                this.state.setAnimation(0, "idle_jingling", true);
                break;
            case 1:
                setHp(30);
                this.state.setAnimation(0, "idle_shangweijingling", true);
                break;
            case 2:
                setHp(30);
                this.state.setAnimation(0, "idle_qishi", true);
                break;
        }
        this.flipHorizontal = false;
    }

    private void reloadAnimation(int pre, int current) {
        if (pre >= current)
            return;

        if (pre == 0) {
            if (current == 1) {
                this.stateData.setMix("idle_jingling", "idle_shangweijingling", 0.0f);
                this.state.setAnimation(0, "idle_shangweijingling", true);
            } else {
                this.stateData.setMix("idle_jingling", "idle_qishi", 0.0f);
                this.state.setAnimation(0, "idle_qishi", true);
            }
        } else {
            this.stateData.setMix("idle_shangweijingling", "idle_qishi", 0.0f);
            this.state.setAnimation(0, "idle_qishi", true);
        }

    }


    @Override
    public void usePreBattleAction() {
    }

    public void usePreBattleAction(ElfMinion minion) {
    }

    public void summonElf(ElfMinion minion) {

        int pre = this.timesUpgraded;
        this.timesUpgraded = Math.max(((ElfMinion) minion).timesUpgraded, this.timesUpgraded);

        reloadAnimation(pre, this.timesUpgraded);


        init();
        applyPowers();
        createIntent();

        this.maxHealth += minion.maxHealth;
        AbstractDungeon.effectsQueue.add(new TextAboveCreatureEffect(this.hb.cX - this.animX, this.hb.cY,
                CardCrawlGame.languagePack.getUIString("AbstractCreature").TEXT[2] + Integer.toString(minion.maxHealth), Settings.GREEN_TEXT_COLOR));
        this.heal(minion.maxHealth, true);
        healthBarUpdatedEvent();
        this.cannotSelected = false;
        
        minion.usePreBattleAction(this);
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            default:
                if (this.targetMonster != null) {
                    addToBot(new AnimateFastAttackAction(this));
                    addToBot(new DamageAction(this.targetMonster, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE, true));
                    addToBot(new AddRefundChargeAction(1));
                }
                break;
        }
    }

    @Override
    protected void getMove(int num) {
        switch (this.timesUpgraded) {
            default:
                setMove((byte) 0, MinionIntent.ATTACK_BUFF, this.damage.get(0).base);
        }
    }

    @Override
    public void changeState(String stateName) {
    }

    @Override
    public void die() {

        if(AbstractDungeon.player.hasPower(SpiritCloisterPower.POWER_ID)){
            this.heal(1,true);
            return;
        }

        this.halfDead = true;
        this.cannotSelected = true;

    }
}
