package VUPShionMod.cards.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.SelectCardToHandAction;
import VUPShionMod.cards.AbstractAnastasiaCard;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class TeamWork extends AbstractAnastasiaCard {
    public static final String ID = VUPShionMod.makeID("TeamWork");
    public static final String IMG = VUPShionMod.assetPath("img/cards/anastasia/anastasia06.png");
    private static final int COST = 3;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public TeamWork() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardToHandAction(returnRandomCardByCardTagInCombat(CardTagsEnum.KUROISU_CARD), true, true));
        addToBot(new SelectCardToHandAction(returnRandomCardByCardTagInCombat(CardTagsEnum.ANASTASIA_CARD), true, true));
        addToBot(new SelectCardToHandAction(returnRandomCardByCardTagInCombat(CardTagsEnum.MINAMI_CARD), true, true));
        addToBot(new SelectCardToHandAction(returnRandomCardByCardTagInCombat(CardTagsEnum.LIYEZHU_CARD), true, true));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }

    public static ArrayList<AbstractCard> returnRandomCardByCardTagInCombat(CardTags tag) {
        ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
        ArrayList<AbstractCard> returnCard = new ArrayList<AbstractCard>();
        for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
            if (c.hasTag(tag) && !c.hasTag(AbstractCard.CardTags.HEALING) && !(c instanceof TeamWork)) {
                list.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
            if (c.hasTag(tag) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }
        for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
            if (c.hasTag(tag) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }


        int temp;
        for (int i = 0; i < 3; i++) {
            temp = AbstractDungeon.cardRng.random(list.size() - 1);
            returnCard.add(list.get(temp));
            list.remove(temp);
        }

        return returnCard;
    }


}
