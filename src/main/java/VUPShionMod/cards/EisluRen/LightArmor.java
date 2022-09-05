package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.stances.LightArmorStance;
import VUPShionMod.stances.RuinGuardianStance;
import VUPShionMod.ui.WingShield;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LightArmor extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(LightArmor.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/LightArmor.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public LightArmor() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.selfRetain =true;
        this.secondaryM = this.baseSecondaryM = 2;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!hasTag(CardTagsEnum.NoWingShieldCharge) && !upgraded)
            addToBot(new LoseWingShieldAction(this.secondaryM));
        addToBot(new ChangeStanceAction(LightArmorStance.STANCE_ID));
    }



    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!hasTag(CardTagsEnum.NoWingShieldCharge))
            if (WingShield.getWingShield().getCount() < this.secondaryM && !upgraded) {
                cantUseMessage = CardCrawlGame.languagePack.getUIString("VUPShionMod:WingShield").TEXT[2];
                return false;
            }

        return super.canUse(p, m);
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
