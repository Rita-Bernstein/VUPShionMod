package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.DuelSinAction;
import VUPShionMod.actions.Liyezhu.FlayTheEvilAction;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import VUPShionMod.powers.Liyezhu.SinPower;
import VUPShionMod.powers.Liyezhu.SwearPower;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.vfx.EisluRen.FinalFlashBlastEffect;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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

    private static final int COST = 2;

    public LiXiaoNan() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.magicNumber = 6;
        this.selfRetain = true;
        this.exhaust = true;
        GraveField.grave.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SwearPower(p, 1)));
        addToBot(new VFXAction(new FinalFlashBlastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.0f));
        calculateCardDamage(m);


        addToBot(new LoseHPAction(p, p, p.maxHealth / 2));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));

        for (int i = 0; i <  this.magicNumber; i++)
            addToBot(new DuelSinAction());
        this.rawDescription = DESCRIPTION;
        initializeDescription();

    }


    public void applyPowers() {
        int tureBaseDamage = this.baseDamage;

        this.baseDamage = AbstractDungeon.player.maxHealth;

        super.applyPowers();
        this.rawDescription = DESCRIPTION;
        this.rawDescription += EXTENDED_DESCRIPTION[0];
        initializeDescription();
        this.baseDamage = tureBaseDamage;
    }


    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }


    public void calculateCardDamage(AbstractMonster mo) {
        int tureBaseDamage = this.baseDamage;

        this.baseDamage = AbstractDungeon.player.maxHealth;
        super.calculateCardDamage(mo);

        this.rawDescription = DESCRIPTION;
        this.rawDescription += EXTENDED_DESCRIPTION[0];
        initializeDescription();

        this.baseDamage = tureBaseDamage;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
            initializeDescription();
        }
    }
}