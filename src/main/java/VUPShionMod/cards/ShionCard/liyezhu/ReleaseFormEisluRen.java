package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.cards.ShionCard.AbstractShionLiyezhuCard;
import VUPShionMod.powers.Shion.ReleaseFormEisluRenPower;
import VUPShionMod.powers.Shion.ReleaseFormLiyezhuBPower;
import VUPShionMod.powers.Shion.ReleaseFormLiyezhuCPower;
import VUPShionMod.powers.Shion.ReleaseFormLiyezhuPower;
import VUPShionMod.vfx.Common.AbstractSpineEffect;
import VUPShionMod.vfx.Common.PortraitWindyPetalEffect;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.Consumer;

public class ReleaseFormEisluRen extends AbstractShionLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(ReleaseFormEisluRen.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/ReleaseFormEisluRen.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ReleaseFormEisluRen() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new PortraitWindyPetalEffect("EisluRen"),1.0f));
        addToBot(new ApplyPowerAction(p, p, new ReleaseFormEisluRenPower(p, 1), 0));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }


}
