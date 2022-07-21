package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.cards.ShionCard.AbstractShionLiyezhuCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class StrengthPray extends AbstractShionLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(StrengthPray.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/liyezhu/lyz06.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public StrengthPray() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 4;
        this.secondaryM= this.baseSecondaryM = 1;
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHyperdimensionalLinksAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.secondaryM)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(2);
            upgradeSecondM(1);
        }
    }
}
