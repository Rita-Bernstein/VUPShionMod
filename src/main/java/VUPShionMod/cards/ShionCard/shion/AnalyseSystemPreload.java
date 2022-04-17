package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.MakeLoadedCardAction;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.cards.ShionCard.tempCards.QuickScreen;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AnalyseSystemPreload extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("AnalyseSystemPreload");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy07.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public AnalyseSystemPreload() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 3;
        this.cardsToPreview = new QuickScreen();
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
        addToBot(new MakeLoadedCardAction(this.upgraded, new QuickScreen(), this.magicNumber));
//        addToBot(new MakeTempCardInDrawPileAction(new QuickScreen(), this.magicNumber, true, true, false));
    }
}
