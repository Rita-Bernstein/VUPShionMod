package VUPShionMod.cards.WangChuan;

import VUPShionMod.patches.CardColorEnum;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;

public abstract class AbstractWCCard extends CustomCard {

    protected final CardStrings cardStrings;
    protected final String NAME;
    protected final String DESCRIPTION;
    protected final String UPGRADE_DESCRIPTION;
    protected final String[] EXTENDED_DESCRIPTION;

    public int secondaryM;
    public int baseSecondaryM;
    public boolean upgradeSecondaryM;
    public boolean isSecondaryMModified;


    public String betaArtPath;

    public AbstractWCCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, CardCrawlGame.languagePack.getCardStrings(id).NAME, img, cost, CardCrawlGame.languagePack.getCardStrings(id).DESCRIPTION, type,
                CardColorEnum.WangChuan_LIME, rarity, target);

        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();
    }


    protected void upgradeSecondM(int amount) {
        this.baseSecondaryM += amount;
        this.secondaryM = this.baseSecondaryM;
        this.upgradeSecondaryM = true;
    }

    public void safeChangeSecondM(int amount) {
        this.baseSecondaryM += amount;
        if (this.baseSecondaryM < 0) this.baseSecondaryM = 0;
        this.secondaryM = this.baseSecondaryM;
        this.upgradeSecondaryM = true;
    }

    @Override
    public void displayUpgrades() {
        super.displayUpgrades();

        if (this.upgradeSecondaryM) {
            this.secondaryM = this.baseSecondaryM;
            this.isSecondaryMModified = true;
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractWCCard card = (AbstractWCCard) super.makeStatEquivalentCopy();
        card.secondaryM = this.secondaryM;
        card.baseSecondaryM = this.baseSecondaryM;
        card.upgradeSecondaryM = this.upgradeSecondaryM;
        card.isSecondaryMModified = this.isSecondaryMModified;
        return card;
    }


    public void loadJokeCardImage(String img) {
        this.betaArtPath = img;
        Texture cardTexture = ImageMaster.loadImage(img);
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, cardTexture.getWidth(), cardTexture.getHeight());
        ReflectionHacks.setPrivate(this, AbstractCard.class, "jokePortrait", cardImg);
    }
}