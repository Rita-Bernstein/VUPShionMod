package VUPShionMod.powers.Monster.BossShion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class PotentialOutbreakPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(PotentialOutbreakPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private final String stateString;
    private boolean triggered = false;

    public PotentialOutbreakPower(AbstractCreature owner, int amount, String stateString) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.stateString = stateString;
        updateDescription();

        loadRegion("heartDef");
        this.priority = 99;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount > this.amount) {
            damageAmount = this.amount;
        }
        this.amount -= damageAmount;
        if (this.amount <= 0 && !this.triggered) {
            this.triggered = true;
            addToBot(new ChangeStateAction((AbstractMonster) this.owner, stateString));
            if (!AbstractDungeon.getCurrRoom().skipMonsterTurn && !AbstractDungeon.actionManager.turnHasEnded)
                addToBot(new PressEndTurnButtonAction());
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            this.amount = 0;
        }

        updateDescription();
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

}
