package VUPShionMod.cards.Codex;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.WangChuan.AbstractWCCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChaosRapidus extends AbstractCodexCard {
    public static final String ID = VUPShionMod.makeID("ChaosRapidus");
    public static final String IMG = VUPShionMod.assetPath("img/cards/codex/hundun.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public ChaosRapidus(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.timesUpgraded = upgrades;
    }

    public ChaosRapidus() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, this.magicNumber));
        if (this.timesUpgraded < 2) {
            addToBot(new ExhaustAction(1, false));
            addToBot(new DiscardAction(p, p, 1, false));
        } else
            addToBot(new ExhaustAction(2, false, true, true));

    }


    @Override
    public void upgrade() {
        super.upgrade();
        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                upgradeMagicNumber(1);
            }

//            if (this.timesUpgraded == 2)
//                upgradeMagicNumber(-2);
        }
    }
}
