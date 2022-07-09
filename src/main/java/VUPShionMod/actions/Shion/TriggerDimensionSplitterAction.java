package VUPShionMod.actions.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.relics.Shion.DimensionSplitterAria;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class TriggerDimensionSplitterAction extends AbstractGameAction {
    private boolean justStart = true;

    public TriggerDimensionSplitterAction() {
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }

        if (this.justStart) {
            this.justStart = false;

            CardCrawlGame.sound.play("ATTACK_IRON_2",-0.5F);
            for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                if (monster != null && !monster.isDeadOrEscaped()) {

                    AbstractDungeon.effectList.add(new AbstractAtlasGameEffect("Fire 071 Ray Shot Up MIX", monster.hb.cX, monster.hb.y + 550.f * Settings.scale,
                            130.0f, 213.0f, 3.0f * Settings.scale, 2, false));
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(monster.hb.cX, monster.hb.cY, AttackEffect.FIRE));
                }
            }
        }

        tickDuration();

        if (this.isDone) {
            int fatal = 0;

            for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                if (monster != null && !monster.isDeadOrEscaped()) {
                    monster.damage(new DamageInfo(null, monster.maxHealth / 10 + AbstractFinFunnel.calculateTotalFinFunnelLevel(),
                            DamageInfo.DamageType.THORNS));
                    if (monster.isDeadOrEscaped() && !monster.hasPower(MinionPower.POWER_ID)) {
                        fatal++;

                    }
                }
            }

            if(fatal>0){
                addToTop(new GainEnergyAction(1));
                addToTop(new TriggerDimensionSplitterAction());
            }

        }
    }
}
