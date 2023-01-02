package VUPShionMod.cards.ShionCard;


import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardTagsEnum;

public abstract class AbstractShionAnastasiaCard extends AbstractVUPShionCard {

    public AbstractShionAnastasiaCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, rarity, target);
        this.tags.add(CardTagsEnum.SHION_ANASTASIA_CARD);
    }
}