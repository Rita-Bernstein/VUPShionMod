package VUPShionMod.cards.ShionCard.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractKuroisuCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlackHand extends AbstractKuroisuCard  {
    public static final String ID = VUPShionMod.makeID("BlackHand");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu11.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public BlackHand() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
        this.secondaryM = this.baseSecondaryM = 2;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHPAction(p, p, this.magicNumber));

        addToBot(new DrawCardAction(this.secondaryM, new AbstractGameAction() {
            @Override
            public void update() {
                addToTop(new WaitAction(0.4f));
                tickDuration();
                if (this.isDone) {
                    for (AbstractCard c : DrawCardAction.drawnCards)
                        c.setCostForTurn(0);
                }
            }
        }));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(AbstractDungeon.player.currentHealth <= this.magicNumber)
            return false;

        return super.canUse(p, m);
    }

    public AbstractCard makeCopy() {
        return new BlackHand();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeSecondM(1);
        }
    }
}
