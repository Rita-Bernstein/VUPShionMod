package VUPShionMod.powers.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.Shion.AnticoagulationPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class BleedingPower extends AbstractShionPower implements HealthBarRenderPower {
    public static final String POWER_ID = VUPShionMod.makeID(BleedingPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private final AbstractCreature source;

    public BleedingPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.name = powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        updateDescription();
        loadShionRegion("BleedingPower");
    }


    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type == DamageInfo.DamageType.NORMAL) {
            onSpecificTrigger();
        }
        return super.onAttacked(info, damageAmount);
    }

    public void atStartOfTurn() {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            if (this.owner.hasPower(AnticoagulationPower.POWER_ID)) {
                this.owner.getPower(AnticoagulationPower.POWER_ID).flash();
            } else
                addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, (int) Math.ceil(this.amount * 0.5f)));
        }
    }

    @Override
    public void onSpecificTrigger() {
        flash();
        if (AbstractDungeon.player.hasPower(DefensiveOrderPower.POWER_ID)) {
            addToBot(new GainBlockAction(AbstractDungeon.player, this.amount));
        } else {
            addToTop(new LoseHPAction(this.owner, null, this.amount));
        }
    }

    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_POISON", 0.05F);
    }


    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public int getHealthBarAmount() {
        return this.amount;
    }

    @Override
    public Color getColor() {
        return new Color(127.0f / 256.0f, 12.0f / 256.0f, 0.0f, 1.0f);
    }
}


