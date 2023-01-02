package VUPShionMod.powers.Wangchuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.GameStatsPatch;
import VUPShionMod.powers.AbstractShionPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CorGladiiPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(CorGladiiPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public CorGladiiPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.priority = 0;

        loadShionRegion("CorGladiiPower");
    }


    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof AbstractShionPower) {
                ((AbstractShionPower) p).onStackPower(this,this.amount);
            }
        }

        for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
            if (card instanceof AbstractVUPShionCard) {
                ((AbstractVUPShionCard) card).onApplyCor();
            }
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        int pre = this.amount;
        super.stackPower(stackAmount);
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof AbstractShionPower) {
                ((AbstractShionPower) p).onStackPower(this,this.amount -  pre);
            }
        }


        for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
            if (card instanceof AbstractVUPShionCard) {
                ((AbstractVUPShionCard) card).onApplyCor();
            }
        }
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (AbstractDungeon.player.hasPower(PetalsFallPower.POWER_ID)) {
            AbstractShionPower p = (AbstractShionPower) AbstractDungeon.player.getPower(PetalsFallPower.POWER_ID);
            p.onNumSpecificTrigger(reduceAmount);
        }

        GameStatsPatch.corGladiiLoseThisTurn += reduceAmount;

        if (AbstractDungeon.player.hasPower(IntensaPower.POWER_ID)) {
            AbstractShionPower p = (AbstractShionPower) AbstractDungeon.player.getPower(IntensaPower.POWER_ID);
            p.updateDescription();
        }

    }

    @Override
    public void onRemove() {
        super.onRemove();
        if (AbstractDungeon.player.hasPower(PetalsFallPower.POWER_ID)) {
            AbstractShionPower p = (AbstractShionPower) AbstractDungeon.player.getPower(PetalsFallPower.POWER_ID);
            p.onNumSpecificTrigger(this.amount);
        }

        GameStatsPatch.corGladiiLoseThisTurn += this.amount;

        if (AbstractDungeon.player.hasPower(IntensaPower.POWER_ID)) {
            AbstractShionPower p = (AbstractShionPower) AbstractDungeon.player.getPower(IntensaPower.POWER_ID);
            p.updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}