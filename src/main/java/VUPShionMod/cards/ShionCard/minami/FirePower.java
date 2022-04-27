package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.LoseFinFunnelUpgradePower;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

public class FirePower extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID("FirePower");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami14.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public FirePower() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = 3;
        this.tags.add(CardTagsEnum.FIN_FUNNEL);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = MathUtils.random(2);
        switch (count){
            case 0:
                addToBot(new SFXAction("SHION_1"));
                break;
            case 1:
                addToBot(new SFXAction("SHION_2"));
                break;
            case 2:
                addToBot(new SFXAction("SHION_6"));
                break;
        }


        this.baseDamage = AbstractFinFunnel.calculateTotalFinFunnelLevel() * this.magicNumber;
        calculateCardDamage(m);
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        this.rawDescription = DESCRIPTION;
        initializeDescription();
        addToBot(new ApplyPowerAction(p, p, new LoseFinFunnelUpgradePower(p, 1), 1));
    }

    @Override
    public void applyPowers() {
        this.baseDamage = AbstractFinFunnel.calculateTotalFinFunnelLevel() * this.magicNumber;
        super.applyPowers();
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new FirePower();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}
