package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.ApplyStiffnessAction;
import VUPShionMod.actions.Wangchuan.LoseCorGladiiAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MentalMotivationChop extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(MentalMotivationChop.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/" + MentalMotivationChop.class.getSimpleName() + ".png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public MentalMotivationChop() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 1;

        this.tags.add(CardTagsEnum.MagiamObruor_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                for (int i = 0; i < 4; i++) {
                    this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
                }
                addToBot(new LoseCorGladiiAction(true));

                break;
            case 2:
                int amount = 0;
                if (p.hasPower(MagiamObruorPower.POWER_ID)) {
                    amount = p.getPower(MagiamObruorPower.POWER_ID).amount;
                }
                for (int i = 0; i < amount; i++) {
                    this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.LIGHTNING));
                }
                break;
        }
        addToBot(new RemoveSpecificPowerAction(p, p, MagiamObruorPower.POWER_ID));
        addToBot(new ApplyStiffnessAction(1));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int trueDamage = this.baseDamage;

        this.baseDamage = 1;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            this.baseDamage += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;

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
                this.selfRetain = true;
            }

            if (this.timesUpgraded == 2) {
            }
        }
    }
}
