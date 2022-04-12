package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.DistantiaAbsensquePower;
import VUPShionMod.powers.MagiamObruorPower;
import VUPShionMod.powers.StiffnessPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class DistantiaAbsensque extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("DistantiaAbsensque");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc36.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public DistantiaAbsensque() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DistantiaAbsensquePower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new MagiamObruorPower(p, this.secondaryM)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeMagicNumber(1);
        }
    }
}
