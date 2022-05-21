package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class EdgeOfSquall extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(EdgeOfSquall.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/EdgeOfSquall.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public EdgeOfSquall() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 3;
        this.isMultiDamage = true;
        this.cardsToPreview = new RipsoulShrilling();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (p.hasPower(PsychicPower.POWER_ID)) {
                    addToTop(new ReducePowerAction(p, p, PsychicPower.POWER_ID, 1));
                    addToTop(new MakeTempCardInHandAction(new RipsoulShrilling(), 2));
                } else
                    addToTop(new MakeTempCardInHandAction(new RipsoulShrilling()));
                isDone = true;
            }
        });

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(5);
        }
    }
}