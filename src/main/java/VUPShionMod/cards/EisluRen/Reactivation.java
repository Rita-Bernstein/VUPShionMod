package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.GainRefundChargeAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

public class Reactivation extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(Reactivation.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/Reactivation.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 3;

    public Reactivation() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 7;
        this.secondaryM = this.baseSecondaryM= 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int charge = 3;
        if(p.hasPower(DexterityPower.POWER_ID)){
            charge += p.getPower(DexterityPower.POWER_ID).amount/5;
        }

        addToBot(new GainRefundChargeAction(charge));
        addToBot(new GainEnergyAction(4));
        addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,this.secondaryM)));
        addToBot(new ApplyPowerAction(p,p,new LoseDexterityPower(p,this.secondaryM)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(2);
        }
    }
}
