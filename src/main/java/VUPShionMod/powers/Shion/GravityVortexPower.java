package VUPShionMod.powers.Shion;


import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ApplyPowerToAllEnemyAction;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.InvestigationFinFunnel;
import VUPShionMod.powers.AbstractShionPower;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import java.util.function.Supplier;

public class GravityVortexPower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID(GravityVortexPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public GravityVortexPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/AttackOrderPower128.png"), 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("VUPShionMod/img/powers/AttackOrderPower48.png"), 0, 0, 48, 48);

        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        if (this.owner.hasPower(AoeAnalysisPower.POWER_ID))
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, AoeAnalysisPower.POWER_ID));

        if (this.owner.hasPower(ChainPursuitPower.POWER_ID))
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, ChainPursuitPower.POWER_ID));

        if (this.owner.hasPower(StrikeIntegratedPower.POWER_ID))
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, StrikeIntegratedPower.POWER_ID));

        if (this.owner.hasPower(MatrixAmplifyPower.POWER_ID))
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, MatrixAmplifyPower.POWER_ID));
    }

    @Override
    public void onTriggerFinFunnel(AbstractFinFunnel finFunnel, AbstractCreature target) {
        int count = 0;
        if (finFunnel.id.equals(GravityFinFunnel.ID)) {
            flash();
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                    if (monster != null && !monster.isDeadOrEscaped()) {
                        count++;
                    }
                }
            }

        }

        if (count > 0) {
            flash();
            addToBot(new GainBlockAction(this.owner, count * 6));
        }
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
