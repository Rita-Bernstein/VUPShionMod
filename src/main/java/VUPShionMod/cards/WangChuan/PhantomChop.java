package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.XActionAction;
import VUPShionMod.actions.Wangchuan.ApplyStiffnessAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Wangchuan.CorGladiiPower;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import VUPShionMod.powers.Wangchuan.SpatialTearPower;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.Consumer;

public class PhantomChop extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(PhantomChop.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/" + PhantomChop.class.getSimpleName() + ".png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;

    public PhantomChop() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            default:
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                break;
            case 1:
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                break;
            case 2:
                Consumer<Integer> actionConsumer = effect -> {
                    int amount = 0;

                    if (!AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty())
                        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
                            if (c.type == CardType.ATTACK) amount++;
                        }


                    amount += effect;

                    for (int i = 0; i < amount; i++)
                        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                };
                addToBot(new XActionAction(actionConsumer, this.freeToPlayOnce, this.energyOnUse));
                addToBot(new ApplyStiffnessAction(2));
                break;
        }

    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (!c.purgeOnUse && this.timesUpgraded <= 1 && c.type == CardType.ATTACK) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractCard card = makeStatEquivalentCopy();
                    card.purgeOnUse = true;
                    AbstractDungeon.player.limbo.group.add(card);
                    card.current_y = -200.0F * Settings.scale;
                    card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                    card.target_y = Settings.HEIGHT / 2.0F;
                    card.targetAngle = 0.0F;
                    card.lighten(false);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.applyPowers();
                    addToTop(new NewQueueCardAction(card, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, true));
                    addToTop(new UnlimboAction(card));
                    if (!Settings.FAST_MODE) {
                        addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                    } else {
                        addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                    }
                    isDone = true;
                }
            });

        }
        super.triggerOnOtherCardPlayed(c);
    }


    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int trueDamage = this.baseDamage;

        if (this.timesUpgraded >= 2) {
            this.baseDamage = 0;
        } else {
            this.baseDamage = 1;
        }

        if (AbstractDungeon.player.hasPower(CorGladiiPower.POWER_ID))
            this.baseDamage += AbstractDungeon.player.getPower(CorGladiiPower.POWER_ID).amount;

        super.calculateCardDamage(mo);

        this.baseDamage = trueDamage;
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
                this.selfRetain = true;
            }

            if (this.timesUpgraded == 2) {
                upgradeBaseCost(-1);
                this.selfRetain = true;
            }
        }
    }
}
