package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.MakeLoadedCardAction;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Boot extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID(Boot.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy20.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public Boot() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard card : p.hand.group) {
                    AbstractCard t = card.makeSameInstanceOf();
                    addToTop(new MakeLoadedCardAction(t,magicNumber));
                }
                isDone = true;
            }
        });


        addToBot(new PressEndTurnButtonAction());
    }
}
