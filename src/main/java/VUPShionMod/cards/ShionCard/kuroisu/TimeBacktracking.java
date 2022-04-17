package VUPShionMod.cards.ShionCard.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionKuroisuCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class TimeBacktracking extends AbstractShionKuroisuCard {
    public static final String ID = VUPShionMod.makeID("TimeBacktracking");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/kuroisu/kuroisu01.png");
    private static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TimeBacktracking() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p,this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new EnergizedPower(p, this.secondaryM), this.secondaryM));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeSecondM(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
