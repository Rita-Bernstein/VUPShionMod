package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Wangchuan.ApplyStiffnessAction;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.StiffnessPower;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
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
        this.magicNumber = this.baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int d = this.magicNumber;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            d += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        this.baseDamage = d;

        calculateCardDamage(m);

        if (m != null)
            addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 105 Ray Left Loop", m.hb.cX, m.hb.cY,
                    50.0f, 50.0f, 10.0f * Settings.scale, 2, false)));

        if (this.upgraded)
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));


        addToBot(new GainBlockAction(p, p, this.block));

        if (upgraded)
            addToBot(new ApplyPowerAction(p, p, new CorGladiiPower(p, timesUpgraded >= 2 ? 8 : 5)));


        addToBot(new ApplyStiffnessAction(3));

        addToBot(new DrawCardAction(2));

        this.rawDescription = getDescription(timesUpgraded);
        initializeDescription();
    }


    public void applyPowers() {
        int d = this.magicNumber;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            d += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;
        this.baseDamage = d;


        int b = 5;
        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            b += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;

        this.baseBlock = b;

        super.applyPowers();

        this.rawDescription = getDescription(timesUpgraded);
        this.rawDescription += EXTENDED_DESCRIPTION[3];
        initializeDescription();
    }


    public void onMoveToDiscard() {
        this.rawDescription = getDescription(timesUpgraded);
        initializeDescription();
    }


    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = getDescription(timesUpgraded);
        this.rawDescription += EXTENDED_DESCRIPTION[3];
        initializeDescription();
    }


    @Override
    public boolean canUpgrade() {
        return timesUpgraded <= 1;
    }

    private String getDescription(int times) {
        switch (times) {
            case 1:
                return EXTENDED_DESCRIPTION[2];
            case 2:
                return UPGRADE_DESCRIPTION;
            default:
                return DESCRIPTION;
        }
    }

    @Override
    public void upgrade() {
        if (timesUpgraded <= 1) {
            this.upgraded = true;
            this.name = EXTENDED_DESCRIPTION[timesUpgraded];
            this.initializeTitle();
            if (timesUpgraded < 1)
                this.rawDescription = EXTENDED_DESCRIPTION[2];
            else
                this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.timesUpgraded++;
        }

        if (timesUpgraded <= 2) {
            if (this.timesUpgraded == 1) {
                upgradeMagicNumber(2);
                upgradeBaseCost(3);
            }

            if (this.timesUpgraded == 2) {
                upgradeBaseCost(2);
                upgradeMagicNumber(2);
            }
        }
    }
}
