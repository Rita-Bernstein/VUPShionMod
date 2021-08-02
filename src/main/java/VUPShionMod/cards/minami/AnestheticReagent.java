package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TriggerDimensionSplitterAction;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.BadgeOfTimePower;
import VUPShionMod.powers.SupportArmamentPower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class AnestheticReagent extends AbstractMinamiCard implements BranchingUpgradesCard {
    public static final String ID = VUPShionMod.makeID("AnestheticReagent");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami10.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AnestheticReagent() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 1;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        if (!isBranchUpgrade()) {
            addToBot(new LoseHPAction(p, p, 4));
            addToBot(new ApplyPowerAction(p, p, new SupportArmamentPower(p, this.magicNumber)));
            addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.secondaryM)));
        } else
            addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.secondaryM)));
    }

    public AbstractCard makeCopy() {
        return new AnestheticReagent();
    }


    private void baseUpgrade() {
        upgradeBaseCost(1);
        upgradeMagicNumber(1);
        upgradeSecondM(1);
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
