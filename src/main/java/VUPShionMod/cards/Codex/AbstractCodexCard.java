package VUPShionMod.cards.Codex;

import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.cards.WangChuan.AbstractWCCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.patches.CardTagsEnum;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;


public abstract class AbstractCodexCard extends AbstractWCCard implements SpawnModificationCard {
    protected String parentCardID;

    public AbstractCodexCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, rarity, target);
        this.tags.add(CardTagsEnum.Codex_CARD);
        this.color = CardColorEnum.Codex_LIME;
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) {
        if (parentCardID != null)
            for (AbstractCard card : currentRewardCards) {
                if (card.cardID.equals(ChaosRapidus.ID))
                    return false;
            }
        return true;
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
                this.rawDescription = EXTENDED_DESCRIPTION[2];
            else
                this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.timesUpgraded++;
        }
    }
}