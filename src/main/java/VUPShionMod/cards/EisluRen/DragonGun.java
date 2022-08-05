package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.powers.Shion.BleedingPower;
import VUPShionMod.powers.Shion.LockOnPower;
import VUPShionMod.ui.WingShield;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;

public class DragonGun extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(DragonGun.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/DragonGun.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public DragonGun() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 3;
        this.magicNumber = this.baseMagicNumber = 5;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LoseWingShieldAction(this.secondaryM));

        addToBot(new ExhaustAction(1, false, this.upgraded, this.upgraded));
        addToBot(new ApplyPowerAction(m,p,new LockOnPower(m,2)));


        if (!Settings.FAST_MODE) {
            addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED), 0.4F));
            for (int i = 0; i < 5; i++) {
                addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
            }
        } else {
            addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED)));
            for (int i = 0; i < 5; i++) {
                addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
            }
        }
        for (int i = 0; i < this.magicNumber; i++){
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (WingShield.getWingShield().getCount() < this.secondaryM) {
            cantUseMessage = CardCrawlGame.languagePack.getUIString("VUPShionMod:WingShield").TEXT[2];
            return false;
        }

        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
