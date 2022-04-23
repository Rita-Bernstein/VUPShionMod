package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.ApplyPrayerAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.prayers.RegenPrayer;
import VUPShionMod.prayers.StrengthPrayer;
import VUPShionMod.stances.JudgeStance;
import VUPShionMod.stances.PrayerStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SoleAnthem extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(SoleAnthem.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 2;

    public SoleAnthem() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 2;
        this.exhaust = true;
        this.cardsToPreview = new FlayTheEvil();
        this.tags.add(CardTagsEnum.Prayer_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (AbstractDungeon.player.stance.ID.equals(PrayerStance.STANCE_ID))
                    addToTop(new ApplyPrayerAction(new StrengthPrayer(secondaryM, magicNumber)));

                if (AbstractDungeon.player.stance.ID.equals(JudgeStance.STANCE_ID)) {
                    AbstractCard temp = new FlayTheEvil();
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
            upgradeMagicNumber(1);
        }
    }
}