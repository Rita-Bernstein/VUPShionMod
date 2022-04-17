package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.MakeLoadedCardAction;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.cards.ShionCard.tempCards.QuickAttack;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AttackSystemPreload extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("AttackSystemPreload");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy05.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public AttackSystemPreload() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 2;
        this.cardsToPreview = new QuickAttack();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.cardsToPreview.upgrade();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeLoadedCardAction(upgraded,new QuickAttack(),this.magicNumber));
//        addToBot(new MakeTempCardInDrawPileAction(new QuickAttack(), this.magicNumber, true, true, false));
    }
}
