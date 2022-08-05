package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.AddRefundChargeAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Reactivation extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(Reactivation.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ReleaseFormEisluRen.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 3;

    public Reactivation() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AddRefundChargeAction(this.magicNumber));
        addToBot(new GainEnergyAction(2));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(2);
        }
    }
}
