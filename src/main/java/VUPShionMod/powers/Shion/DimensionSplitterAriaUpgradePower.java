package VUPShionMod.powers.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.relics.Shion.DimensionSplitterAria;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.UUID;

public class DimensionSplitterAriaUpgradePower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(DimensionSplitterAriaUpgradePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DimensionSplitterAriaUpgradePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID + UUID.randomUUID();
        this.owner = owner;
        this.amount = amount;
        loadShionRegion("DimensionSplitterAriaUpgrade");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(CardTagsEnum.FIN_FUNNEL)) {
            this.amount--;
            updateDescription();
            if (this.amount <= 0) {
                AbstractRelic relic = AbstractDungeon.player.getRelic(DimensionSplitterAria.ID);
                if (relic != null) {
                    this.flash();
                    relic.counter++;
                    ((DimensionSplitterAria) relic).setDescriptionAfterLoading();
                }
                addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
        }
    }
}
