package VUPShionMod.cards.ShionCard.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionAnastasiaCard;
import VUPShionMod.powers.Shion.AttackOrderBetaPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AttackOrderBeta extends AbstractShionAnastasiaCard {
    public static final String ID = VUPShionMod.makeID(AttackOrderBeta.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/anastasia/anastasia02.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AttackOrderBeta() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AttackOrderBetaPower(p)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
