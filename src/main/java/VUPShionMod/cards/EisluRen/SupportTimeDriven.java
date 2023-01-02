package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.EisluRen.CoverMinionPower;
import VUPShionMod.powers.EisluRen.SupportTimeDrivenPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SupportTimeDriven extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(SupportTimeDriven.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/SupportTimeDriven.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public SupportTimeDriven() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SupportTimeDrivenPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new CoverMinionPower(p)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
