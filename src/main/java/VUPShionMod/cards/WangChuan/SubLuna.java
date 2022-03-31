package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.StiffnessPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class SubLuna extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("SubLuna");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc21.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 3;

    public SubLuna() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 9;
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1)));
        if(StiffnessPower.applyStiffness())
        addToBot(new ReducePowerAction(p, p, StiffnessPower.POWER_ID, this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new EnergizedBluePower(p, 3)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(2);
        }
    }
}
