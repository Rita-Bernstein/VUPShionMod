package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.CorGladiiPower;
import VUPShionMod.powers.StiffnessPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VertexGladii extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("VertexGladii");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc09.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 2;

    public VertexGladii() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseBlock = 10;
        this.magicNumber =this.baseMagicNumber =5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int d = this.magicNumber;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            d += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        this.baseDamage = d;

        calculateCardDamage(m);

        if (this.upgraded)
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));


        addToBot(new GainBlockAction(p, p, this.block));

        if (upgraded)
            addToBot(new ApplyPowerAction(p, p, new CorGladiiPower(p, 2)));
        else
            addToBot(new ReducePowerAction(p, p, CorGladiiPower.POWER_ID, 1));

        if(StiffnessPower.applyStiffness())
        addToBot(new ApplyPowerAction(p, p, new StiffnessPower(p, upgraded ? 1 : 2)));


        this.rawDescription = upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        initializeDescription();
    }


    public void applyPowers() {
        int d = this.magicNumber;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            d = AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        this.baseDamage = d;


        int b = 10;
        if (upgraded) {
            b = 9;
            if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
                b += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        }

        this.baseBlock = b;

        super.applyPowers();

        this.rawDescription = upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        this.rawDescription += EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }


    public void onMoveToDiscard() {
        this.rawDescription = upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        initializeDescription();
    }


    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        this.rawDescription += EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(2);
            upgradeBaseCost(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.name = EXTENDED_DESCRIPTION[0];
            this.initializeTitle();
            this.initializeDescription();
        }
    }
}
