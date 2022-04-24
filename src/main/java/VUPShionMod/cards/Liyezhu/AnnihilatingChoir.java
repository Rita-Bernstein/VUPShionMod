package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.PsychicPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class AnnihilatingChoir extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(AnnihilatingChoir.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 3;

    public AnnihilatingChoir() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 20;
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));

        if(p.hasPower(PsychicPower.POWER_ID)){
            int powerAmount = p.getPower(PsychicPower.POWER_ID).amount;
            if(this.upgraded) {
                this.baseDamage = powerAmount * 15;
                applyPowers();

                addToBot(new SFXAction("ATTACK_HEAVY"));
                addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
                addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));

                addToBot(new GainEnergyAction(powerAmount));

                addToBot(new RemoveSpecificPowerAction(p, p, PsychicPower.POWER_ID));

                addToBot(new ApplyPowerAction(p,p,new PsychicPower(p,powerAmount)));

            }else {
                this.baseDamage = powerAmount * 10;
                applyPowers();

                addToBot(new SFXAction("ATTACK_HEAVY"));
                addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
                addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));

                addToBot(new HealAction(p, p, powerAmount *10));
                addToBot(new GainEnergyAction(powerAmount));

                addToBot(new RemoveSpecificPowerAction(p, p, PsychicPower.POWER_ID));
            }
        }

        this.baseDamage = 20;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}