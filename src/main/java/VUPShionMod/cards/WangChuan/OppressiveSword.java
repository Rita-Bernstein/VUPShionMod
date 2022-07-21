package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.XActionAction;
import VUPShionMod.actions.Wangchuan.ApplyStiffnessAction;
import VUPShionMod.powers.Wangchuan.*;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.function.Consumer;

public class OppressiveSword extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(OppressiveSword.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/" + OppressiveSword.class.getSimpleName() + ".png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public OppressiveSword() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (p.hasPower(StiffnessPower.POWER_ID)) {
                            this.amount = p.getPower(StiffnessPower.POWER_ID).amount;
                            addToTop(new RemoveSpecificPowerAction(p, p, StiffnessPower.POWER_ID));
                            addToTop(new ApplyPowerAction(p, p, new CorGladiiPower(p, this.amount * 4)));
                        }
                        isDone = true;
                    }
                });
                addToBot(new ApplyStiffnessAction(1));
                break;
            case 2:
                if (m != null)
                    addToBot(new VFXAction(new AbstractAtlasGameEffect("Black Line", m.hb.cX, m.hb.y + 700.0f * Settings.scale,
                            50.0f, 90.0f, 8.0f * Settings.scale, 2, false)));

                Consumer<Integer> actionConsumer = effect -> {
                    addToTop(new ApplyPowerAction(p, p, new MagiamObruorPower(p, effect)));
                    addToTop(new ApplyPowerAction(p, p, new CorGladiiPower(p, 5 * effect)));
                    baseDamage = this.magicNumber * effect;
                    calculateCardDamage(m);
                    addToTop(new DamageAction(m, new DamageInfo(p, this.damage * effect, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
                };
                addToBot(new XActionAction(actionConsumer, this.freeToPlayOnce, this.energyOnUse));
//                addToBot(new ApplyPowerAction(p, p, new OppressiveSwordPower(p, 1)));
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
                            addToTop(new ApplyPowerAction(p, p, new CorGladiiPower(p, (int) Math.floor(AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount * 0.4))));
                        isDone = true;
                    }
                });
                break;
        }


    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int trueDamage = this.baseDamage;

        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            this.baseDamage = AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount * this.magicNumber;

        super.calculateCardDamage(mo);

        this.baseDamage = trueDamage;
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (this.timesUpgraded >= 2) {
            if (!(super.canUse(p, m) && EnergyPanel.totalCount > 0)) {
                this.cantUseMessage = EXTENDED_DESCRIPTION[3];
                return false;
            }
            return true;
        } else
            return super.canUse(p, m);
    }


    @Override
    public boolean canUpgrade() {
        return timesUpgraded <= 1;
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
                upgradeMagicNumber(1);
            }

            if (this.timesUpgraded == 2) {
                upgradeBaseCost(-1);

                this.exhaust = false;
            }
        }
    }
}
