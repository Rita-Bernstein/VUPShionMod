package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TriggerAllFinFunnelAction;
import VUPShionMod.actions.TriggerDimensionSplitterAction;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.BadgeOfTimePower;
import VUPShionMod.powers.GainBadgeOfTimePower;
import VUPShionMod.relics.DimensionSplitterAria;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class CrackOfTime extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("CrackOfTime");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu08.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardColor COLOR = CardColorEnum.VUP_Shion_LIME;

    private static final int COST = 2;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public CrackOfTime() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        addToBot(new GainBlockAction(p, this.block));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (p.hasPower(BadgeOfTimePower.POWER_ID)) {
                    int amount = p.getPower(BadgeOfTimePower.POWER_ID).amount;
                    if (amount > 1)
                        addToTop(new ApplyPowerAction(p, p, new GainBadgeOfTimePower(p, (int) Math.floor(amount / 2.0f))));
                    addToTop(new RemoveSpecificPowerAction(p, p, BadgeOfTimePower.POWER_ID));
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void applyPowers() {
        this.baseBlock = 0;
        if (AbstractDungeon.player.hasPower(BadgeOfTimePower.POWER_ID))
            this.baseBlock = AbstractDungeon.player.getPower(BadgeOfTimePower.POWER_ID).amount * this.magicNumber;

        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
        this.isBlockModified = this.block != this.baseBlock;
    }

    public AbstractCard makeCopy() {
        return new CrackOfTime();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeBaseCost(1);
        }
    }
}
