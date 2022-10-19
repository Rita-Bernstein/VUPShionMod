package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.actions.EisluRen.AddWingShieldDamageReduceAction;
import VUPShionMod.powers.EisluRen.CoverMinionPower;
import VUPShionMod.powers.EisluRen.RoyalGuardianPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;

public class RoyalGuardian extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(RoyalGuardian.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/RoyalGuardian.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public RoyalGuardian() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 3;
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.1F));
        } else {
            addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
        }

        addToBot(new ApplyPowerAction(p,p,new RoyalGuardianPower(p,this.magicNumber)));
        addToBot(new AddWingShieldDamageReduceAction(this.secondaryM));
        addToBot(new ApplyPowerAction(p,p,new CoverMinionPower(p)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}
