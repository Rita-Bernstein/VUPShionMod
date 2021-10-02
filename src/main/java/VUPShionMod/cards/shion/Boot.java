package VUPShionMod.cards.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.MakeLoadedCardAction;
import VUPShionMod.cards.AbstractShionCard;
import VUPShionMod.cards.tempCards.QuickAttack;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Boot extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("Boot");
    public static final String IMG = VUPShionMod.assetPath("img/cards/shion/zy20.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public Boot() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard card : p.hand.group) {
            AbstractCard t = card.makeSameInstanceOf();
            addToBot(new MakeLoadedCardAction(t,this.magicNumber));
//            addToBot(new MakeTempCardInDrawPileAction(t, this.magicNumber, true, true, false));
        }

        addToBot(new PressEndTurnButtonAction());
    }
}
