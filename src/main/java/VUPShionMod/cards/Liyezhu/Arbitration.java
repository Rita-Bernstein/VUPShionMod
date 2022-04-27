package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Liyezhu.Arbitration2Power;
import VUPShionMod.powers.Liyezhu.ArbitrationPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Arbitration extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(Arbitration.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 2;

    public Arbitration() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded)
            addToBot(new ApplyPowerAction(p, p, new Arbitration2Power(p, this.magicNumber)));
        else
            addToBot(new ApplyPowerAction(p, p, new ArbitrationPower(p, this.magicNumber)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.name = EXTENDED_DESCRIPTION[0];
            initializeTitle();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}