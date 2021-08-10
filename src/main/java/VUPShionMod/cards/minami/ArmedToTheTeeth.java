package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.LoseHyperdimensionalLinksAction;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ArmedToTheTeeth extends AbstractMinamiCard {
    public static final String ID = VUPShionMod.makeID("ArmedToTheTeeth");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami15.png");
    private static final int COST = 3;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ArmedToTheTeeth() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("SHION_16"));

        if (p.hasPower(HyperdimensionalLinksPower.POWER_ID)) {
            int amount = p.getPower(HyperdimensionalLinksPower.POWER_ID).amount;
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, amount), amount));
            addToBot(new LoseHyperdimensionalLinksAction(true));
        }
    }

    public AbstractCard makeCopy() {
        return new ArmedToTheTeeth();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeBaseCost(2);
        }
    }
}
