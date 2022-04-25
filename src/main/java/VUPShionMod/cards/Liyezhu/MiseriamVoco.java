package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class MiseriamVoco extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(MiseriamVoco.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 2;

    private float rotationTimer;
    private int previewIndex;
    private ArrayList<AbstractCard> cardsList = new ArrayList<>();

    public MiseriamVoco() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 2;

        cardsList.add(new CorCrucis());
        cardsList.add(new VoluntasDoloris());
        cardsList.add(new ViaAfflictionis());
        cardsList.add(new ExitiumMaeroris());

        this.tags.add(CardTagsEnum.Suffering_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new CorCrucis());
        list.add(new VoluntasDoloris());
        list.add(new ViaAfflictionis());

        if (!this.upgraded) list.remove(AbstractDungeon.cardRng.random(list.size() - 1));

        for (AbstractCard card : list) {
            addToBot(new MakeTempCardInHandAction(card));
        }


        AbstractCard c = new ExitiumMaeroris();
        if (upgraded)
            c.upgrade();

        addToBot(new MakeTempCardInHandAction(c));
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            cardsList.get(3).upgrade();
        }
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