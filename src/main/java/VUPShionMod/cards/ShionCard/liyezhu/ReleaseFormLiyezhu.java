package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.cards.ShionCard.AbstractShionLiyezhuCard;
import VUPShionMod.powers.Shion.ReleaseFormLiyezhuBPower;
import VUPShionMod.powers.Shion.ReleaseFormLiyezhuCPower;
import VUPShionMod.powers.Shion.ReleaseFormLiyezhuPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReleaseFormLiyezhu extends AbstractShionLiyezhuCard {
    public static final String ID = VUPShionMod.makeID("ReleaseFormLiyezhu");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/liyezhu/lyz09.png");
    private static final int COST = 3;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ReleaseFormLiyezhu() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHyperdimensionalLinksAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new ReleaseFormLiyezhuPower(p, this.magicNumber), 0));
        addToBot(new ApplyPowerAction(p, p, new ReleaseFormLiyezhuCPower(p, this.secondaryM), 0));
        addToBot(new ApplyPowerAction(p, p, new ReleaseFormLiyezhuBPower(p, 1), 0));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSecondM(9);
        }
    }


}
