package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.MakeLoadedCardAction;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.cards.tempCards.QuickAttack;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AttackWithDefense extends AbstractMinamiCard {
    public static final String ID = VUPShionMod.makeID("AttackWithDefense");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami02.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public AttackWithDefense() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.baseBlock = 9;
        this.cardsToPreview = new QuickAttack();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new MakeLoadedCardAction(new QuickAttack(),this.magicNumber));
//        addToBot(new MakeTempCardInDrawPileAction(new QuickAttack(), this.magicNumber, true, true, false));
        addToBot(new GainBlockAction(p, this.block));
    }

    public AbstractCard makeCopy() {
        return new AttackWithDefense();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeBlock(4);
        }
    }
}
