package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Liyezhu.ProphecyOfDestructionPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ProphecyOfDestruction extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(ProphecyOfDestruction.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 2;

    public ProphecyOfDestruction() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 15;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new ProphecyOfDestructionPower(p,this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(5);
        }
    }
}