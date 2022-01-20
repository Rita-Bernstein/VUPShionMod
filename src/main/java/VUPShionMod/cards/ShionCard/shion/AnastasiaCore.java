package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.powers.AnastasiaCorePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AnastasiaCore extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("AnastasiaCore");
    public static final String IMG = VUPShionMod.assetPath("img/cards/shion/zy16.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public AnastasiaCore() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgraded = true;
            ++this.timesUpgraded;
            this.name = cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeTitle();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("SHION_16"));
        addToBot(new ApplyPowerAction(p, p, new AnastasiaCorePower(p, 1)));
    }
}
