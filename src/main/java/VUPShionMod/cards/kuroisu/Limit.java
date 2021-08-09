package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractKuroisuCard;
import VUPShionMod.powers.BadgeOfTimePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class Limit extends AbstractKuroisuCard {
    public static final String ID = VUPShionMod.makeID("Limit");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu13.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;

    public Limit() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.baseDamage = 6;
        this.isMultiDamage = true;
        this.baseBlock = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReducePowerAction(p, p, BadgeOfTimePower.POWER_ID, this.secondaryM));
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.FIRE, true));
    }

    public AbstractCard makeCopy() {
        return new Limit();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(BadgeOfTimePower.POWER_ID)) {
            if (p.getPower(BadgeOfTimePower.POWER_ID).amount >= 3)
                return super.canUse(p, m);
        }
        return false;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(6);
            upgradeBlock(6);
        }
    }
}
