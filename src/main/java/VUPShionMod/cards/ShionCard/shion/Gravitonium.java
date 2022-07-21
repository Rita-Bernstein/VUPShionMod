package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.powers.Shion.GravitoniumPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Gravitonium extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID(Gravitonium.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy24.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 2;

    public Gravitonium() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new GravitoniumPower(p)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.isInnate = true;
        }
    }
}
