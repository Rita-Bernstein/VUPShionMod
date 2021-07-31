package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;

public class HolyCharge extends AbstractVUPShionCard implements BranchingUpgradesCard {
    public static final String ID = VUPShionMod.makeID("HolyCharge");
    public static final String IMG = VUPShionMod.assetPath("img/cards/liyezhu/lyz09.png"); //TODO lyz05.png
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardColor COLOR = CardColorEnum.VUP_Shion_LIME;

    private static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public HolyCharge() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 8;
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (this.isBranchUpgrade()) {
                this.type = CardType.SKILL;
                this.name = cardStrings.EXTENDED_DESCRIPTION[0];
                this.initializeTitle();
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
                this.upgradeSecondM(1);
            } else {
                this.upgradeDamage(2);
                this.upgradeMagicNumber(1);
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            }
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.isBranchUpgrade()) {
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