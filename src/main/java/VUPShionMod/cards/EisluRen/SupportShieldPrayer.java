package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.AddWingShieldDamageReduceCombatAction;
import VUPShionMod.actions.EisluRen.GainRefundChargeAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SupportShieldPrayer extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(SupportShieldPrayer.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ReleaseFormEisluRen.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public SupportShieldPrayer() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 12;
        this.magicNumber = this.baseMagicNumber = 5;
        this.secondaryM = this.baseSecondaryM = 3;
        this.exhaust =true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new GainRefundChargeAction(this.magicNumber));
        addToBot(new AddWingShieldDamageReduceCombatAction(this.secondaryM));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}
