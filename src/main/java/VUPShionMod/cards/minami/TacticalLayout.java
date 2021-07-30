package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TacticalLayoutAction;
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
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TacticalLayout extends AbstractVUPShionCard implements BranchingUpgradesCard {
    public static final String ID = VUPShionMod.makeID("TacticalLayout");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami10.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardColor COLOR = CardColorEnum.VUP_Shion_LIME;

    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TacticalLayout() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.baseBlock = 4;
        this.baseDamage = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SupportArmamentPower(p, this.magicNumber)));
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                baseDamage = p.currentBlock;
                calculateCardDamage(m);
                addToTop(new TacticalLayoutAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
                isDone = true;
            }
        });

        if (isBranchUpgrade())
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        else if (upgraded)
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        else
            this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new TacticalLayout();
    }


    @Override
    public void applyPowers() {
        this.baseDamage = AbstractDungeon.player.currentBlock;
        super.applyPowers();

        this.damage += this.block;
        if (isBranchUpgrade())
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1] + cardStrings.EXTENDED_DESCRIPTION[2];
        else if (upgraded)
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        else
            this.rawDescription = cardStrings.DESCRIPTION;

        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        if (isBranchUpgrade())
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        else if (upgraded)
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        else
            this.rawDescription = cardStrings.DESCRIPTION;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (isBranchUpgrade())
            this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1] + cardStrings.EXTENDED_DESCRIPTION[2];
        else if (upgraded)
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        else
            this.rawDescription = cardStrings.DESCRIPTION;
    }

    private void baseUpgrade() {
        upgradeBaseCost(0);
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    private void branchUpgrade() {
        this.target = CardTarget.ENEMY;
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
