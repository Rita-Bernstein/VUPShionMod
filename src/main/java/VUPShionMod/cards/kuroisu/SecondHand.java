package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.SecondHandAction;
import VUPShionMod.cards.AbstractKuroisuCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SecondHand extends AbstractKuroisuCard {
    public static final String ID = VUPShionMod.makeID("SecondHand");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu07.png");
    private static final int COST = 0;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public SecondHand() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 2;
        this.magicNumber = this.baseMagicNumber = 2;
        this.shuffleBackIntoDrawPile = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new SecondHandAction(this));
        }
    }

    public AbstractCard makeCopy() {
        return new SecondHand();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
