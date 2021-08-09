package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TriggerDimensionSplitterAction;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LockIndication extends AbstractMinamiCard implements BranchingUpgradesCard {
    public static final String ID = VUPShionMod.makeID("LockIndication");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami03.png");
    private static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public LockIndication() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = MathUtils.random(2);
        switch (count){
            case 0:
                addToBot(new SFXAction("SHION_10"));
                break;
            case 1:
                addToBot(new SFXAction("SHION_11"));
                break;
        }

        addToBot(new ApplyPowerAction(p, p, new HyperdimensionalLinksPower(p, this.magicNumber)));
        if (this.upgraded && getUpgradeType() == UpgradeType.BRANCH_UPGRADE)
            addToBot(new TriggerDimensionSplitterAction(this.secondaryM,true));
        else
            addToBot(new TriggerDimensionSplitterAction(this.secondaryM));
    }

    public AbstractCard makeCopy() {
        return new LockIndication();
    }


    private void baseUpgrade() {
        upgradeMagicNumber(1);
        upgradeSecondM(5);
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    private void branchUpgrade() {
        upgradeMagicNumber(1);
        upgradeBaseCost(0);
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
