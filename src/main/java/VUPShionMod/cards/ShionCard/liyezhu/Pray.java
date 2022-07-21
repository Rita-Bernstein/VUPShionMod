package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.cards.ShionCard.AbstractShionLiyezhuCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Pray extends AbstractShionLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(Pray.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/liyezhu/lyz07.png");
    private static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Pray() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 2;
        this.baseSecondaryM = this.secondaryM = 1;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHyperdimensionalLinksAction(2));
//        addToBot(new ApplyPowerAction(p, p, new HyperdimensionalLinksPower(p, 2)));
        addToBot(new DrawCardAction(this.baseMagicNumber, new AbstractGameAction() {
            @Override
            public void update() {
                int ctr = 0;
                for (AbstractCard card : DrawCardAction.drawnCards) {
                    card.setCostForTurn(0);
                    ctr++;
                    if (ctr >= Pray.this.baseSecondaryM) {
                        break;
                    }
                }
                isDone = true;
            }
        }));
    }
}
