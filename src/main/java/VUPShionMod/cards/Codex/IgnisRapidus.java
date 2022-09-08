package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Common.FreeCardPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IgnisRapidus extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID(IgnisRapidus.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/huo.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public IgnisRapidus(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.timesUpgraded = upgrades;
        this.exhaust = true;
        this.parentCardID = IgnisNimius.ID;
    }

    public IgnisRapidus() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FreeCardPower(p, this.magicNumber)));
        if(this.timesUpgraded >=2){
            addToBot(new DrawCardAction(1));
        }
    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                upgradeMagicNumber(1);
            }

            if (this.timesUpgraded == 2) {
                upgradeMagicNumber(-1);
                upgradeBaseCost(0);
                this.exhaust = false;
            }
        }
    }
}
