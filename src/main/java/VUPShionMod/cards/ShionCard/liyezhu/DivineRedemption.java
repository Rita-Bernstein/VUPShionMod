package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.cards.ShionCard.AbstractShionLiyezhuCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DivineRedemption extends AbstractShionLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(DivineRedemption.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/liyezhu/lyz03.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public DivineRedemption() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 9;
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHyperdimensionalLinksAction(this.magicNumber));
//        addToBot(new ApplyPowerAction(p, p, new HyperdimensionalLinksPower(p, this.magicNumber),this.magicNumber));
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c.type == CardType.STATUS || c.type == CardType.CURSE)
                        temp.addToTop(c);
                }

                if (temp.isEmpty()) {
                    isDone = true;
                    return;
                }

                if (temp.size() <= secondaryM) {
                    for (AbstractCard c : temp.group) {
                        addToTop(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                    }

                    isDone = true;
                    return;
                }

                AbstractCard card;
                for (int i = 0; i < secondaryM; i++) {
                    card = temp.group.get(AbstractDungeon.cardRandomRng.random(temp.group.size() - 1));
                    addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                    temp.group.remove(card);
                }

                isDone = true;
            }
        });
    }

    public AbstractCard makeCopy() {
        return new DivineRedemption();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}
