package VUPShionMod.cards.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.relics.DimensionSplitterAria;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.List;

public class Bombardment extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("Bombardment");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "img/cards/shion/zy19.png";

    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public Bombardment() {
        super(ID, NAME, VUPShionMod.assetPath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColorEnum.VUP_Shion_LIME, RARITY, TARGET);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded && p.hasRelic(DimensionSplitterAria.ID)) {
            AbstractRelic relic = p.getRelic(DimensionSplitterAria.ID);
            relic.atTurnStart();
        }
        List<AbstractFinFunnel> funnelList = AbstractPlayerPatches.AddFields.finFunnelList.get(p);
        for (AbstractFinFunnel funnel : funnelList) {
            funnel.atTurnStart();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
