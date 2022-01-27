package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MagiamObruorPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("MagiamObruorPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public MagiamObruorPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/MagiamObruorPower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/MagiamObruorPower36.png")), 0, 0, 36, 36);
    }

    @Override
    public void atStartOfTurn() {
        flash();
        if (this.owner.hasPower(MensVirtusquePower.POWER_ID)) {
            AbstractPower p = this.owner.getPower(MensVirtusquePower.POWER_ID);
            p.flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CorGladiiPower(AbstractDungeon.player, p.amount * this.amount)));
        } else
            addToBot(new LoseEnergyAction(this.amount));

        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,MagiamObruorPower.POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = this.amount > 1 ? String.format(DESCRIPTIONS[1], this.amount) : DESCRIPTIONS[0];
    }
}