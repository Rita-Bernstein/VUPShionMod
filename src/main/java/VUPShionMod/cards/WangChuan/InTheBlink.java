package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.CorGladiiPower;
import VUPShionMod.powers.StiffnessPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InTheBlink extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("InTheBlink");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc05.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public InTheBlink(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.timesUpgraded = upgrades;
        this.magicNumber = this.baseMagicNumber = 3;
        this.baseSecondaryM = this.secondaryM = 1;
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[9] + EXTENDED_DESCRIPTION[12] + EXTENDED_DESCRIPTION[13];
        initializeDescription();
    }

    public InTheBlink() {
        this(0);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            case 0:

        }
    }






    @Override
    public boolean canUpgrade() {
        return this.timesUpgraded <= 8;
    }

    @Override
    public void upgrade() {
        if (this.timesUpgraded <= 8) {
            switch (this.timesUpgraded) {
                case 0:
                    this.target = CardTarget.ALL_ENEMY;
                    this.isMultiDamage = true;
                    upgradeMagicNumber(1);
                    upgradeBaseCost(2);
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[10] + EXTENDED_DESCRIPTION[12] + EXTENDED_DESCRIPTION[15];
                    break;
                case 1:
                    upgradeMagicNumber(4);
                    this.rarity = CardRarity.UNCOMMON;
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[10] + EXTENDED_DESCRIPTION[15];
                    break;
                case 2:
                    upgradeMagicNumber(2);
                    this.rarity = CardRarity.RARE;
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[10] + EXTENDED_DESCRIPTION[16] + EXTENDED_DESCRIPTION[14];
                    break;
                case 3:
                    upgradeMagicNumber(1);
                    upgradeSecondM(1);
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[11] + EXTENDED_DESCRIPTION[16] + EXTENDED_DESCRIPTION[14];
                    break;
                case 4:
                    upgradeMagicNumber(1);
                    upgradeSecondM(1);
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[11] + EXTENDED_DESCRIPTION[16] + EXTENDED_DESCRIPTION[14];
                    break;
                case 5:
                    upgradeMagicNumber(2);
                    upgradeSecondM(1);
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[11] + EXTENDED_DESCRIPTION[16] + EXTENDED_DESCRIPTION[13];
                    break;
                case 6:
                    upgradeMagicNumber(2);
                    upgradeSecondM(1);
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[11] + EXTENDED_DESCRIPTION[16] + EXTENDED_DESCRIPTION[13];
                    break;
                case 7:
                    upgradeMagicNumber(2);
                    upgradeSecondM(1);
                    this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[11] + EXTENDED_DESCRIPTION[16];
                    break;
                case 8:
                    upgradeMagicNumber(2);
                    upgradeSecondM(1);
                    this.rawDescription = UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[11] + EXTENDED_DESCRIPTION[17] + EXTENDED_DESCRIPTION[18];
                    break;

            }


            this.name = EXTENDED_DESCRIPTION[this.timesUpgraded];
            this.upgraded = true;
            this.timesUpgraded++;
            initializeTitle();
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new InTheBlink(this.timesUpgraded);
    }
}
