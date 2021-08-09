package VUPShionMod.cards.shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.AbstractShionCard;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class SpeedShot extends AbstractShionCard {
    public static final String ID = VUPShionMod.makeID("SpeedShot");
    public static final String IMG =  VUPShionMod.assetPath("img/cards/shion/zy11.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = -1;

    public SpeedShot() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.tags.add(CardTagsEnum.FIN_FUNNEL);
        this.baseDamage = 0;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
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
            AbstractFinFunnel funnel = AbstractPlayerPatches.AddFields.activatedFinFunnel.get(p);
            for(int i = 0; i < effect; i++) {
                if (funnel != null) {
                    funnel.fire(m, this.damage, this.damageTypeForTurn);
                } else {
                    this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
                }
            }
            if (!this.freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += Math.ceil(VUPShionMod.calculateTotalFinFunnelLevel() / 2.0);
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseDamage += Math.ceil(VUPShionMod.calculateTotalFinFunnelLevel() / 2.0);
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }
}
