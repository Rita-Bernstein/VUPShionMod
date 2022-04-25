package VUPShionMod.cards.Liyezhu;

import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.stances.JudgeStance;
import VUPShionMod.stances.PrayerStance;
import VUPShionMod.stances.SpiritStance;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class AbstractLiyezhuCard extends AbstractVUPShionCard {

    public AbstractLiyezhuCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, rarity, target);
        this.tags.add(CardTagsEnum.LIYEZHU_CARD);
        this.color = CardColorEnum.Liyezhu_LIME;
    }


    public static boolean isInPrayer() {
        return AbstractDungeon.player.stance.ID.equals(PrayerStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(SpiritStance.STANCE_ID);
    }

    public static boolean isInJudge() {
        return AbstractDungeon.player.stance.ID.equals(JudgeStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(SpiritStance.STANCE_ID);
    }
}
