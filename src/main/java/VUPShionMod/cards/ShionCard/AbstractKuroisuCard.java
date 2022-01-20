package VUPShionMod.cards.ShionCard;


import VUPShionMod.patches.CardTagsEnum;

public abstract class AbstractKuroisuCard extends AbstractVUPShionCard {

    public AbstractKuroisuCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, rarity, target);
        this.tags.add(CardTagsEnum.KUROISU_CARD);
    }
}