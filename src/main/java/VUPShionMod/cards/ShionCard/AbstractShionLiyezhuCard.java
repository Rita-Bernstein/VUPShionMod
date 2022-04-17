package VUPShionMod.cards.ShionCard;


import VUPShionMod.patches.CardTagsEnum;

public abstract class AbstractShionLiyezhuCard extends AbstractVUPShionCard {

    public AbstractShionLiyezhuCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, rarity, target);
        this.tags.add(CardTagsEnum.SHION_LIYEZHU_CARD);
    }
}