package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.LoseHyperdimensionalLinksAction;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class SuperCharge extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID("SuperCharge");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami08.png");
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
        addToBot(new LoseHyperdimensionalLinksAction(this.secondaryM));
//        addToBot(new ReducePowerAction(p, p, HyperdimensionalLinksPower.POWER_ID, this.secondaryM));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new SuperCharge();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        AbstractPower power = p.getPower(HyperdimensionalLinksPower.POWER_ID);
        if (power == null) return false;
        if (power.amount < this.secondaryM) return false;
        else
            return super.canUse(p, m);

    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeSecondM(-1);
        }
    }
}
