package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TacticalLayoutAction;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.powers.SupportArmamentPower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TacticalLayout extends AbstractMinamiCard implements BranchingUpgradesCard {
    public static final String ID = VUPShionMod.makeID("TacticalLayout");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami04.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TacticalLayout() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
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

        if (upgraded) {
            if (this.getUpgradeType() == UpgradeType.BRANCH_UPGRADE)
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            else
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        } else
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
        if (upgraded) {
            if (this.getUpgradeType() == UpgradeType.BRANCH_UPGRADE)
                this.rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2];
            else
                this.rawDescription = UPGRADE_DESCRIPTION;
        } else
            this.rawDescription = DESCRIPTION;

        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        if (upgraded) {
            if (this.getUpgradeType() == UpgradeType.BRANCH_UPGRADE)
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            else
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        } else
            this.rawDescription = cardStrings.DESCRIPTION;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (upgraded) {
            if (this.getUpgradeType() == UpgradeType.BRANCH_UPGRADE)
                this.rawDescription = EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2];
            else
                this.rawDescription = UPGRADE_DESCRIPTION;
        } else
            this.rawDescription = DESCRIPTION;
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
