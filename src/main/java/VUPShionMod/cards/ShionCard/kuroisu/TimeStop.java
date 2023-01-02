package VUPShionMod.cards.ShionCard.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.LoadCardDiscardPileToTopOfDeckAction;
import VUPShionMod.cards.ShionCard.AbstractShionKuroisuCard;
import VUPShionMod.monsters.Story.Ouroboros;
import VUPShionMod.patches.AchievementPatches;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class TimeStop extends AbstractShionKuroisuCard {
    public static final String ID = VUPShionMod.makeID(TimeStop.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/kuroisu/kuroisu03.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public TimeStop() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new StunMonsterAction(m, p, 1));

        if (m.id.equals(Ouroboros.ID) && !m.hasPower(ArtifactPower.POWER_ID)) {
            AchievementPatches.unlockAchievement("10");
        }

        addToBot(new LoadCardDiscardPileToTopOfDeckAction(p, upgraded));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
