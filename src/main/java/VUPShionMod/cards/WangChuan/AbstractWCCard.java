package VUPShionMod.cards.WangChuan;

import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.patches.CardTagsEnum;

public abstract class AbstractWCCard extends AbstractVUPShionCard {

    public AbstractWCCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, rarity, target);
        this.tags.add(CardTagsEnum.WANGCHUAN_CARD);
        this.color = CardColorEnum.WangChuan_LIME;
    }
}