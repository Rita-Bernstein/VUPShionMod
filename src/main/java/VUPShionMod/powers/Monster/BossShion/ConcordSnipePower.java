package VUPShionMod.powers.Monster.BossShion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.Shion.LockOnPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class ConcordSnipePower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(ConcordSnipePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public ConcordSnipePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/ConcordSnipePower128.png"), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/ConcordSnipePower48.png"), 0, 0, 48, 48);

    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, this.owner, new LockOnPower(AbstractDungeon.player, this.amount)));
    }



    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

}
