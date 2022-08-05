package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.ui.WingShield;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Chopper extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(Chopper.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/ReleaseFormEisluRen.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public Chopper(int upgrade) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 10;
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 2;
        this.isMultiDamage = true;
        this.timesUpgraded = upgrade;
    }

    public Chopper() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseWingShieldAction(this.secondaryM));

        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.SLASH_HEAVY, true));
        }
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
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();

        upgradeMagicNumber(1);
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Chopper(this.timesUpgraded);
    }
}
