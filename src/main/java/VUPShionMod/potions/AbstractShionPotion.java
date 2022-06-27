package VUPShionMod.potions;

import VUPShionMod.VUPShionMod;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public abstract class AbstractShionPotion extends AbstractPotion {
    public AbstractShionPotion(String id, PotionRarity rarity, PotionSize size) {
        super(CardCrawlGame.languagePack.getPotionString(id).NAME, id, rarity, size, AbstractPotion.PotionColor.STRENGTH);

    }
}
