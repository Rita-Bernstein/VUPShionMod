package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.LoseHyperdimensionalLinksAction;
import VUPShionMod.cards.ShionCard.AbstractShionLiyezhuCard;
import VUPShionMod.powers.Shion.HyperdimensionalLinksPower;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BlueBlade extends AbstractShionLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(BlueBlade.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/liyezhu/lyz04.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public BlueBlade() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 6;
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseHyperdimensionalLinksAction(this.secondaryM));
        addToBot(new SFXAction("BLUNT_FAST"));
        if (m != null)
            addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 016 Impact Explosion Radial", m.hb.cX, m.hb.cY + 0.0f * Settings.scale,
                    125.0f, 125.0f, 2.0f * Settings.scale, 2, false)));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));


        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {

        if (p.hasPower(HyperdimensionalLinksPower.POWER_ID)) {
            if (p.getPower(HyperdimensionalLinksPower.POWER_ID).amount >= this.secondaryM)
                return super.canUse(p, m);
        }

        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;

    }

    @Override
    public void applyPowers() {
        int trueBaseDamage = this.baseDamage;
        this.isMultiDamage = false;
        super.applyPowers();


        this.isMultiDamage = true;
        int amount = 0;
        if (AbstractDungeon.player.hasPower(HyperdimensionalLinksPower.POWER_ID))
            amount = AbstractDungeon.player.getPower(HyperdimensionalLinksPower.POWER_ID).amount * this.magicNumber;
        this.baseDamage = amount;

        super.applyPowers();
        this.isMultiDamage = false;
        this.baseDamage = trueBaseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int trueBaseDamage = this.baseDamage;
        this.isMultiDamage = false;
        super.calculateCardDamage(mo);


        this.isMultiDamage = true;
        int amount = 0;
        if (AbstractDungeon.player.hasPower(HyperdimensionalLinksPower.POWER_ID))
            amount = AbstractDungeon.player.getPower(HyperdimensionalLinksPower.POWER_ID).amount * this.magicNumber;
        this.baseDamage = amount;

        super.calculateCardDamage(mo);
        this.isMultiDamage = false;
        this.baseDamage = trueBaseDamage;

    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeBaseCost(0);
            this.selfRetain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
