package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.AcceleratorPower;
import VUPShionMod.powers.MagiamObruorPower;
import VUPShionMod.powers.OculusMortis2Power;
import VUPShionMod.powers.OculusMortisPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OculusMortis extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("OculusMortis");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc57.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public OculusMortis() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded)
            addToBot(new ApplyPowerAction(p, p, new OculusMortis2Power(p, this.magicNumber)));
        else
            addToBot(new ApplyPowerAction(p, p, new OculusMortisPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new MagiamObruorPower(p, 1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
