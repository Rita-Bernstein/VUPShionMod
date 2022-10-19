package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Wangchuan.IntensaPower;
import VUPShionMod.powers.Wangchuan.PoisePower;
import VUPShionMod.powers.Wangchuan.StiffnessEndIncreasePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Poise extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(Poise.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc28.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 3;

    public Poise() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new PoisePower(p,this.magicNumber)));
        addToBot(new ApplyPowerAction(p,p,new StiffnessEndIncreasePower(p,this.secondaryM)));

        if(this.upgraded)
            addToBot(new ApplyPowerAction(p,p,new IntensaPower(p,1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(2);
            upgradeSecondM(1);
        }
    }
}
