package VUPShionMod.cards.ShionCard.anastasia;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionAnastasiaCard;
import VUPShionMod.cards.ShionCard.optionCards.*;
import VUPShionMod.powers.Shion.*;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class FinFunnelUpgrade extends AbstractShionAnastasiaCard {
    public static final String ID = VUPShionMod.makeID("FinFunnelUpgrade");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/anastasia/anastasia10.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 2;

    private float rotationTimer;
    private int previewIndex;
    private ArrayList<AbstractCard> cardsList = new ArrayList<>();

    public FinFunnelUpgrade() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        cardsList.add(new MatrixUpgrade());
        cardsList.add(new GravityFinFunnelUpgrade());
        cardsList.add(new PursuitFinFunnelUpgrade());
        cardsList.add(new DissectingFinFunnelUpgrade());
        this.purgeOnUse = true;

        this.isInnate = true;
        this.setBannerTexture("VUPShionMod/img/banner/512/banner_rare.png", "VUPShionMod/img/banner/1024/banner_rare.png");
        this.setPortraitTextures("VUPShionMod/img/banner/512/frame_skill_rare.png", "VUPShionMod/img/banner/1024/frame_skill_rare.png");
    }

    public static boolean checkUpgradePower() {
        AbstractPlayer p = AbstractDungeon.player;
        return !p.hasPower(InvestigationFinFunnelUpgradePower.POWER_ID) &&
                !p.hasPower(MatrixFinFunnelUpgradePower.POWER_ID) &&
                !p.hasPower(GravityFinFunnelUpgradePower.POWER_ID) &&
                !p.hasPower(DissectingFinFunnelUpgradePower.POWER_ID) &&
                !p.hasPower(PursuitFinFunnelUpgradePower.POWER_ID);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new MatrixUpgrade());
        list.add(new GravityFinFunnelUpgrade());
        list.add(new PursuitFinFunnelUpgrade());
        list.add(new DissectingFinFunnelUpgrade());
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

    @Override
    public AbstractCard makeCopy() {
        return super.makeCopy();
    }
}
