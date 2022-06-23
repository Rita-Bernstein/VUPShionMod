package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.DuelSinAction;
import VUPShionMod.actions.Liyezhu.FlayTheEvilAction;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import VUPShionMod.powers.Liyezhu.SinPower;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LiXiaoNan extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(LiXiaoNan.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/LiXiaoNan.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 3;

    public LiXiaoNan() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int tureBaseDamage = this.baseDamage;

        int psy = 0;
        int sin = 0;
        if (AbstractDungeon.player.hasPower(PsychicPower.POWER_ID))
            psy = AbstractDungeon.player.getPower(PsychicPower.POWER_ID).amount;


        if (AbstractDungeon.player.hasPower(SinPower.POWER_ID))
            sin = AbstractDungeon.player.getPower(SinPower.POWER_ID).amount;

        this.baseDamage = (int) Math.floor(Math.max(AbstractDungeon.player.maxHealth / 2, AbstractDungeon.player.currentHealth)
                * (psy + sin) * this.magicNumber * 0.1f);

        calculateCardDamage(m);


        addToBot(new LoseHPAction(p, p, p.maxHealth / 2));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));

        this.baseDamage = tureBaseDamage;
    }


    public void applyPowers() {
        int tureBaseDamage = this.baseDamage;

        int psy = 0;
        int sin = 0;
        if (AbstractDungeon.player.hasPower(PsychicPower.POWER_ID))
            psy = AbstractDungeon.player.getPower(PsychicPower.POWER_ID).amount;


        if (AbstractDungeon.player.hasPower(SinPower.POWER_ID))
            sin = AbstractDungeon.player.getPower(SinPower.POWER_ID).amount;

        this.baseDamage = (int) Math.floor(Math.max(AbstractDungeon.player.maxHealth / 2, AbstractDungeon.player.currentHealth)
                * (psy + sin) * this.magicNumber * 0.1f);

        super.applyPowers();
        this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        this.rawDescription += EXTENDED_DESCRIPTION[0];
        initializeDescription();
        this.baseDamage = tureBaseDamage;
    }


    public void onMoveToDiscard() {
        this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        initializeDescription();
    }


    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        this.rawDescription += EXTENDED_DESCRIPTION[0];
        initializeDescription();
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