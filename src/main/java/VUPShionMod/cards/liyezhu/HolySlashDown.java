package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.GainHyperdimensionalLinksAction;
import VUPShionMod.cards.AbstractLiyezhuCard;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import VUPShionMod.powers.HolySlashDownPower;
import VUPShionMod.powers.HyperdimensionalLinksPower;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HolySlashDown extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID("HolySlashDown");
    public static final String IMG = VUPShionMod.assetPath("img/cards/liyezhu/lyz12.png");
    private static final int COST = 2;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public HolySlashDown(int upgrades) {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 7;
        this.magicNumber = this.baseMagicNumber = 1;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    public HolySlashDown() {
        this(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseDamage = 7;
        int amount = 0;
        if (p.hasPower(HyperdimensionalLinksPower.POWER_ID))
            amount = p.getPower(HyperdimensionalLinksPower.POWER_ID).amount;
        this.baseDamage += amount;

        calculateCardDamage(m);

        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 020 Slash1 Ray Right", m.hb.cX, m.hb.cY,
                    125.0f, 125.0f, 2.0f * Settings.scale, 2, false)));
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }

        addToBot(new GainHyperdimensionalLinksAction(this.secondaryM));
//        addToBot(new ApplyPowerAction(p, p, new HyperdimensionalLinksPower(p, this.secondaryM)));

        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void applyPowers() {
        this.baseDamage = 7;
        int amount = 0;
        if (AbstractDungeon.player.hasPower(HyperdimensionalLinksPower.POWER_ID))
            amount = AbstractDungeon.player.getPower(HyperdimensionalLinksPower.POWER_ID).amount * this.magicNumber;
        this.baseDamage += amount;
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
    }

    public AbstractCard makeCopy() {
        return new HolySlashDown(this.timesUpgraded);
    }

    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        this.upgradeMagicNumber(1);
        this.upgradeSecondM(1);
        this.timesUpgraded++;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
    }
}
