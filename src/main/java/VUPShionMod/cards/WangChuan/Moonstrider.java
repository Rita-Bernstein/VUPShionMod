package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.MoonstriderPower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.Predicate;

public class Moonstrider extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("Moonstrider");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc20.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 1;

    public Moonstrider() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 12;
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new GainBlockAction(p, this.block));
        this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        initializeDescription();


        addToBot(new ReducePowerAction(p, p, StiffnessPower.POWER_ID, 1));
        addToBot(new ApplyPowerAction(p, p, new MoonstriderPower(p, this.magicNumber)));

        addToBot(new DrawPileToHandAction(1, AbstractCard.CardType.ATTACK));

    }

    @Override
    public void applyPowers() {
        int b = this.secondaryM;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID)) {
            b += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        }

        this.baseBlock = b;

        super.applyPowers();

        this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        this.rawDescription += EXTENDED_DESCRIPTION[0];
        initializeDescription();
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
