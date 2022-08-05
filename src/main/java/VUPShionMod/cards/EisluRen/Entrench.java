package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.ui.WingShield;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Entrench extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(Entrench.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ReleaseFormEisluRen.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public Entrench() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.secondaryM = this.baseSecondaryM = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseWingShieldAction(this.secondaryM));
        addToBot(new RemoveAllBlockAction(p, p));
        addToBot(new GainShieldAction(p, p.currentBlock * (this.upgraded ? 3 : 2)));


    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
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
            upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
