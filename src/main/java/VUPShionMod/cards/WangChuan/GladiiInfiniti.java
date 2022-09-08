package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.ApplyStiffnessAction;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.IntensaPower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;

public class GladiiInfiniti extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(GladiiInfiniti.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc14.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 6;

    public GladiiInfiniti() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.magicNumber = this.baseMagicNumber = 5;
        this.selfRetain = true;
        this.exhaust =true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new SFXAction("MONSTER_COLLECTOR_DEBUFF"));
        addToBot(new VFXAction(new CollectorCurseEffect(m.hb.cX, m.hb.cY), 2.0F));

        int d = 1;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            d += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        this.baseDamage = d;

        calculateCardDamage(m);

        for (int i = 0; i < 8; i++)
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        initializeDescription();



        addToBot(new ApplyPowerAction(p,p,new IntensaPower(p),0));
        addToBot(new ApplyStiffnessAction(4));

        addToBot(new StunMonsterAction(m, p, 1));
//        addToBot(new MakeTempCardInHandAction(new GladiiInfiniti()));
    }


    public void applyPowers() {
        int d = 1;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            d += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        this.baseDamage = d;
        super.applyPowers();

        this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        this.rawDescription += EXTENDED_DESCRIPTION[0];
        initializeDescription();
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
    public void onRetained() {
        updateCost(-1);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(5);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
