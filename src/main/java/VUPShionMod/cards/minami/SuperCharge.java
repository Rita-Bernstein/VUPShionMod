package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.powers.SupportArmamentPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class SuperCharge extends AbstractMinamiCard {
    public static final String ID = VUPShionMod.makeID("SuperCharge");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami08.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SuperCharge() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new SupportArmamentPower(p,this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.secondaryM), this.secondaryM));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.secondaryM), this.secondaryM));
    }

    public AbstractCard makeCopy() {
        return new SuperCharge();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeSecondM(2);
        }
    }
}
