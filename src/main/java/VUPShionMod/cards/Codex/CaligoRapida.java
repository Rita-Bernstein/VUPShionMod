package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.NextTurnAttackPower;
import VUPShionMod.powers.PreTripleDamagePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.PhantasmalPower;

public class CaligoRapida extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID("CaligoRapida");
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/an.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public CaligoRapida(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 0;
        this.timesUpgraded = upgrades;
        this.exhaust = true;
    }

    public CaligoRapida() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        if (this.timesUpgraded < 2) {
            addToBot(new ApplyPowerAction(p, p, new PhantasmalPower(p, 1), 1));
        } else {
            addToBot(new ApplyPowerAction(p, p, new PreTripleDamagePower(p, 1), 1));
        }

        if (this.timesUpgraded >= 1)
            addToBot(new ApplyPowerAction(p, p, new NextTurnAttackPower(p, this.magicNumber)));
    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                upgradeMagicNumber(1);
            }

            if (this.timesUpgraded == 2) {
                upgradeMagicNumber(1);
                this.exhaust = false;
                this.shuffleBackIntoDrawPile = true;
            }
        }
    }
}
