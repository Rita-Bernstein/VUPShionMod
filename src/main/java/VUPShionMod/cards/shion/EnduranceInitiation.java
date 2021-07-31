package VUPShionMod.cards.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EnduranceInitiationAction;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnduranceInitiation extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("EnduranceInitiation");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "img/cards/shion/zy13.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public EnduranceInitiation() {
        super(ID, NAME, VUPShionMod.assetPath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColorEnum.VUP_Shion_LIME, RARITY, TARGET);
        this.baseMagicNumber = 2;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EnduranceInitiationAction(this.baseMagicNumber, this.upgraded));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
