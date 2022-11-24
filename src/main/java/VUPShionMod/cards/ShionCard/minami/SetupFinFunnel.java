package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.AddFinFunnelAction;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower;

public class SetupFinFunnel extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID(SetupFinFunnel.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami04.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    public SetupFinFunnel(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.isInnate = true;
        this.returnToHand = true;
        this.selfRetain = true;
        this.magicNumber = this.baseMagicNumber = 1;
        this.timesUpgraded = upgrades;
    }

    public SetupFinFunnel() {
        this(0);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AddFinFunnelAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p,p,new EnergyDownPower(p,1)));
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    public void upgrade() {
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();

        upgradeMagicNumber(2);

    }

    @Override
    public AbstractCard makeCopy() {
        return new SetupFinFunnel(this.timesUpgraded);
    }
}
