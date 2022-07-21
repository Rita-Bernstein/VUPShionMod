package VUPShionMod.powers.Monster.PlagaAMundo;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Unique.ExhaustRarityCardAction;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class DestroyPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(DestroyPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DestroyPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/DefectPower128.png"), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/DefectPower48.png"), 0, 0, 48, 48);

        updateDescription();
    }

    @Override
    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && info.type != DamageInfo.DamageType.THORNS) {
            for (int i = 0; i < this.amount; i++)
                addToBot(new ExhaustRarityCardAction());
        }
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }
}
