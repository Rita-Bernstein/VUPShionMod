package VUPShionMod.powers.Wangchuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
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

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/CorGladiiPower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/CorGladiiPower36.png")), 0, 0, 36, 36);
    }


    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        for(AbstractPower p : AbstractDungeon.player.powers){
            if(p instanceof AbstractShionPower){
                ((AbstractShionPower) p).onStackPower(this);
            }
        }

        for(AbstractCard card: AbstractDungeon.player.discardPile.group){
            if(card instanceof AbstractVUPShionCard){
                ((AbstractVUPShionCard) card).onApplyCor();
            }
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        for(AbstractPower p : AbstractDungeon.player.powers){
            if(p instanceof AbstractShionPower){
                ((AbstractShionPower) p).onStackPower(this);
            }
        }


        for(AbstractCard card: AbstractDungeon.player.discardPile.group){
            if(card instanceof AbstractVUPShionCard){
                ((AbstractVUPShionCard) card).onApplyCor();
            }
        }
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if (AbstractDungeon.player.hasPower(PetalsFallPower.POWER_ID)) {
            AbstractShionPower p =(AbstractShionPower)AbstractDungeon.player.getPower(PetalsFallPower.POWER_ID);
            p.onNumSpecificTrigger(reduceAmount);
        }
    }

    @Override
    public void onRemove() {
        super.onRemove();
        if (AbstractDungeon.player.hasPower(PetalsFallPower.POWER_ID)) {
            AbstractShionPower p =(AbstractShionPower)AbstractDungeon.player.getPower(PetalsFallPower.POWER_ID);
            p.onNumSpecificTrigger(this.amount);
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}