package VUPShionMod.cards.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.MakeLoadedCardAction;
import VUPShionMod.cards.AbstractShionCard;
import VUPShionMod.cards.tempCards.QuickAttack;
import VUPShionMod.cards.tempCards.QuickScreen;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AttackSystemPreload extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("AttackSystemPreload");
    public static final String IMG = VUPShionMod.assetPath("img/cards/shion/zy05.png");
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
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.cardsToPreview.upgrade();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeLoadedCardAction(new QuickAttack(),this.magicNumber));
//        addToBot(new MakeTempCardInDrawPileAction(new QuickAttack(), this.magicNumber, true, true, false));
    }
}
