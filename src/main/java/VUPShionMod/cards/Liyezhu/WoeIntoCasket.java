package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ExhaustAllStatusAndCurseAction;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WoeIntoCasket extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(WoeIntoCasket.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/WoeIntoCasket.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public WoeIntoCasket() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
        GraveField.grave.set(this,true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExhaustAllStatusAndCurseAction());
        if (p.hasPower(PsychicPower.POWER_ID)) {
            int amount = p.getPower(PsychicPower.POWER_ID).amount;
            if (amount > 7) amount = 7;
            addToBot(new GainEnergyAction(amount));
            addToBot(new DrawCardAction(amount));
            addToBot(new ReducePowerAction(p, p, PsychicPower.POWER_ID,amount));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}