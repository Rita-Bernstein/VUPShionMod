package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.PsychicPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SpiritImpact extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(SpiritImpact.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public SpiritImpact() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 10;
        this.magicNumber = this.baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int combo = 0;
        int tureDamage = this.baseDamage;
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power.ID.equals(PsychicPower.POWER_ID)) {
                combo = power.amount;
            }
        }

        this.baseDamage += combo * this.magicNumber;
        calculateCardDamage(m);
        this.baseDamage = tureDamage;

        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        addToBot(new ApplyPowerAction(p, p, new PsychicPower(p, 1)));
    }

    @Override
    public void applyPowers() {
        int combo = 0;
        int tureDamage = this.baseDamage;
        for (AbstractPower power : AbstractDungeon.player.powers) {
            if (power.ID.equals(PsychicPower.POWER_ID)) {
                combo = power.amount;
            }
        }

        this.baseDamage += combo * this.magicNumber;
        super.applyPowers();

        this.baseDamage = tureDamage;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(2);
        }
    }
}