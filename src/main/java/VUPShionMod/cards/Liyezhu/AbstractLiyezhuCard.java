package VUPShionMod.cards.Liyezhu;

import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.patches.CardTagsEnum;

public abstract class AbstractLiyezhuCard extends AbstractVUPShionCard {

    public AbstractLiyezhuCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, rarity, target);
        this.tags.add(CardTagsEnum.LIYEZHU_CARD);
        this.color = CardColorEnum.Liyezhu_LIME;
    }

}
