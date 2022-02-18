package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.CorGladiiPower;
import VUPShionMod.powers.StiffnessPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;

public class Reflect extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("Reflect");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc17.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 2;

    public Reflect() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM  = this.baseSecondaryM = 10;
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));


        int d = 0;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            d = AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        this.baseDamage = d;
        calculateCardDamage(m);

        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.1F));
        } else {
            addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
        }

        addToBot(new ApplyPowerAction(p, p, new FlameBarrierPower(p, this.secondaryM)));
        this.rawDescription = DESCRIPTION;
        initializeDescription();

    }

    public void applyPowers() {
        int d = 0;
        int b = this.magicNumber;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID)) {
            d = AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
            b += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        }

        this.baseDamage = d;
        this.baseBlock = b;


        super.applyPowers();

        this.rawDescription = DESCRIPTION;
        this.rawDescription += UPGRADE_DESCRIPTION;
        initializeDescription();
    }


    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }


    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = DESCRIPTION;
        this.rawDescription += UPGRADE_DESCRIPTION;
        initializeDescription();
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(1);
        }
    }
}
