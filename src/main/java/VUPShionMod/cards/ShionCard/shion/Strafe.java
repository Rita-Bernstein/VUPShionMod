package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Strafe extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("Strafe");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy15.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public Strafe() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.tags.add(CardTagsEnum.FIN_FUNNEL);
        this.baseDamage = 0;
        this.baseMagicNumber = this.magicNumber = 1;
        loadJokeCardImage(VUPShionMod.assetPath("img/cards/ShionCard/joke/zy15.png"));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseDamage = AbstractFinFunnel.calculateTotalFinFunnelLevel();
        calculateCardDamage(m);
        AbstractFinFunnel funnel =AbstractPlayerPatches.AddFields.finFunnelManager.get(p).selectedFinFunnel;

            if (funnel != null) {
                funnel.activeFire(m, this.damage, this.damageTypeForTurn,this.magicNumber);
            } else {
                for (int i = 0; i < this.magicNumber; i++)
                this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
            }


        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }


    @Override
    public void applyPowers() {
        this.baseDamage = AbstractFinFunnel.calculateTotalFinFunnelLevel();
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
}
