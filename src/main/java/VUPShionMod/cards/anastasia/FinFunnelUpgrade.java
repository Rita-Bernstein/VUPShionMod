package VUPShionMod.cards.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractAnastasiaCard;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.cards.optionCards.DimensionSplitterUpgrade;
import VUPShionMod.cards.optionCards.GravityFinFunnelUpgrade;
import VUPShionMod.cards.optionCards.InvestigationFinFunnelUpgrade;
import VUPShionMod.cards.optionCards.PursuitFinFunnelUpgrade;
import VUPShionMod.patches.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class FinFunnelUpgrade extends AbstractAnastasiaCard {
    public static final String ID = VUPShionMod.makeID("FinFunnelUpgrade");
    public static final String IMG = VUPShionMod.assetPath( "img/cards/shion/zy02.png"); //TODO anastasia10.png
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 0;

    public FinFunnelUpgrade() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new DimensionSplitterUpgrade());
        list.add(new InvestigationFinFunnelUpgrade());
        list.add(new GravityFinFunnelUpgrade());
        list.add(new PursuitFinFunnelUpgrade());
        addToBot(new ChooseOneAction(list));
    }
}
