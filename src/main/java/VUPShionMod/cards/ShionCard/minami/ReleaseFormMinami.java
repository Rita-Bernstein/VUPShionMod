package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import VUPShionMod.powers.Shion.ReleaseFormMinamiPower;
import VUPShionMod.vfx.Common.PortraitWindyPetalEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReleaseFormMinami extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID(ReleaseFormMinami.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami09.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ReleaseFormMinami() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new PortraitWindyPetalEffect("Minami"),1.0f));
        addToBot(new ApplyPowerAction(p, p, new ReleaseFormMinamiPower(p, this.magicNumber), 0));
    }

    public AbstractCard makeCopy() {
        return new ReleaseFormMinami();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.isEthereal = false;
        }
    }
}