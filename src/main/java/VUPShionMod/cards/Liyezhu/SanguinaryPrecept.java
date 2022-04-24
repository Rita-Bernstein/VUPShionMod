package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.ApplyPrayerAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.prayers.LoseHPPrayer;
import VUPShionMod.prayers.MiraclePrayer;
import VUPShionMod.prayers.MiracleUpgradePrayer;
import VUPShionMod.stances.JudgeStance;
import VUPShionMod.stances.PrayerStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SanguinaryPrecept extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(SanguinaryPrecept.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public SanguinaryPrecept() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 6;
        this.secondaryM = this.baseSecondaryM = 2;
        this.exhaust = true;
        this.cardsToPreview = new FlickeringTip();
        this.tags.add(CardTagsEnum.Prayer_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (AbstractDungeon.player.stance.ID.equals(PrayerStance.STANCE_ID)) {
                        addToTop(new ApplyPrayerAction(new LoseHPPrayer(secondaryM, magicNumber)));
                }
                if (AbstractDungeon.player.stance.ID.equals(JudgeStance.STANCE_ID)) {
                    AbstractCard temp = new FlickeringTip();
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
            upgradeMagicNumber(3);
        }
    }
}