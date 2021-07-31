package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import VUPShionMod.powers.HolySlashDownPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HolySlashDown extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("HolySlashDown");
    public static final String IMG = VUPShionMod.assetPath("img/cards/liyezhu/lyz09.png"); //TODO lyz12.png
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardColor COLOR = CardColorEnum.VUP_Shion_LIME;

    private static final int COST = 2;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public HolySlashDown() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 4;
        this.baseSecondaryM = this.secondaryM = 2;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(4);
            this.upgradeSecondM(1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BadgeOfThePaleBlueCrossPower(p, this.baseMagicNumber)));
        if (this.upgraded) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    int amount = p.getPower(BadgeOfThePaleBlueCrossPower.POWER_ID).amount * 2;
                    addToBot(new GainBlockAction(p, amount));
                    isDone = true;
                }
            });
        } else {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    int amount = p.getPower(BadgeOfThePaleBlueCrossPower.POWER_ID).amount;
                    addToBot(new GainBlockAction(p, amount));
                    isDone = true;
                }
            });
        }
        addToBot(new ApplyPowerAction(p, p, new HolySlashDownPower(p, this.baseSecondaryM)));
    }
}
