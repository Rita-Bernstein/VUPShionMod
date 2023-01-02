package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.ApplyPrayerAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.prayers.LoseStrengthPrayer;
import VUPShionMod.prayers.SinPrayer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TranquilPrayer extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(TranquilPrayer.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/TranquilPrayer.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public TranquilPrayer() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 2;
        this.exhaust = true;
        this.cardsToPreview = new EdgeOfSquall();
        this.tags.add(CardTagsEnum.Prayer_CARD);

        loadJokeCardImage("VUPShionMod/img/cards/Liyezhu/joke/TranquilPrayer.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (isInPrayer())
                    addToTop(new ApplyPrayerAction(new SinPrayer(secondaryM, magicNumber)));

                if (isInJudge()) {
                    AbstractCard temp = new EdgeOfSquall();
                    if (upgraded) temp.upgrade();
                    temp.setCostForTurn(0);
                    addToTop(new MakeTempCardInHandAction(temp, 1));
                }
                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeSecondM(1);
            this.cardsToPreview.upgrade();
            this.selfRetain = true;
        }
    }
}