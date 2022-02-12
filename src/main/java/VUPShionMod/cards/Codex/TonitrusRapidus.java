package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TonitrusRapidus extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID("TonitrusRapidus");
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/lei.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public TonitrusRapidus(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 1;
        this.timesUpgraded = upgrades;
        this.exhaust = true;
    }

    public TonitrusRapidus() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                addToBot(new GainEnergyAction(this.magicNumber));
                break;
            case 1:
                addToBot(new GainEnergyAction(this.magicNumber));
                addToBot(new DrawCardAction(p, this.secondaryM));
                break;
            case 2:
                addToBot(new GainEnergyAction(this.magicNumber));
                addToBot(new DrawCardAction(p, this.secondaryM));
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
                this.exhaust = false;
                upgradeMagicNumber(-1);
            }
        }
    }
}
