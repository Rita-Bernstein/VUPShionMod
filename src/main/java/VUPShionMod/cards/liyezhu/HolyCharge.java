package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractLiyezhuCard;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;

public class HolyCharge extends AbstractLiyezhuCard implements BranchingUpgradesCard {
    public static final String ID = VUPShionMod.makeID("HolyCharge");
    public static final String IMG = VUPShionMod.assetPath("img/cards/liyezhu/lyz05.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public HolyCharge() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 8;
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (this.isBranchUpgrade()) {
                this.tags.add(CardTags.HEALING);
                this.type = CardType.SKILL;
                this.name = cardStrings.EXTENDED_DESCRIPTION[0];
                this.initializeTitle();
                this.rawDescription = EXTENDED_DESCRIPTION[1];
                this.upgradeSecondM(1);
            } else {
                this.upgradeDamage(2);
                this.upgradeMagicNumber(1);
                this.rawDescription = UPGRADE_DESCRIPTION;
            }
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded && getUpgradeType() == UpgradeType.BRANCH_UPGRADE) {
            addToBot(new HealAction(p, p, 8));
            if (p.hasPower(BadgeOfThePaleBlueCrossPower.POWER_ID)) {
                int mul = p.getPower(BadgeOfThePaleBlueCrossPower.POWER_ID).amount;
                addToBot(new HealAction(p, p, mul * this.baseMagicNumber));
                addToBot(new GainBlockAction(p, mul * this.baseSecondaryM));
            }
        } else {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            if (p.hasPower(BadgeOfThePaleBlueCrossPower.POWER_ID)) {
                int mul = p.getPower(BadgeOfThePaleBlueCrossPower.POWER_ID).amount;
                addToBot(new DamageAction(m, new DamageInfo(p, mul * this.baseMagicNumber, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new ApplyPowerAction(p, p, new RegenPower(p, mul * this.baseSecondaryM)));
            }
        }
        addToBot(new RemoveSpecificPowerAction(p, p, BadgeOfThePaleBlueCrossPower.POWER_ID));
    }
}
