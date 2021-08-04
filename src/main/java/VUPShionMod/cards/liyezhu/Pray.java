package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractLiyezhuCard;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Pray extends AbstractLiyezhuCard implements BranchingUpgradesCard {
    public static final String ID = VUPShionMod.makeID("Pray");
    public static final String IMG = VUPShionMod.assetPath("img/cards/liyezhu/lyz09.png"); //TODO lyz07.png
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Pray() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 3;
        this.baseSecondaryM = this.secondaryM = 1;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (this.isBranchUpgrade()) {
                this.name = cardStrings.EXTENDED_DESCRIPTION[0];
                this.initializeTitle();
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                this.upgradeMagicNumber(-1);
                this.upgradeBaseCost(0);
                this.exhaust = false;
            } else {
                this.upgradeMagicNumber(1);
                this.upgradeSecondM(1);
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            }
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BadgeOfThePaleBlueCrossPower(p, 1)));
        addToBot(new DrawCardAction(this.baseMagicNumber, new AbstractGameAction() {
            @Override
            public void update() {
                int ctr = 0;
                for (AbstractCard card : DrawCardAction.drawnCards) {
                    card.setCostForTurn(0);
                    ctr++;
                    if (ctr >= Pray.this.baseSecondaryM) {
                        break;
                    }
                }
                if (Pray.this.upgraded && Pray.this.getUpgradeType() == UpgradeType.BRANCH_UPGRADE) {
                    ctr = 0;
                    for (AbstractCard card : DrawCardAction.drawnCards) {
                        if (card.color == CardColor.COLORLESS) {
                            ctr++;
                        }
                    }
                    addToBot(new DrawCardAction(ctr));
                }
                isDone = true;
            }
        }));
    }
}
