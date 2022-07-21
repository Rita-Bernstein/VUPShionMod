package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.MotherRosarioAction;
import VUPShionMod.actions.Wangchuan.ApplyStiffnessAction;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class MotherRosario extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(MotherRosario.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/" + MotherRosario.class.getSimpleName() + ".png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public MotherRosario() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.secondaryM; i++)
            addToBot(new MotherRosarioAction(this));

        switch (this.timesUpgraded) {
            default:
                break;
            case 2:
                int cor = 0;
                int dex = 0;
                if (p.hasPower(CorGladiiPower.POWER_ID)) {
                    cor = p.getPower(CorGladiiPower.POWER_ID).amount;
                }
                if (p.hasPower(DexterityPower.POWER_ID)) {
                    dex = p.getPower(DexterityPower.POWER_ID).amount;
                }

                if (cor + dex > 0) {
                    for (int i = 0; i < 3; i++)
                        addToBot(new DamageAllEnemiesAction(null,
                                DamageInfo.createDamageMatrix(cor + dex, false), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HEAVY));
                }

                break;
        }

        addToBot(new ApplyStiffnessAction(this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new MagiamObruorPower(p, 1)));

    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int trueDamage = this.baseDamage;

        switch (this.timesUpgraded) {
            default:
                this.baseDamage = 4;
                break;
            case 1:
                this.baseDamage = 3;
                break;
            case 2:
                this.baseDamage = 2;
                break;
        }

        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID))
            this.baseDamage += AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;


        super.calculateCardDamage(mo);

        this.baseDamage = trueDamage;
    }

    @Override
    public boolean canUpgrade() {
        return timesUpgraded <= 1;
    }

    @Override
    public void upgrade() {
        if (timesUpgraded <= 1) {
            this.upgraded = true;
            this.name = EXTENDED_DESCRIPTION[timesUpgraded];
            this.initializeTitle();
            if (timesUpgraded < 1)
                this.rawDescription = EXTENDED_DESCRIPTION[2];
            else
                this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.timesUpgraded++;
        }

        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                upgradeSecondM(4);
            }

            if (this.timesUpgraded == 2) {

            }
        }
    }
}
