package VUPShionMod.cards.tempCards;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class QuickRepair extends CustomCard {
    public static final String ID = VUPShionMod.makeID("QuickRepair");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "img/cards/shion/zy10.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = -2;

    public QuickRepair() {
        super(ID, NAME, VUPShionMod.assetPath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);
        this.baseMagicNumber = 6;
        this.tags.add(CardTags.HEALING);
        this.tags.add(CardTagsEnum.LOADED);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, p, this.magicNumber));
    }

    @Override
    public void applyPowers() {
        int realMagicNumber = this.baseMagicNumber;
        this.baseMagicNumber += VUPShionMod.calculateTotalFinFunnelLevel();
        this.magicNumber = this.baseMagicNumber;
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
        this.baseMagicNumber = realMagicNumber;
        this.isMagicNumberModified = this.magicNumber != this.baseMagicNumber;
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
