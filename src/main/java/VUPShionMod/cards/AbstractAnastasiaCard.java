package VUPShionMod.cards;


import VUPShionMod.patches.CardTagsEnum;

public abstract class AbstractAnastasiaCard extends AbstractVUPShionCard {

    public AbstractAnastasiaCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, rarity, target);
        this.tags.add(CardTagsEnum.ANASTASIA_CARD);
    }
}