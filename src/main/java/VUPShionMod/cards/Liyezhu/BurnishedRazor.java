package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.DuelSinAction;
import VUPShionMod.powers.SinPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.watcher.ExpungeVFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BurnishedRazor extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(BurnishedRazor.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 2;

    public BurnishedRazor() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean hasPower = false;

        if (p.hasPower(SinPower.POWER_ID))
            hasPower = true;

        if (hasPower)
            this.baseDamage = p.currentHealth / 3;
        else
            this.baseDamage = p.currentHealth / 4;

        calculateCardDamage(m);
        addToBot(new ExpungeVFXAction(m));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));

        if (!this.upgraded)
            addToBot(new DamageAction(p, new DamageInfo(p, this.damage / 2, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));

        addToBot(new DuelSinAction());

        if(hasPower)
            addToBot(new ReducePowerAction(p,p,SinPower.POWER_ID,1));

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