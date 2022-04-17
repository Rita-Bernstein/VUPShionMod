package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InordinateAdmonition extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(InordinateAdmonition.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 2;

    public InordinateAdmonition() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 3;
        this.isMultiDamage = true;
        this.secondaryM = this.baseSecondaryM = 2;
        this.exhaust = true;
        this.cardsToPreview = new ChasteReflection();
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
            upgradeDamage(1);
        }
    }
}