package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChaosNimius extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID(ChaosNimius.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/hundun.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public ChaosNimius(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.timesUpgraded = upgrades;
    }

    public ChaosNimius() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                addToBot(new DrawCardAction(p, this.magicNumber));
                addToBot(new ExhaustAction(1, false));
                addToBot(new DiscardAction(p, p, 1, false));
                break;
            case 1:
                addToBot(new ExpertiseAction(p, BaseMod.MAX_HAND_SIZE));
                addToBot(new ExhaustAction(BaseMod.MAX_HAND_SIZE, false, true, true));
                break;
            case 2:
                addToBot(new DrawCardAction(p, this.magicNumber));
                addToBot(new ExhaustAction(2, false, true, true));
                break;
        }
    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                upgradeBaseCost(2);
                this.exhaust = true;
                this.isEthereal = true;
            }

            if (this.timesUpgraded == 2) {
                this.exhaust = false;
                this.isEthereal = false;
                upgradeMagicNumber(1);
                upgradeBaseCost(1);
            }

        }
    }
}
