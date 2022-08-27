package VUPShionMod.actions.Unique;

import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.minions.AbstractPlayerMinion;
import VUPShionMod.minions.MinionGroup;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class BossTriggerDimensionSplitterAction extends AbstractGameAction {
    private boolean justStart = true;
    private AbstractCreature target;

    public BossTriggerDimensionSplitterAction() {
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;
        this.target = AbstractDungeon.player;

        if (!MinionGroup.areMinionsBasicallyDead()) {
            AbstractPlayerMinion minion = MinionGroup.getCurrentMinion();
            this.target = minion;
        }
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }

        if (this.justStart) {
            this.justStart = false;

            CardCrawlGame.sound.play("ATTACK_IRON_2", -0.5F);
            AbstractDungeon.effectList.add(new AbstractAtlasGameEffect("Fire 071 Ray Shot Up MIX", target.hb.cX, target.hb.y + 550.f * Settings.scale,
                    130.0f, 213.0f, 6.0f * Settings.scale, 2, false));
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AttackEffect.FIRE));

        }

        tickDuration();

        if (this.isDone) {
            int fatal = 0;
            target.damage(new DamageInfo(null, target.maxHealth / 2 + 15, DamageInfo.DamageType.THORNS));
        }
    }
}
