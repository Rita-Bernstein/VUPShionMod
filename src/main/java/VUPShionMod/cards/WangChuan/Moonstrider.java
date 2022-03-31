package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.CorGladiiPower;
import VUPShionMod.powers.MoonstriderPower;
import VUPShionMod.powers.StiffnessPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Moonstrider extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("Moonstrider");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc20.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 1;

    public Moonstrider() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 12;
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ApplyPowerAction(p, p, new MoonstriderPower(p, this.magicNumber)));

        this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        initializeDescription();
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
            this.selfRetain = true;
            upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
