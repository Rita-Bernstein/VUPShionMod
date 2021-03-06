package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractKuroisuCard;
import VUPShionMod.powers.BadgeOfTimePower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlackHand extends AbstractKuroisuCard implements BranchingUpgradesCard {
    public static final String ID = VUPShionMod.makeID("BlackHand");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu11.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public BlackHand() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
        this.secondaryM = this.baseSecondaryM = 2;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, this.magicNumber));

        if (upgraded)
            addToBot(new ApplyPowerAction(p, p, new BadgeOfTimePower(p, 2)));
        else
            addToBot(new ApplyPowerAction(p, p, new BadgeOfTimePower(p, 1)));

        addToBot(new GainEnergyAction(2));
        addToBot(new DrawCardAction(this.secondaryM, new AbstractGameAction() {
            @Override
            public void update() {
                addToTop(new WaitAction(0.4f));
                tickDuration();
                if (this.isDone) {
                    for (AbstractCard c : DrawCardAction.drawnCards)
                        c.setCostForTurn(0);
                }
            }
        }));
    }

    public AbstractCard makeCopy() {
        return new BlackHand();
    }



    private void baseUpgrade() {
        upgradeSecondM(1);
        this.exhaust = true;
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    private void branchUpgrade() {
        upgradeMagicNumber(1);
        upgradeBaseCost(0);
        this.exhaust = false;
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
