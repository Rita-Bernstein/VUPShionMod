package VUPShionMod.cards;


import VUPShionMod.patches.CardTagsEnum;

public abstract class AbstractShionCard extends AbstractVUPShionCard {

    public AbstractShionCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, rarity, target);
        this.tags.add(CardTagsEnum.SHION_CARD);
    }
}