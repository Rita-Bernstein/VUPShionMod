package VUPShionMod.cards.ShionCard.tempCards;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class QuickScreen extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("QuickScreen");
    public static final String IMG = VUPShionMod.assetPath("img/cards/shion/zy10.png");

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public QuickScreen() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 1;
        this.tags.add(CardTagsEnum.LOADED);
        this.exhaust = true;
        this.color = CardColor.COLORLESS;

        setDisplayRarity(CardRarity.UNCOMMON);
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ScryAction(this.magicNumber));
        addToBot(new DrawCardAction(p, this.secondaryM));
    }
}
