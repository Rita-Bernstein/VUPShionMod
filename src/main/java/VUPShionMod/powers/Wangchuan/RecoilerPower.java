package VUPShionMod.powers.Wangchuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RecoilerPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(RecoilerPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public RecoilerPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/CircuitPower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/CircuitPower32.png")), 0, 0, 32, 32);
        updateDescription();
        this.isTurnBased = true;
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }

    @Override
    public void onStackPower(AbstractPower power) {
        if (power.ID.equals(MagiamObruorPower.POWER_ID)) {
            flash();
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.amount, true),
                    DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        }
    }
}
