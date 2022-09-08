package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.LoseMaxHPAction;
import VUPShionMod.actions.Common.UpgradeDeckAction;
import VUPShionMod.actions.Liyezhu.ApplyPrayerAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.prayers.StrengthPrayer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LiyezhuUpgradeCard extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(LiyezhuUpgradeCard.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/LiyezhuUpgradeCard.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public LiyezhuUpgradeCard() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.exhaust = true;
        this.selfRetain = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int count = 0;
                for (AbstractCard card : AbstractDungeon.player.hand.group) {
                    if (card instanceof Miracle) {
                        count++;
                    }
                }

                if (count >= magicNumber) {
                    for (AbstractCard card : AbstractDungeon.player.hand.group) {
                        if (card instanceof Miracle) {
                            addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                        }
                    }
                } else {
                    addToTop(new LoseMaxHPAction(p, p, magicNumber));
                }


                isDone = true;
            }
        });

        if (this.upgraded) {
            addToBot(new UpgradeDeckAction(1, true));
        } else {
            addToBot(new UpgradeDeckAction(1, false));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}