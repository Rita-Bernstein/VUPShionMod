package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.EisluRen.YonggukCityTroyPower;
import VUPShionMod.ui.WingShield;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.InvinciblePower;

public class YonggukCityTroy extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(YonggukCityTroy.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/YonggukCityTroy.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 3;

    public YonggukCityTroy() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.secondaryM = this.baseSecondaryM = 4;
        this.baseBlock = 6;
        this.magicNumber = this.baseMagicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!hasTag(CardTagsEnum.NoWingShieldCharge))
            addToBot(new LoseWingShieldAction(this.secondaryM));
        for (int i = 0; i < this.magicNumber; i++)
            addToBot(new GainBlockAction(p, 6));
        addToBot(new ApplyPowerAction(p, p, new YonggukCityTroyPower(p)));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!hasTag(CardTagsEnum.NoWingShieldCharge))
            if (WingShield.getWingShield().getCount() < this.secondaryM) {
                cantUseMessage = CardCrawlGame.languagePack.getUIString("VUPShionMod:WingShield").TEXT[2];
                return false;
            }

        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(2);
            upgradeSecondM(-1);
        }
    }
}
