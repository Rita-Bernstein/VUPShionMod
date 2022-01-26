package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.ApplyPowerToAllEnemyAction;
import VUPShionMod.actions.XActionAction;
import VUPShionMod.powers.CorGladiiPower;
import VUPShionMod.powers.StiffnessPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ConstrictedPower;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GladiiInfiniti extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("GladiiInfiniti");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc14.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = -1;

    public GladiiInfiniti() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.magicNumber = this.baseMagicNumber = 5;
        this.baseSecondaryM = this.secondaryM = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int d = upgraded ? 5 : 3;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            d += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        this.baseDamage = d;

        calculateCardDamage(m);

        Consumer<Integer> actionConsumer = effect -> {
            for (int i = 0; i < effect + 2; i++)
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            if (upgraded)
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        };

        addToBot(new XActionAction(actionConsumer, this.freeToPlayOnce, this.energyOnUse));

        this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        initializeDescription();

        addToBot(new ApplyPowerAction(p, p, new CorGladiiPower(p, this.magicNumber)));
        if (upgraded)
            addToBot(new ReducePowerAction(p, p, StiffnessPower.POWER_ID, this.secondaryM));
    }


    public void applyPowers() {
        int d = upgraded ? 5 : 3;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            d = AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
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
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
