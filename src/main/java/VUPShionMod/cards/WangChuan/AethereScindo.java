package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.GameStatsPatch;
import VUPShionMod.powers.CorGladiiPower;
import VUPShionMod.powers.StiffnessPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AethereScindo extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("AethereScindo");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc13.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public AethereScindo() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.magicNumber = this.baseMagicNumber = 1;
        this.baseSecondaryM = this.secondaryM = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m);
        if (this.upgraded)
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        addToBot(new DrawCardAction(1));
        addToBot(new ApplyPowerAction(p, p, new StiffnessPower(p, 1)));
    }


    public void applyPowers() {
        super.applyPowers();
        this.damage = GameStatsPatch.lastDamageDeal;
        this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        this.rawDescription += EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }


    public void onMoveToDiscard() {
        this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        initializeDescription();
    }


    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.damage = GameStatsPatch.lastDamageDeal;
        this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        this.rawDescription += EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
