package VUPShionMod.powers.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ConcordPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("ConcordPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ConcordPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/HolyCoffinSinkingSpiritPower128.png"), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/HolyCoffinSinkingSpiritPower48.png"), 0, 0, 48, 48);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount > 0)
            this.description = String.format(DESCRIPTIONS[1], 50 - this.amount, 50 - this.amount);
        else
            this.description = String.format(DESCRIPTIONS[0], this.amount - 50, this.amount - 50);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
//        if (type == DamageInfo.DamageType.NORMAL) {
            if (50 - this.amount > 0)
                return damage * ((100 - (50 - this.amount)) * 0.01f);
            else
                return damage * ((100 + (this.amount - 50))) * 0.01f;
//        }
//        return damage;

    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
//        if (damageType == DamageInfo.DamageType.NORMAL) {
            if (50 - this.amount > 0)
                return damage * ((100 + (50 - this.amount)) * 0.01f);
            else
                return damage * ((100 - (this.amount - 50)) * 0.01f);
//        }
//        return damage;
    }
}
