package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.GainWingShieldChargeAction;
import VUPShionMod.powers.EisluRen.ExtremeOverloadPower;
import VUPShionMod.ui.WingShield;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;

public class ExtremeOverload extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(ExtremeOverload.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ExtremeOverload.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public ExtremeOverload() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber= 3;
        this.secondaryM = this.baseSecondaryM = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("STANCE_ENTER_WRATH"));
        addToBot(new VFXAction(new BorderFlashEffect(Color.SCARLET, true)));
        addToBot(new VFXAction(new StanceChangeParticleGenerator(p.hb.cX, p.hb.cY, "Wrath")));

        addToBot(new GainWingShieldChargeAction(WingShield.getWingShield().getMaxCount()));
        addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,this.secondaryM)));
        addToBot(new ApplyPowerAction(p,p,new LoseDexterityPower(p,this.secondaryM)));
        addToBot(new ApplyPowerAction(p,p,new ExtremeOverloadPower(p,this.magicNumber)));
    }



    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
            upgradeMagicNumber(-1);
        }
    }
}
