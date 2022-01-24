package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BombardaMagica extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("BombardaMagica");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/PlaceHolder.png");  // todo
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public BombardaMagica() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 6;
        this.selfRetain = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        addToBot(new MakeTempCardInHandAction(makeStatEquivalentCopy()));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(3);
        }
    }
}
