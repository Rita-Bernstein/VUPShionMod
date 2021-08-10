package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractKuroisuCard;
import VUPShionMod.cards.tempCards.QuickDefend;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TimeSlack extends AbstractKuroisuCard {
    public static final String ID = VUPShionMod.makeID("TimeSlack");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu02.png");
    private static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TimeSlack() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 4;
        this.magicNumber = this.baseMagicNumber = 1;
        this.cardsToPreview = new QuickDefend();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new MakeTempCardInDiscardAction(new QuickDefend(),this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
        }
    }
}
