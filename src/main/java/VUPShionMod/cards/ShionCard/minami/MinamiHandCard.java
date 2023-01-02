package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.SelectSrcCardToHandAction;
import VUPShionMod.actions.Shion.FinFunnelMinionAction;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import VUPShionMod.patches.CardTagsEnum;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.Predicate;

public class MinamiHandCard extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID(MinamiHandCard.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami05.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    public MinamiHandCard() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        GraveField.grave.set(this, true);
        this.tags.add(CardTagsEnum.TRIGGER_FIN_FUNNEL);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new FinFunnelMinionAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng)));
        addToBot(new SelectSrcCardToHandAction(1, true,
                card -> card.type == AbstractCard.CardType.POWER,
                card -> card.setCostForTurn(0)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }

}
