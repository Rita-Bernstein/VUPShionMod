package VUPShionMod.powers.Wangchuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PetalsFallPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(PetalsFallPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public PetalsFallPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/PetalsFallPower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/PetalsFallPower36.png")), 0, 0, 36, 36);
    }


    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount, this.amount);
    }

    @Override
    public void onNumSpecificTrigger(int amount) {
        flash();
        addToBot(new GainBlockAction(this.owner, this.amount * amount));
        addToBot(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(this.amount * amount, true),
                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE, true));
    }
}