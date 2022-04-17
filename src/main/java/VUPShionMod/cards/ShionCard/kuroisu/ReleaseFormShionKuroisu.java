package VUPShionMod.cards.ShionCard.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionKuroisuCard;
import VUPShionMod.cards.ShionCard.tempCards.QuickScreen;
import VUPShionMod.powers.ReleaseFormKuroisuPower;
import VUPShionMod.powers.UpgradedReleaseFormKuroisuPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReleaseFormShionKuroisu extends AbstractShionKuroisuCard {
    public static final String ID = VUPShionMod.makeID("ReleaseFormKuroisu");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/kuroisu/kuroisu09.png");
    private static final int COST = 3;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ReleaseFormShionKuroisu() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.cardsToPreview = new QuickScreen();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        if (upgraded)
            addToBot(new ApplyPowerAction(p, p, new UpgradedReleaseFormKuroisuPower(p, this.magicNumber), 0));
        else
            addToBot(new ApplyPowerAction(p, p, new ReleaseFormKuroisuPower(p, this.magicNumber), 0));


    }

    public AbstractCard makeCopy() {
        return new ReleaseFormShionKuroisu();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.cardsToPreview.upgrade();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.cardsToPreview.upgrade();
            initializeDescription();
            this.upgradeMagicNumber(1);
        }
    }
}