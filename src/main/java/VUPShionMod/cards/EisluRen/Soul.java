package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.EisluRen.AttackIncreasePower;
import VUPShionMod.powers.EisluRen.TurnAttackIncreasePower;
import VUPShionMod.ui.WingShield;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Soul extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(Soul.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ReleaseFormEisluRen.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public Soul() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber =this.baseMagicNumber = 300;
        this.secondaryM = this.baseSecondaryM = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!hasTag(CardTagsEnum.NoWingShieldCharge))
        addToBot(new LoseWingShieldAction(this.secondaryM));

        addToBot(new SFXAction("STANCE_ENTER_WRATH"));
        addToBot(new ApplyPowerAction(p,p,new TurnAttackIncreasePower(p,this.magicNumber)));
        addToBot(new GainEnergyAction(3));
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
            upgradeMagicNumber(100);
        }
    }
}
