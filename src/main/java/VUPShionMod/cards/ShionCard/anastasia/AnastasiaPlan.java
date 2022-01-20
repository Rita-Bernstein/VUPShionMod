package VUPShionMod.cards.ShionCard.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractAnastasiaCard;
import VUPShionMod.powers.DoubleCardPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AnastasiaPlan extends AbstractAnastasiaCard {
    public static final String ID = VUPShionMod.makeID("AnastasiaPlan");
    public static final String IMG = VUPShionMod.assetPath("img/cards/anastasia/anastasia07.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AnastasiaPlan() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 0;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new DoubleCardPower(p,this.magicNumber)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new AnastasiaPlan();
    }
}
