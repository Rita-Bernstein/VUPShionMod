package VUPShionMod.cards.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.ChangeCostAction;
import VUPShionMod.cards.AbstractShionCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

public class FirstStrike extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("FirstStrike");
    public static final String IMG =  VUPShionMod.assetPath("img/cards/shion/zy21.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public FirstStrike() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 10;
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ChangeCostAction(this.uuid, this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new FirstStrike();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
        }
    }
}