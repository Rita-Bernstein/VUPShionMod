package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractKuroisuCard;
import VUPShionMod.powers.ReleaseFormKuroisuPower;
import VUPShionMod.powers.UpgradedReleaseFormKuroisuPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReleaseFormKuroisu extends AbstractKuroisuCard {
    public static final String ID = VUPShionMod.makeID("ReleaseFormKuroisu");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu09.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ReleaseFormKuroisu() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            addToBot(new ApplyPowerAction(p, p, new UpgradedReleaseFormKuroisuPower(p, this.secondaryM)));
        else
            addToBot(new ApplyPowerAction(p, p, new ReleaseFormKuroisuPower(p, this.magicNumber)));
    }

    public AbstractCard makeCopy() {
        return new ReleaseFormKuroisu();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
