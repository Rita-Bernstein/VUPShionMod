package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import VUPShionMod.powers.ReleaseFormMinamiPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReleaseFormShionMinami extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID("ReleaseFormMinami");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami09.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ReleaseFormShionMinami() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new ApplyPowerAction(p, p, new ReleaseFormMinamiPower(p, this.magicNumber), 0));
    }

    public AbstractCard makeCopy() {
        return new ReleaseFormShionMinami();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.isEthereal = false;
        }
    }
}
