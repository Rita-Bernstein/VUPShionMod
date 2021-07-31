package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class StrengthPray extends AbstractVUPShionCard implements BranchingUpgradesCard {
    public static final String ID = VUPShionMod.makeID("StrengthPray");
    public static final String IMG = VUPShionMod.assetPath("img/cards/liyezhu/lyz09.png"); //TODO lyz06.png
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardColor COLOR = CardColorEnum.VUP_Shion_LIME;

    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public StrengthPray() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 4;
        this.baseSecondaryM = this.secondaryM = 1;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (this.isBranchUpgrade()) {
                this.type = CardType.POWER;
                this.upgradeSecondM(2);
                this.name = cardStrings.EXTENDED_DESCRIPTION[0];
                this.initializeTitle();
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                this.initializeDescription();
            } else {
                this.upgradeBaseCost(0);
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                this.initializeDescription();
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.baseMagicNumber)));
        if (!this.isBranchUpgrade()) {
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.baseMagicNumber)));
        }
        addToBot(new ApplyPowerAction(p, p, new BadgeOfThePaleBlueCrossPower(p, this.baseSecondaryM)));
    }
}
