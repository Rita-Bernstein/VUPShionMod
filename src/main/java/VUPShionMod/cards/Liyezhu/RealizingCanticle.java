package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.ApplyPrayerAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.prayers.ArtifactPrayer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class RealizingCanticle extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(RealizingCanticle.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/RealizingCanticle.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public RealizingCanticle() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.secondaryM = this.baseSecondaryM = 1;
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
        this.cardsToPreview = new BeinglessMoment();
        this.tags.add(CardTagsEnum.Prayer_CARD);

        loadJokeCardImage("VUPShionMod/img/cards/Liyezhu/joke/RealizingCanticle.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new ArtifactPower(p,1)));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (isInPrayer())
                    addToTop(new ApplyPrayerAction(new ArtifactPrayer(secondaryM, magicNumber)));

                if (isInJudge() ) {
                    AbstractCard temp = new BeinglessMoment();
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
            upgradeSecondM(1);
            this.selfRetain = true;
        }
    }
}