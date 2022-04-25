package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.Calamity2Power;
import VUPShionMod.powers.CalamityPower;
import VUPShionMod.powers.LoseHPPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BerserkPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Calamity extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(Calamity.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/lyz09.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public Calamity() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.isInnate = true;
        vupCardSetBanner(CardRarity.RARE, CardType.POWER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 5)));
        addToBot(new ApplyPowerAction(p, p, new BerserkPower(p, 2)));
        addToBot(new ApplyPowerAction(p, p, new LoseHPPower(p, 20)));
        addToBot(new ApplyPowerAction(p, p, new CalamityPower(p, 2)));
        addToBot(new ApplyPowerAction(p, p, new Calamity2Power(p)));
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        if (EnergyPanelPatches.PatchEnergyPanelField.canUseSans.get(AbstractDungeon.overlayMenu.energyPanel)) {
            EnergyPanelPatches.PatchEnergyPanelField.sans.get(AbstractDungeon.overlayMenu.energyPanel).loseSan(10);
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}