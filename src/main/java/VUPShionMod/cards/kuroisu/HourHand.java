package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractKuroisuCard;
import VUPShionMod.cards.tempCards.QuickScreen;
import VUPShionMod.powers.BadgeOfTimePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HourHand extends AbstractKuroisuCard {
    public static final String ID = VUPShionMod.makeID("HourHand");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu05.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public HourHand() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 12;
        this.selfRetain = true;
        this.cardsToPreview = new QuickScreen();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new MakeTempCardInDiscardAction(new QuickScreen(),1));
    }

    public AbstractCard makeCopy() {
        return new HourHand();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(4);
        }
    }
}
