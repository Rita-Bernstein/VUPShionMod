package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.InvestigationFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.patches.AbstractPlayerPatches;
import VUPShionMod.powers.AttackOrderSpecialPower;
import VUPShionMod.powers.PursuitPower;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerToRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.List;

public class AnastasiaNecklace extends CustomRelic {
    public static final String ID = VUPShionMod.makeID("AnastasiaNecklace");
    public static final String IMG_PATH = "img/relics/AnastasiaNecklace.png";
    private static final String OUTLINE_PATH = "img/relics/outline/AnastasiaNecklace.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    private boolean triggered = false;

    public AnastasiaNecklace() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
        getUpdatedDescription();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void triggerRelic() {
        if (!triggered) {
            triggered = true;
            AbstractDungeon.player.increaseMaxHp(200, true);
            AbstractDungeon.player.energy.energyMaster++;
            if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion) {
                for (AbstractFinFunnel f : AbstractPlayerPatches.AddFields.finFunnelList.get(AbstractDungeon.player)) {
                    f.upgradeLevel(9);
                }
            }
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new AttackOrderSpecialPower(AbstractDungeon.player)));
        }
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BarricadePower(AbstractDungeon.player)));

    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if (this.triggered)
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 200));
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if (triggered && m.hasPower(PursuitPower.POWER_ID)) {
            int amount = (m.getPower(PursuitPower.POWER_ID)).amount;
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                flash();
                addToBot(new RelicAboveCreatureAction(m, this));
                addToBot(new ApplyPowerToRandomEnemyAction(AbstractDungeon.player, new PursuitPower(null, amount), amount, false, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }
}
