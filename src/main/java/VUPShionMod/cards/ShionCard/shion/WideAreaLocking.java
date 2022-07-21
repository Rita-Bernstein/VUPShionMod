package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.powers.Shion.MatrixAmplifyPower;
import VUPShionMod.powers.Shion.WideAreaLockingPower;
import VUPShionMod.vfx.Common.PortraitWindyPetalEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WideAreaLocking extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID(WideAreaLocking.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy06.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public WideAreaLocking() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new WideAreaLockingPower(p,1)));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}
