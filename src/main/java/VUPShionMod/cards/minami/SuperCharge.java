package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class SuperCharge extends AbstractMinamiCard {
    public static final String ID = VUPShionMod.makeID("SuperCharge");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami08.png");
    private static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SuperCharge() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReducePowerAction(p, p, HyperdimensionalLinksPower.POWER_ID, 1));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.secondaryM), this.secondaryM));
        addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, this.secondaryM), this.secondaryM));
    }

    public AbstractCard makeCopy() {
        return new SuperCharge();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        AbstractPower power = p.getPower(HyperdimensionalLinksPower.POWER_ID);
        if (power == null) return false;
        if (power.amount < secondaryM) return false;
        else
            return super.canUse(p, m);

    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            upgradeSecondM(1);
        }
    }
}
