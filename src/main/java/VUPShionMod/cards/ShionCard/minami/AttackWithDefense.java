package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.MakeLoadedCardAction;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import VUPShionMod.cards.ShionCard.tempCards.QuickAttack;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AttackWithDefense extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID(AttackWithDefense.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami02.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public AttackWithDefense() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.baseBlock = 12;
        this.cardsToPreview = new QuickAttack();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeLoadedCardAction(new QuickAttack(), this.magicNumber));
//        addToBot(new MakeTempCardInDrawPileAction(new QuickAttack(), this.magicNumber, true, true, false));
        addToBot(new GainBlockAction(p, this.block));
    }

    public AbstractCard makeCopy() {
        return new AttackWithDefense();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeBlock(6);
        }
    }
}
