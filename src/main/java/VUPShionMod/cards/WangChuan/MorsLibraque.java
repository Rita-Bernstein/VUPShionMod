package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.ApplyPowerToAllEnemyAction;
import VUPShionMod.actions.CustomWaitAction;
import VUPShionMod.powers.MorsLibraquePower;
import VUPShionMod.powers.NoSkillsPower;
import VUPShionMod.powers.StiffnessPower;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.watcher.EnergyDownPower;

import java.util.function.Supplier;

public class MorsLibraque extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("MorsLibraque");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc33.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public MorsLibraque() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 5;
        this.selfRetain = true;

        vupCardSetBanner(CardRarity.RARE, this.type);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new AbstractAtlasGameEffect("Smoke 037 Radial Transition", Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f,
                96.0f, 54.0f, 10.0f * Settings.scale, 2, false)));
        addToBot(new CustomWaitAction(1.5f));

        addToBot(new ApplyPowerAction(p, p, new EnergyDownPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new NoSkillsPower(p)));
        Supplier<AbstractPower> powerToApply = () -> new MorsLibraquePower(null, this.secondaryM);
        addToBot(new ApplyPowerToAllEnemyAction(powerToApply));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeSecondM(-2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
