package VUPShionMod.cards.ShionCard.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.ShionCard.AbstractShionCard;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.List;

public class DimensionSplitting extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("DimensionSplitting");
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/shion/zy17.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public DimensionSplitting() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.baseMagicNumber = 2;
        this.selfRetain = true;
        this.isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        List<AbstractCard> cardList = AbstractDungeon.actionManager.cardsPlayedThisCombat;
        int ctr = 0;
        for (AbstractCard card : cardList) {
            if (card.hasTag(CardTagsEnum.FIN_FUNNEL)) {
                ctr++;
            }
        }
        this.baseDamage += ctr * this.baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        List<AbstractCard> cardList = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        int ctr = 0;
        for (AbstractCard card : cardList) {
            if (card.hasTag(CardTagsEnum.FIN_FUNNEL)) {
                ctr++;
            }
        }
        this.baseDamage += ctr * this.baseMagicNumber;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                if (!monster.isDeadOrEscaped()) {
                    addToBot(new SFXAction("ATTACK_IRON_2", -0.5F, true));
                    addToBot(new VFXAction(new AbstractAtlasGameEffect("Fire 071 Ray Shot Up MIX", m.hb.cX, m.hb.y + 550.f * Settings.scale,
                            130.0f, 213.0f, 3.0f * Settings.scale, 2, false)));
                }
            }
        }
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, true));
    }
}
