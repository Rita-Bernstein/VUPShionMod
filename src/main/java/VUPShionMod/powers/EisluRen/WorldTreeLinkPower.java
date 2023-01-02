package VUPShionMod.powers.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class WorldTreeLinkPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(WorldTreeLinkPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public WorldTreeLinkPower(AbstractCreature owner) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        updateDescription();
        this.isTurnBased = true;
        loadShionRegion("WorldTreeLinkPower");
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        BaseMod.MAX_HAND_SIZE += 1;
    }

    @Override
    public void onRemove() {
        BaseMod.MAX_HAND_SIZE -= 1;
    }

    @Override
    public void onVictory() {
        BaseMod.MAX_HAND_SIZE -= 1;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new ExpertiseAction(this.owner, BaseMod.MAX_HAND_SIZE));
    }
}
