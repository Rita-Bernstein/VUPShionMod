package VUPShionMod.powers.Shion;


import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.LoseMaxHPAction;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class StructureDissectionPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("StructureDissectionPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public StructureDissectionPower(AbstractCreature owner,int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/StructureDissectionPower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/StructureDissectionPower36.png")), 0, 0, 36, 36);
        updateDescription();
        this.type = PowerType.DEBUFF;
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        this.description += DESCRIPTIONS[1];
        this.description += DESCRIPTIONS[2];
        this.description += DESCRIPTIONS[3];
        this.description += DESCRIPTIONS[4];
        this.description += DESCRIPTIONS[5];
        this.description += DESCRIPTIONS[6];
        this.description += DESCRIPTIONS[7];
        this.description += DESCRIPTIONS[8];
        this.description += DESCRIPTIONS[9];
        this.description += DESCRIPTIONS[10];
        this.description += DESCRIPTIONS[11];


        this.description = String.format(this.description,
                3,
                7,
                12,
                18,
                25,
                33,
                42,
                52,
                64,
                78,
                94
        );
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        trigger();
    }

    @Override
    public void stackPower(int stackAmount) {
        int previous = this.amount;
        super.stackPower(stackAmount);
        trigger(previous);
    }

    public void trigger() {
        trigger(0);
    }

    public void trigger(int previous) {
        if (previous < 3 && this.amount >= 3)
            addToTop(new ApplyPowerAction(this.owner, this.owner, new VulnerablePower(this.owner, 5, false)));

        if (previous < 7 && this.amount >= 7)
            addToTop(new ApplyPowerAction(this.owner, this.owner, new WeakPower(this.owner, 5, false)));

        if (previous < 12 && this.amount >= 12) {
            addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -9)));
            addToTop(new ApplyPowerAction(this.owner, this.owner, new GainStrengthPower(this.owner, 9)));
        }

        if (previous < 18 && this.amount >= 18)
            addToTop(new ApplyPowerAction(this.owner, this.owner, new SlowPower(this.owner, 0)));

        if (previous < 25 && this.amount >= 25)
            addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -1)));

        if (previous < 33 && this.amount >= 33)
            addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -2)));

        if (previous < 42 && this.amount >= 42)
            addToTop(new DamageAction(this.owner, new DamageInfo(null, this.owner.maxHealth / 10, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));

        if (previous < 52 && this.amount >= 52)
            addToTop(new LoseMaxHPAction(this.owner, this.owner, this.owner.maxHealth / 20));

        if (previous < 64 && this.amount >= 64)
            addToTop(new LoseMaxHPAction(this.owner, this.owner, this.owner.maxHealth / 20));


        if (previous < 78 && this.amount >= 78)
            addToTop(new ApplyPowerAction(this.owner, this.owner, new VulnerablePower(this.owner, 99, false)));

        if (previous < 94 && this.amount >= 94) {
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            addToTop(new StunMonsterAction((AbstractMonster) this.owner, this.owner, 1));
        }

    }

}
