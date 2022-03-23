package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.CorGladiiPower;
import VUPShionMod.powers.FlawlessParryPower;
import VUPShionMod.powers.StiffnessPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

public class FlawlessParry extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("FlawlessParry");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc19.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public FlawlessParry() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.baseDamage = 6;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));

        if (p.hasPower(CorGladiiPower.POWER_ID) && upgraded)
            addToBot(new DamageAction(m, new DamageInfo(p, p.getPower(CorGladiiPower.POWER_ID).amount, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));

        addToBot(new ApplyPowerAction(p, p, new FlawlessParryPower(p, this.magicNumber)));
        if (StiffnessPower.applyStiffness())
            addToBot(new ApplyPowerAction(p, p, new StiffnessPower(p, 1)));
        addToBot(new DrawCardAction(1));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.name = EXTENDED_DESCRIPTION[0];
            initializeTitle();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeDamage(3);
        }
    }
}
