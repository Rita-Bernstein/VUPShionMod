package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Codex.TurnDexPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class VentusRapidus extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID("VentusRapidus");
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/feng.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public VentusRapidus(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 1;
        this.timesUpgraded = upgrades;
    }

    public VentusRapidus() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber)));
                break;
            case 1:
                addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber)));
                break;
            case 2:
                addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber)));
                addToBot(new ApplyPowerAction(p, p, new TurnDexPower(p, this.secondaryM)));
                break;
        }

    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                upgradeBaseCost(0);
            }

            if (this.timesUpgraded == 2) {
                upgradeMagicNumber(-1);
            }
        }
    }
}
