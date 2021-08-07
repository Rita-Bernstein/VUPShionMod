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
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class FinFunnelUpgrade extends AbstractAnastasiaCard {
    public static final String ID = VUPShionMod.makeID("FinFunnelUpgrade");
    public static final String IMG = VUPShionMod.assetPath("img/cards/anastasia/anastasia10.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 0;

    private float rotationTimer;
    private int previewIndex;
    private ArrayList<AbstractCard> cardsList = new ArrayList<>();

    public FinFunnelUpgrade() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        cardsList.add(new DimensionSplitterUpgrade());
        cardsList.add(new InvestigationFinFunnelUpgrade());
        cardsList.add(new GravityFinFunnelUpgrade());
        cardsList.add(new PursuitFinFunnelUpgrade());
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


    @Override
    public void update() {
        super.update();
        if (hb.hovered) {
            if (rotationTimer <= 0F) {
                rotationTimer = 2F;
                if (cardsList.size() == 0) {
                    cardsToPreview = CardLibrary.cards.get("Madness");
                } else {
                    cardsToPreview = cardsList.get(previewIndex);
                }
                if (previewIndex == cardsList.size() - 1) {
                    previewIndex = 0;
                } else {
                    previewIndex++;
                }
            } else {
                rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }
}
