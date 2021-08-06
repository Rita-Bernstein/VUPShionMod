package VUPShionMod.cards.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractShionCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Boot extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("Boot");
    public static final String IMG =  VUPShionMod.assetPath("img/cards/shion/zy20.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 3;

    public Boot() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseMagicNumber = 2;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard card : p.hand.group) {
            if (card.cardID.equals(this.cardID)) continue;
            AbstractCard t = card.makeSameInstanceOf();
            if (!t.exhaust) {
                t.rawDescription = t.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
                t.initializeDescription();
            }
            t.exhaustOnUseOnce = true;
            addToBot(new MakeTempCardInDrawPileAction(t, this.baseMagicNumber, true, true, false));
        }
        AbstractDungeon.getCurrRoom().skipMonsterTurn = false;
        addToBot(new PressEndTurnButtonAction());
    }
}
