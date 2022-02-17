package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.ThreePowerPower;
import VUPShionMod.powers.TwoPowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HomoNimius extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID("HomoNimius");
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/ren.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public HomoNimius(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.timesUpgraded = upgrades;
        this.exhaust = true;
    }

    public HomoNimius() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                addToBot(new ApplyPowerAction(p, p, new TwoPowerPower(p, 1)));
                break;
            case 1:
                addToBot(new ApplyPowerAction(p, p, new ThreePowerPower(p, 1)));
                break;
            case 2:
                addToBot(new ApplyPowerAction(p, p, new ThreePowerPower(p, 1)));
                break;
        }

    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1)
                upgradeMagicNumber(1);

            if (this.timesUpgraded == 2) {
                this.isInnate = true;
                upgradeBaseCost(0);
            }
        }
    }
}