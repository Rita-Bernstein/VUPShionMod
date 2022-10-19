package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.FlickeringTipAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlickeringTip extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(FlickeringTip.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/FlickeringTip.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public FlickeringTip() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 14;
        this.magicNumber = this.baseMagicNumber = 3;
        vupCardSetBanner(CardRarity.RARE,CardType.ATTACK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FlickeringTipAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(5);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.selfRetain = true;
        }
    }
}