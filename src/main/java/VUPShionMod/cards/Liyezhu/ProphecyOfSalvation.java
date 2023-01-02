package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.AddSansAction;
import VUPShionMod.powers.Liyezhu.ProphecyOfSalvation2Power;
import VUPShionMod.powers.Liyezhu.ProphecyOfSalvationPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ProphecyOfSalvation extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(ProphecyOfSalvation.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/ProphecyOfSalvation.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public ProphecyOfSalvation() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AddSansAction(this.secondaryM));
        addToBot(new ApplyPowerAction(p, p, new ProphecyOfSalvationPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new ProphecyOfSalvation2Power(p, 1)));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(2);
            upgradeBaseCost(0);
        }
    }
}