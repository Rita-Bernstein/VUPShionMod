package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

public class HeliumLuxquePower extends AbstractShionPower {
    public static final String POWER_ID = VUPShionMod.makeID("HeliumLuxquePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public HeliumLuxquePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        loadRegion("demonForm");
        updateDescription();
    }


    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }

    @Override
    public void atStartOfTurn() {
//        addToBot(new SFXAction("ATTACK_HEAVY"));
//        addToBot(new VFXAction(AbstractDungeon.player, new MindblastEffect(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY,AbstractDungeon.player.flipHorizontal), 0.1F));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 010 Charge Impact Up", mo.hb.cX, mo.hb.cY + 720.0f * Settings.scale,
                        50.0f, 90.0f, 10.0f * Settings.scale, 2, false)));
            }
        }

        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(this.amount, true),
                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, HeliumLuxquePower.POWER_ID));
    }
}