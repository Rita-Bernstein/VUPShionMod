package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.actions.EisluRen.GainRefundChargeAction;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;

public class SealResidue extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(SealResidue.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/SealResidue.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public SealResidue() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 21;
        this.secondaryM = this.baseSecondaryM = 9;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2 && AbstractDungeon.actionManager.cardsPlayedThisCombat
                .get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2).type == CardType.POWER) {
            addToBot(new GainShieldAction(p, this.magicNumber));
            addToBot(new GainRefundChargeAction(this.secondaryM));
            return;
        }

        float chance = this.upgraded ? 0.8f : 0.6f;
        if (AbstractDungeon.cardRandomRng.randomBoolean(chance)) {
            addToBot(new GainShieldAction(p, this.magicNumber));
            if (AbstractDungeon.cardRandomRng.randomBoolean(chance))
                addToBot(new GainRefundChargeAction(this.secondaryM));
        }
    }


    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 1 && AbstractDungeon.actionManager.cardsPlayedThisCombat
                .get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1).type == CardType.POWER) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
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
