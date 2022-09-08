package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.ApplySinAction;
import VUPShionMod.actions.Liyezhu.DuelSinAction;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import VUPShionMod.powers.Liyezhu.SinPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class ReapTheSinful extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(ReapTheSinful.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/ReapTheSinful.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public ReapTheSinful() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 9;
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (p.hasPower(SinPower.POWER_ID))
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                addToBot(new ApplySinAction(mo, p.getPower(SinPower.POWER_ID).amount));
            }

        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));


        addToBot(new DuelSinAction());
        if (upgraded)
            addToBot(new DuelSinAction());


        if (p.hasPower(PsychicPower.POWER_ID))
            if (p.getPower(PsychicPower.POWER_ID).amount >= 1) {
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    addToBot(new ApplySinAction(mo, this.magicNumber));
                }
                addToBot(new ApplySinAction(p, this.magicNumber));
                addToBot(new ReducePowerAction(p, p, PsychicPower.POWER_ID, 1));
            }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}