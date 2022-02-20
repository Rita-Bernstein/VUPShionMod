package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.FreeCardPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class IgnisNimius extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID("IgnisNimius");
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/huo.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public IgnisNimius(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.timesUpgraded = upgrades;
        this.exhaust = true;
    }

    public IgnisNimius() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FreeCardPower(p, this.magicNumber)));
    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                upgradeMagicNumber(3);
                upgradeBaseCost(2);
                this.isEthereal = true;
            }

            if (this.timesUpgraded == 2) {
                upgradeBaseCost(0);
                upgradeMagicNumber(-1);
                this.isEthereal = false;
            }
        }
    }
}
