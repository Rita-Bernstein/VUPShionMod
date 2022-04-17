package VUPShionMod.cards.ShionCard;


import VUPShionMod.patches.CardTagsEnum;

public abstract class AbstractShionMinamiCard extends AbstractVUPShionCard {

    public AbstractShionMinamiCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, rarity, target);
        this.tags.add(CardTagsEnum.SHION_MINAMI_CARD);
    }
}