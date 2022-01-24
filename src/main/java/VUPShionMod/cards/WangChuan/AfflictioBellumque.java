package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.ApplyPowerToAllEnemyAction;
import VUPShionMod.powers.MagiamObruorPower;
import VUPShionMod.powers.StiffnessPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import java.util.function.Supplier;

public class AfflictioBellumque extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("AfflictioBellumque");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/PlaceHolder.png");  // todo
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public AfflictioBellumque() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 17;
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = 1;
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));

        Supplier<AbstractPower> powerToApply = () -> new VulnerablePower(null, this.magicNumber, false);
        addToBot(new ApplyPowerToAllEnemyAction(powerToApply));

        addToBot(new ApplyPowerAction(p, p, new MagiamObruorPower(p, 1)));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
        }
    }
}
