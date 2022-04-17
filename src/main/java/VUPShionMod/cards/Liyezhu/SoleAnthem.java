package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SoleAnthem extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(SoleAnthem.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 2;

    public SoleAnthem() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 2;
        this.exhaust = true;
        this.cardsToPreview = new FlayTheEvil();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

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
}