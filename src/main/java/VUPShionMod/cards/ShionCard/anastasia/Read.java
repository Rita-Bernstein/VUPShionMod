package VUPShionMod.cards.ShionCard.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionAnastasiaCard;
import VUPShionMod.patches.CardTagsEnum;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.Predicate;

public class Read extends AbstractShionAnastasiaCard {
    public static final String ID = VUPShionMod.makeID("Read");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/anastasia/anastasia11.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Read() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Predicate<AbstractCard> predicate = (pr) -> pr.hasTag(CardTagsEnum.LOADED);
        addToBot(new MoveCardsAction(p.hand, p.drawPile, predicate, this.magicNumber));
//        addToBot(new ReadAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }


}
