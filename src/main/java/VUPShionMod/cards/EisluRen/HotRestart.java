package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.AddWingShieldDamageReduceAction;
import VUPShionMod.actions.EisluRen.GainRefundChargeAction;
import VUPShionMod.powers.EisluRen.HotRestartPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class HotRestart extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(HotRestart.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/HotRestart.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public HotRestart() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded)
            addToBot(new GainRefundChargeAction(this.magicNumber));

        int drawCard = 0;
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            drawCard += AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount / 5;
        }

        addToBot(new DrawCardAction(drawCard));
        addToBot(new AddWingShieldDamageReduceAction(drawCard * 2));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
