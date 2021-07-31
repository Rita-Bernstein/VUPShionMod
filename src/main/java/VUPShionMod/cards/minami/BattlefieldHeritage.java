package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.RandomDiscardPileToHandAction;
import VUPShionMod.actions.TriggerDimensionSplitterAction;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.BadgeOfTimePower;
import VUPShionMod.powers.SupportArmamentPower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BattlefieldHeritage extends AbstractVUPShionCard implements BranchingUpgradesCard {
    public static final String ID = VUPShionMod.makeID("BattlefieldHeritage");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami10.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardColor COLOR = CardColorEnum.VUP_Shion_LIME;

    private static final int COST = 2;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public BattlefieldHeritage() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SupportArmamentPower(p, this.magicNumber)));

        if (!isBranchUpgrade())
            addToBot(new RandomDiscardPileToHandAction(this.secondaryM));
        else
            addToBot(new BetterDiscardPileToHandAction(1));
    }

    public AbstractCard makeCopy() {
        return new BattlefieldHeritage();
    }


    private void baseUpgrade() {
        upgradeBaseCost(1);
        upgradeMagicNumber(1);
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    private void branchUpgrade() {
        upgradeBaseCost(1);
        this.name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (isBranchUpgrade()) {
                branchUpgrade();
            } else {
                baseUpgrade();
            }
        }
    }
}
