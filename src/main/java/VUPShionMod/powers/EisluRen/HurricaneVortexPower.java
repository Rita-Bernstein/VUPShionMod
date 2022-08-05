package VUPShionMod.powers.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ApplyPowerToAllEnemyAction;
import VUPShionMod.powers.AbstractShionPower;
import VUPShionMod.powers.Shion.PursuitPower;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.function.Supplier;

public class HurricaneVortexPower extends AbstractShionPower implements CloneablePowerInterface {
    public static final String POWER_ID = VUPShionMod.makeID(HurricaneVortexPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public HurricaneVortexPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.owner = owner;
        this.ID = POWER_ID;
        this.amount = amount;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/CircuitPower128.png")), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(VUPShionMod.assetPath("img/powers/CircuitPower32.png")), 0, 0, 32, 32);
        updateDescription();
        this.isTurnBased = true;

    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], amount);
    }


    @Override
    public void atStartOfTurn() {
        flash();
        int damage = 7;
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            damage += AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
        }
        addToBot(new DamageAllEnemiesAction(this.owner, DamageInfo.createDamageMatrix(damage, false),
                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE, true));

        Supplier<AbstractPower> powerToApply = () -> new WeakPower(null, 1, false);
        addToBot(new ApplyPowerToAllEnemyAction(powerToApply));
    }


    @Override
    public AbstractPower makeCopy() {
        return new HurricaneVortexPower(this.owner, amount);
    }
}
