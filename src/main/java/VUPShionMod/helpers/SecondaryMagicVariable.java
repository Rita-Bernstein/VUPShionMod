package VUPShionMod.helpers;

import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.cards.WangChuan.AbstractWCCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class SecondaryMagicVariable extends DynamicVariable {
    @Override
    public String key() {
        return "VUPShionSecondM";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractVUPShionCard) {
            AbstractVUPShionCard asc = (AbstractVUPShionCard) card;
            return asc.isSecondaryMModified;
        } else
            return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractVUPShionCard) {
            AbstractVUPShionCard asc = (AbstractVUPShionCard) card;
            return asc.secondaryM;
        } else
            return 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractVUPShionCard) {
            AbstractVUPShionCard asc = (AbstractVUPShionCard) card;
            return asc.baseSecondaryM;
        } else {
            return 0;
        }
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractVUPShionCard) {
            AbstractVUPShionCard asc = (AbstractVUPShionCard) card;
            return asc.upgradeSecondaryM;
        } else {
            return false;
        }
    }
}