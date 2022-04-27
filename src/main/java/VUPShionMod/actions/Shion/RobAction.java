package VUPShionMod.actions.Shion;


import VUPShionMod.vfx.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class RobAction extends AbstractGameAction {
    private int increaseGold;
    private DamageInfo info;
    private static final float DURATION = 0.1F;

    public RobAction(AbstractCreature target, DamageInfo info, int goldAmount) {
        this.info = info;
        this.setValues(target, info);
        this.increaseGold = goldAmount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    @Override
    public void update() {
        if (this.duration == 0.1F && this.target != null) {
//            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY));
            CardCrawlGame.sound.play("ATTACK_HEAVY");

            AbstractDungeon.effectList.add(new AbstractAtlasGameEffect("Sparks 041 Shot Right", this.target.hb.cX, this.target.hb.cY,
                    212.0f, 255.0f, 1.0f * Settings.scale, 2, false));


            this.target.damage(this.info);
            if (( this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                AbstractDungeon.player.gainGold(this.increaseGold);

                for (int i = 0; i < this.increaseGold; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(this.source, this.target.hb.cX, this.target.hb.cY, this.source.hb.cX, this.source.hb.cY, true));
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }
}
