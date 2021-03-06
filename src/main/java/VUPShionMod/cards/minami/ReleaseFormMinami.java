package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.powers.RailgunPower;
import VUPShionMod.powers.ReleaseFormMinamiPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReleaseFormMinami extends AbstractMinamiCard {
    public static final String ID = VUPShionMod.makeID("ReleaseFormMinami");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami09.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ReleaseFormMinami() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 20;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ReleaseFormMinamiPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new RailgunPower(p, this.secondaryM)));

    }

    public AbstractCard makeCopy() {
        return new ReleaseFormMinami();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeSecondM(10);
        }
    }
}
