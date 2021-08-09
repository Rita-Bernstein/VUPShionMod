package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractKuroisuCard;
import VUPShionMod.powers.BadgeOfTimePower;
import VUPShionMod.powers.TimeBombPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TimeBomb extends AbstractKuroisuCard {
    public static final String ID = VUPShionMod.makeID("TimeBomb");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu04.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TimeBomb() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 20;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BadgeOfTimePower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new TimeBombPower(p, this.secondaryM)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeSecondM(10);
        }
    }
}
