package VUPShionMod.powers.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.relics.Shion.ConcordCompanion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;

public class HyperdimensionalLinksPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(HyperdimensionalLinksPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HyperdimensionalLinksPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        loadShionRegion("SupportArmamentPower");
        updateDescription();
        this.priority = 0;

        if (AbstractDungeon.player.hasRelic(ConcordCompanion.ID) && this.amount > 9) {
            this.amount = 9;
        }
    }


    @Override
    public void onVictory() {
        super.onVictory();
        if (this.amount >= 10)
            randomDeckUpgrade();
//            AbstractDungeon.effectList.add(new CampfireSmithEffect());
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);

        if (AbstractDungeon.player.hasRelic(ConcordCompanion.ID) && this.amount > 9) {
            this.amount = 9;
        }

    }

    public void randomDeckUpgrade() {
        ArrayList<AbstractCard> possibleCards = new ArrayList<AbstractCard>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade()) {
                possibleCards.add(c);
            }
        }

        AbstractCard theCard = null;
        if (!possibleCards.isEmpty()) {
            theCard = possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
            theCard.upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(theCard);
        }

        if (theCard != null) {
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(theCard.makeStatEquivalentCopy()));
            addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
