package VUPShionMod.cards.ShionCard;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.anastasia.FinFunnelUpgrade;
import VUPShionMod.patches.CardColorEnum;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;


public abstract class AbstractVUPShionCard extends CustomCard {

    protected final CardStrings cardStrings;
    protected final String NAME;
    protected final String DESCRIPTION;
    protected final String UPGRADE_DESCRIPTION;
    protected final String[] EXTENDED_DESCRIPTION;

    public int secondaryM;
    public int baseSecondaryM;
    public boolean upgradeSecondaryM;
    public boolean isSecondaryMModified;

    private static final Color ENERGY_COST_RESTRICTED_COLOR = new Color(1.0F, 0.3F, 0.3F, 1.0F);
    private static final Color ENERGY_COST_MODIFIED_COLOR = new Color(0.4F, 1.0F, 0.4F, 1.0F);
    protected static final Color drakOrbRenderColor = Color.WHITE.cpy();

    private static final Texture orb_b = ImageMaster.loadImage("VUPShionMod/img/cardui/Shion/512/card_lime_orb_b.png");
    private static final Texture orb_g = ImageMaster.loadImage("VUPShionMod/img/cardui/Shion/512/card_lime_orb_g.png");
    private static final Texture orb_w = ImageMaster.loadImage("VUPShionMod/img/cardui/Shion/512/card_lime_orb_w.png");

    private static final Texture orb_ab = ImageMaster.loadImage("VUPShionMod/img/cardui/Shion/512/card_lime_orb_ab.png");
    private static final Texture orb_ag = ImageMaster.loadImage("VUPShionMod/img/cardui/Shion/512/card_lime_orb_ag.png");
    private static final Texture orb_aw = ImageMaster.loadImage("VUPShionMod/img/cardui/Shion/512/card_lime_orb_aw.png");

    public String betaArtPath;

    public AbstractVUPShionCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, CardCrawlGame.languagePack.getCardStrings(id).NAME, img, cost, CardCrawlGame.languagePack.getCardStrings(id).DESCRIPTION, type,
                CardColorEnum.VUP_Shion_LIME, rarity, target);

        cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        initializeTitle();
        initializeDescription();

        vupCardSetBanner();
    }

    public void vupCardSetBanner() {
        String rarityString;
        String typeString;

        switch (rarity) {
            case RARE:
                rarityString = "rare";
                break;
            case UNCOMMON:
                rarityString = "uncommon";
                break;
            default:
                rarityString = "common";
        }

        switch (type) {
            case ATTACK:
                typeString = "attack";
                break;
            case POWER:
                typeString = "power";
                break;
            default:
                typeString = "skill";
                break;
        }

        this.setBannerTexture("VUPShionMod/img/banner/512/banner_" + rarityString + ".png", "VUPShionMod/img/banner/1024/banner_" + rarityString + ".png");
        this.setPortraitTextures("VUPShionMod/img/banner/512/frame_" + typeString + "_" + rarityString + ".png",
                "VUPShionMod/img/banner/1024/frame_" + typeString + "_" + rarityString + ".png");
    }


    public void updateDescription() {
        //Overwritten in cards
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

    public int getModifyAlterDamage() {
        return this.secondaryM;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractVUPShionCard card = (AbstractVUPShionCard) super.makeStatEquivalentCopy();
        card.secondaryM = this.secondaryM;
        card.baseSecondaryM = this.baseSecondaryM;
        card.upgradeSecondaryM = this.upgradeSecondaryM;
        card.isSecondaryMModified = this.isSecondaryMModified;
        return card;
    }

    public void postReturnToHand() {
    }

    public void onTriggerLoaded(){

    }

    @SpireOverride
    protected void renderEnergy(SpriteBatch sb) {
        if (this.rarity != CardRarity.SPECIAL) {
            if (this.cost <= -2 || this.isLocked || !this.isSeen) {
                return;
            }

            float x = -108.0f;
            float y = 200.0f;

            if (VUPShionMod.useSimpleOrb) {
                if (this instanceof FinFunnelUpgrade)
                    darkOrbRenderHelper(sb, orb_g, x, y);
                else
                    switch (rarity) {
                        case RARE:
                            darkOrbRenderHelper(sb, orb_g, x, y);
                            break;
                        case UNCOMMON:
                            darkOrbRenderHelper(sb, orb_b, x, y);
                            break;
                        default:
                            darkOrbRenderHelper(sb, orb_w, x, y);
                            break;
                    }
            } else {
                if (this instanceof FinFunnelUpgrade)
                    darkOrbRenderHelper(sb, orb_ag, x, y);
                else
                    switch (rarity) {
                        case RARE:
                            darkOrbRenderHelper(sb, orb_ag, x, y);
                            break;
                        case UNCOMMON:
                            darkOrbRenderHelper(sb, orb_ab, x, y);
                            break;
                        default:
                            darkOrbRenderHelper(sb, orb_aw, x, y);
                            break;
                    }
            }


            Color costColor = Color.WHITE.cpy();
            if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(this) && !this.hasEnoughEnergy()) {
                costColor = ENERGY_COST_RESTRICTED_COLOR;
            } else if (this.isCostModified || this.isCostModifiedForTurn || this.freeToPlay()) {
                costColor = ENERGY_COST_MODIFIED_COLOR;
            }

            costColor.a = this.transparency;
            String text = this.getCost();
            BitmapFont font = this.getEnergyFont();
            if ((this.type != AbstractCard.CardType.STATUS || this.cardID.equals("Slimed")) && (this.color != AbstractCard.CardColor.CURSE || this.cardID.equals("Pride"))) {
                FontHelper.renderRotatedText(sb, font, text, this.current_x, this.current_y, -124.0F * this.drawScale * Settings.scale, 190.0F * this.drawScale * Settings.scale, this.angle, false, costColor);
            }
        } else
            SpireSuper.call(sb);

    }

    protected String getCost() {
        if (this.cost == -1)
            return "X";
        if (freeToPlay()) {
            return "0";
        }
        return Integer.toString(this.costForTurn);
    }

    private BitmapFont getEnergyFont() {
        FontHelper.cardEnergyFont_L.getData().setScale(this.drawScale);
        return FontHelper.cardEnergyFont_L;
    }

    protected void darkOrbRenderHelper(SpriteBatch sb, Texture img, float posX, float posY) {
        sb.setColor(drakOrbRenderColor);
        float length = (float) Math.sqrt(posX * posX + posY * posY);
        float angleFinal = (float) Math.toRadians(this.angle + 180.0f - (float) Math.toDegrees(Math.sinh(posY / length)));
        float drawX = this.current_x + length * (float) Math.cos(angleFinal) * this.drawScale * Settings.scale * 1.0f;
        float drawY = this.current_y + length * (float) Math.sin(angleFinal) * this.drawScale * Settings.scale * 1.0f;

        sb.draw(img,
                drawX - img.getWidth() / 2.0f, drawY - img.getHeight() / 2.0f,
                img.getWidth() / 2.0f, img.getHeight() / 2.0f,
                img.getWidth(), img.getHeight(),
                this.drawScale * Settings.scale * 1.0f,
                this.drawScale * Settings.scale * 1.0f,
                this.angle,
                0, 0,
                img.getWidth(), img.getHeight(),
                false, false);
    }

    public void loadJokeCardImage(String img) {
        this.betaArtPath = img;
        Texture cardTexture = ImageMaster.loadImage(img);
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, cardTexture.getWidth(), cardTexture.getHeight());
        ReflectionHacks.setPrivate(this, AbstractCard.class, "jokePortrait", cardImg);
    }
}