package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.ui.WingShield;
import VUPShionMod.vfx.EisluRen.FinalFlashBlastEffect;
import VUPShionMod.vfx.Monster.OuroborosLaserBeamEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FinalFlash extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(FinalFlash.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/FinalFlash.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public FinalFlash() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 3;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!hasTag(CardTagsEnum.NoWingShieldCharge))
            addToBot(new LoseWingShieldAction(WingShield.getWingShield().getCount()));
        addToBot(new VFXAction(new FinalFlashBlastEffect(p.dialogX, p.dialogY, p.flipHorizontal),0.0f));

        for (int i = 0; i < WingShield.getWingShield().getCount() * 3; i++) {
            addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE,true));
        }
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (WingShield.getWingShield().getCount() <= 10) {
            cantUseMessage = CardCrawlGame.languagePack.getUIString("VUPShionMod:WingShield").TEXT[2];
            return false;
        }

        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(5);
        }
    }
}
