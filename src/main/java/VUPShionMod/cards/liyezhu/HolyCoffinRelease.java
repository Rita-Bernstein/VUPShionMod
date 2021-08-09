package VUPShionMod.cards.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractLiyezhuCard;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.BadgeOfThePaleBlueCrossPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class HolyCoffinRelease extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID("HolyCoffinRelease");
    public static final String IMG = VUPShionMod.assetPath("img/cards/liyezhu/lyz14.png");
    private static final int COST = -1;
    public static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public HolyCoffinRelease() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.baseDamage = 0;
        this.baseMagicNumber = 1;
        this.isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        if (p.hasRelic(ChemicalX.ID)) {
            effect += ChemicalX.BOOST;
            p.getRelic(ChemicalX.ID).flash();
        }
        if (this.upgraded) {
            effect++;
        }
        if (effect > 0) {
            addToBot(new DrawCardAction(effect, new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractCard card : DrawCardAction.drawnCards) {
                        if (AbstractDungeon.cardRng.randomBoolean()) {
                            card.setCostForTurn(0);
                        }
                    }
                    int dmg = 0;
                    if (p.hasPower(BadgeOfThePaleBlueCrossPower.POWER_ID)) {
                        dmg += p.getPower(BadgeOfThePaleBlueCrossPower.POWER_ID).amount;
                    }
                    addToBot(new ApplyPowerAction(p, p, new BadgeOfThePaleBlueCrossPower(p, DrawCardAction.drawnCards.size())));
                    HolyCoffinRelease.this.baseDamage = (dmg + DrawCardAction.drawnCards.size()) * HolyCoffinRelease.this.baseMagicNumber;
                    HolyCoffinRelease.this.calculateCardDamage(null);
                    addToBot(new DamageAllEnemiesAction(p, HolyCoffinRelease.this.multiDamage, HolyCoffinRelease.this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
                    addToBot(new RemoveSpecificPowerAction(p, p, BadgeOfThePaleBlueCrossPower.POWER_ID));
                    isDone = true;
                }
            }));
            if (!this.freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
    }
}
