package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.LoseSansAction;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import VUPShionMod.ui.SansMeter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class EmanationOfIre extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(EmanationOfIre.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/EmanationOfIre.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public EmanationOfIre() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 7;
        this.magicNumber = this.baseMagicNumber = 99;
        this.secondaryM = this.baseSecondaryM =2;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseSansAction(this.secondaryM));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false)));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (p.hasPower(PsychicPower.POWER_ID)) {
                    addToTop(new ReducePowerAction(p, p, PsychicPower.POWER_ID, 1));
                    addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
                }
                isDone = true;
            }
        });

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (SansMeter.getSans().amount < this.secondaryM) {
            cantUseMessage = CardCrawlGame.languagePack.getUIString("VUPShionMod:SansMeter").TEXT[5];
            return false;
        }

        return super.canUse(p, m);
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(7);
        }
    }
}