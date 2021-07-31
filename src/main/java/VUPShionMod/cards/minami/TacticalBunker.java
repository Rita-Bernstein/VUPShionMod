package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.SupportArmamentPower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TacticalBunker extends AbstractVUPShionCard implements BranchingUpgradesCard {
    public static final String ID = VUPShionMod.makeID("TacticalBunker");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami10.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardColor COLOR = CardColorEnum.VUP_Shion_LIME;

    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TacticalBunker() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 4;
        this.baseBlock = 4;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SupportArmamentPower(p, this.magicNumber)));
        addToBot(new GainBlockAction(p, this.block));

        if (isBranchUpgrade())
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractPower power = p.getPower(SupportArmamentPower.POWER_ID);
                    if (power != null)
                        addToTop(new GainBlockAction(p, power.amount * 2));
                    isDone = true;
                }
            });
        else
            addToBot(new DrawCardAction(p, this.secondaryM));

    }

    public AbstractCard makeCopy() {
        return new TacticalBunker();
    }


    private void baseUpgrade() {
        upgradeMagicNumber(1);
        upgradeBlock(4);
        upgradeSecondM(1);
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    private void branchUpgrade() {
        upgradeMagicNumber(1);
        upgradeBlock(4);
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