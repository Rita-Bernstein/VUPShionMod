package VUPShionMod.cards.EisluRen;

import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;

public abstract class AbstractEisluRenCard extends AbstractVUPShionCard {

    public AbstractEisluRenCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, rarity, target);
        this.color = CardColorEnum.EisluRen_LIME;
    }
}