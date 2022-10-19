package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.PlayerMinionTakeTurnAction;
import VUPShionMod.msic.Shield;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

public class WindArrow extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(WindArrow.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/WindArrow.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public WindArrow() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int chance = this.upgraded ? 70 : 50;
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            chance += AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
        }

        if (chance > 0)
            if (AbstractDungeon.cardRng.random(99) <= chance) {
                if (m != null && m.hb != null)
                    addToBot(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));

                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
                this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
                initializeDescription();

                if (AbstractDungeon.cardRng.random(99) < (this.upgraded ? 70 : 50)) {
                    addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -this.magicNumber)));
                }
            }

        addToBot(new PlayerMinionTakeTurnAction());
    }

    public void applyPowers() {
        this.baseDamage = 12;
        AbstractPower p = AbstractDungeon.player.getPower(DexterityPower.POWER_ID);
        if (p != null) this.baseDamage += p.amount;

        super.applyPowers();
        this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        this.rawDescription += EXTENDED_DESCRIPTION[0];
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
