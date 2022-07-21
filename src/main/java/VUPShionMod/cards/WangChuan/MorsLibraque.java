package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ApplyPowerToAllEnemyAction;
import VUPShionMod.actions.Common.CustomWaitAction;
import VUPShionMod.powers.Wangchuan.MorsLibraquePower;
import VUPShionMod.powers.Common.NoSkillsPower;
import VUPShionMod.powers.Wangchuan.TurnObruorPower;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import VUPShionMod.vfx.Common.PortraitWindyPetalEffect;
import VUPShionMod.vfx.Shion.LargPortraitFlashInEffect;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.function.Supplier;

public class MorsLibraque extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(MorsLibraque.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc33.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public MorsLibraque() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondaryM = this.baseSecondaryM = 4;
        this.selfRetain = true;

        GraveField.grave.set(this,true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new AbstractAtlasGameEffect("Smoke 037 Radial Transition", Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f,
                96.0f, 54.0f, 10.0f * Settings.scale, 2, false)));
        addToBot(new VFXAction(new PortraitWindyPetalEffect("MorsLibraque"),1.0f));
        addToBot(new CustomWaitAction(1.5f));

        addToBot(new ApplyPowerAction(p, p, new TurnObruorPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new NoSkillsPower(p)));
        Supplier<AbstractPower> powerToApply = () -> new MorsLibraquePower(null, this.secondaryM);
        addToBot(new ApplyPowerToAllEnemyAction(powerToApply));

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeSecondM(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
