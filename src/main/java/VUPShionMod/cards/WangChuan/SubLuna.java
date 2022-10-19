package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ApplyPowerToAllEnemyAction;
import VUPShionMod.powers.Wangchuan.ImmuneDamagePower;
import VUPShionMod.powers.Wangchuan.IntensaPower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import VUPShionMod.powers.Wangchuan.SubLunaPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class SubLuna extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(SubLuna.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc21.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 2;

    public SubLuna() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 9;
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ImmuneDamagePower(p)));
        addToBot(new ApplyPowerAction(p,p,new SubLunaPower(p,1)));
        addToBot(new ReducePowerAction(p, p, StiffnessPower.POWER_ID, this.magicNumber));

        if(this.upgraded)
            addToBot(new ApplyPowerAction(p,p,new IntensaPower(p,1)));

        addToBot(new ApplyPowerAction(p, p, new EnergizedBluePower(p, 1)));
        addToBot(new PressEndTurnButtonAction());
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.selfRetain = true;
        }
    }
}
