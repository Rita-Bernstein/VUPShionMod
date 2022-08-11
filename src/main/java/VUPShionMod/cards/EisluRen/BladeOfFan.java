package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.ui.WingShield;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;

public class BladeOfFan extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(BladeOfFan.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/BladeOfFan.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public BladeOfFan() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 3;
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 1;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!hasTag(CardTagsEnum.NoWingShieldCharge))
        addToBot(new LoseWingShieldAction(this.secondaryM));
        for (int i = 0; i < this.magicNumber; i++)
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    int count = 0;
                    int[] finalMultiDamage = multiDamage;
                    if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                            if (monster != null && !monster.isDeadOrEscaped()) {
                                count++;
                            }
                        }
                    }

                    if (count <= 1) {
                        for (int i = 0; i < finalMultiDamage.length; i++) {
                            finalMultiDamage[i] *= 2;
                        }
                    }

                    addToTop(new DamageAllEnemiesAction(p, finalMultiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
                    addToTop(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
                    isDone = true;
                }
            });
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!hasTag(CardTagsEnum.NoWingShieldCharge))
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
        }
    }
}
