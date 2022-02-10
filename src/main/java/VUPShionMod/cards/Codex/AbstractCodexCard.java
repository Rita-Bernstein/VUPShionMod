package VUPShionMod.cards.Codex;

import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.cards.WangChuan.AbstractWCCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.patches.CardTagsEnum;

public abstract class AbstractCodexCard extends AbstractWCCard {
    public AbstractCodexCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        this(id, img, cost, type, rarity, target, 0);
    }

    public AbstractCodexCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target, int upgrades) {
        super(id, img, cost, type, rarity, target);
        this.tags.add(CardTagsEnum.Codex_CARD);
        this.color = CardColorEnum.Codex_LIME;
        this.timesUpgraded = upgrades;
    }

    @Override
    public boolean canUpgrade() {
        return timesUpgraded <= 1;
    }

    @Override
    public void upgrade() {
        if (timesUpgraded <= 1) {
            this.upgraded = true;
            this.name = EXTENDED_DESCRIPTION[timesUpgraded];
            this.initializeTitle();
            if (timesUpgraded < 1)
                this.rawDescription = UPGRADE_DESCRIPTION;
            else
                this.rawDescription = EXTENDED_DESCRIPTION[2];
            initializeDescription();
            this.timesUpgraded++;
        }
    }
}