package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.PsychicPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class EmanationOfIre extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(EmanationOfIre.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public EmanationOfIre() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 6;
        this.magicNumber = this.baseMagicNumber = 2;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (m != null)
                    if (!m.isDeadOrEscaped() && m.getIntentBaseDmg() < 0) {
                        addToTop(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false)));
                    }
                isDone = true;
            }
        });

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
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(4);
        }
    }
}