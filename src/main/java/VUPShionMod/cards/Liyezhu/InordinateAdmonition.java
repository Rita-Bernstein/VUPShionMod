package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.ApplyPrayerAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.prayers.SelfSinPrayer;
import VUPShionMod.prayers.VampirePrayer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InordinateAdmonition extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(InordinateAdmonition.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/InordinateAdmonition.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 2;

    public InordinateAdmonition() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 6;
        this.isMultiDamage = true;
        this.secondaryM = this.baseSecondaryM = 2;
        this.exhaust = true;
        this.cardsToPreview = new ChasteReflection();
        this.tags.add(CardTagsEnum.Prayer_CARD);

        loadJokeCardImage("VUPShionMod/img/cards/Liyezhu/joke/InordinateAdmonition.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (isInPrayer()) {
                    addToTop(new ApplyPrayerAction(new VampirePrayer(secondaryM, baseDamage)));
                    addToTop(new ApplyPrayerAction(new SelfSinPrayer(secondaryM, 1)));
                }
                if (isInJudge() ) {
                    AbstractCard temp = new ChasteReflection();
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
            this.cardsToPreview.upgrade();
            upgradeDamage(4);
        }
    }
}