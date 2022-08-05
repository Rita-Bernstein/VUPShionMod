package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.GameStatsPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

public class CounterCannon extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(CounterCannon.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/CounterCannon.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public CounterCannon() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.isMultiDamage = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal)));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE));
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void applyPowers() {
        this.baseDamage = GameStatsPatch.wingShieldDamageReduceThisCombat;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }


    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}
