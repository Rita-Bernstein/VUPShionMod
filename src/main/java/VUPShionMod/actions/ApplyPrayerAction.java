package VUPShionMod.actions;

import VUPShionMod.patches.AbstractPrayerPatches;
import VUPShionMod.prayers.AbstractPrayer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Collections;

public class ApplyPrayerAction extends AbstractGameAction {
    private AbstractPrayer prayerToApply;
    private float startingDuration;
    private boolean justStart = true;

    public ApplyPrayerAction(AbstractPrayer prayerToApply) {
        this.prayerToApply = prayerToApply;

        if (Settings.FAST_MODE) {
            this.startingDuration = 0.1F;
        } else {
            this.startingDuration = Settings.ACTION_DUR_FAST;
        }

        this.duration = this.startingDuration;

        this.actionType = ActionType.POWER;

    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.duration = 0.0F;
            this.startingDuration = 0.0F;
            this.isDone = true;
            return;
        }

        if (justStart) {
            justStart = false;

//            if (this.source != null) {
//                for (AbstractPower pow : this.source.powers) {
//                    pow.onApplyPower(this.prayerToApply, this.target, this.source);
//                }
//            }


//            if (AbstractDungeon.player.hasRelic("Champion Belt") && this.source != null && this.source.isPlayer && this.target != this.source && this.prayerToApply.ID
//                    .equals("Vulnerable") && !this.target.hasPower("Artifact")) {
//                AbstractDungeon.player.getRelic("Champion Belt").onTrigger(this.target);
//            }


//            if (AbstractDungeon.player.hasRelic("Turnip") && this.target.isPlayer && this.prayerToApply.ID.equals("Frail")) {
//
//                AbstractDungeon.player.getRelic("Turnip").flash();
//                addToTop(new TextAboveCreatureAction(this.target, TEXT[1]));
//                this.duration -= Gdx.graphics.getDeltaTime();
//
//
//                return;
//            }

//            if (this.target.hasPower("Artifact") &&
//                    this.prayerToApply.type == AbstractPower.PowerType.DEBUFF) {
//                addToTop(new TextAboveCreatureAction(this.target, TEXT[0]));
//                this.duration -= Gdx.graphics.getDeltaTime();
//                CardCrawlGame.sound.play("NULLIFY_SFX");
//                this.target.getPower("Artifact").flashWithoutSound();
//                this.target.getPower("Artifact").onSpecificTrigger();
//
//
//                return;
//            }


//            boolean hasBuffAlready = false;
//            for (AbstractPrayer p : AbstractPrayerPatches.prayers) {
//                if (p.ID.equals(this.prayerToApply.ID) && !p.ID.equals("Night Terror")) {
//                    p.flash();
//
//                    if ((p instanceof com.megacrit.cardcrawl.powers.StrengthPower || p instanceof com.megacrit.cardcrawl.powers.DexterityPower) && this.amount <= 0) {
//                        AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.prayerToApply.name + TEXT[3]));
//
//
//                    } else if (this.amount > 0) {
//                        if (p.type == AbstractPower.PowerType.BUFF || p instanceof com.megacrit.cardcrawl.powers.StrengthPower || p instanceof com.megacrit.cardcrawl.powers.DexterityPower) {
//                            AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, "+" +
//
//
//                                    Integer.toString(this.amount) + " " + this.prayerToApply.name));
//                        } else {
//                            AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, "+" +
//
//
//                                    Integer.toString(this.amount) + " " + this.prayerToApply.name));
//                        }
//
//                    } else if (p.type == AbstractPower.PowerType.BUFF) {
//                        AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.prayerToApply.name + TEXT[3]));
//
//                    } else {
//
//
//                        AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.prayerToApply.name + TEXT[3]));
//                    }
//
//
//                    p.updateDescription();
//                    hasBuffAlready = true;
//                    AbstractDungeon.onModifyPower();
//                }
//            }

//            if (this.prayerToApply.type == AbstractPower.PowerType.DEBUFF) {
//                this.target.useFastShakeAnimation(0.5F);
//            }

                AbstractPrayerPatches.prayers.add(this.prayerToApply);
                Collections.sort(AbstractPrayerPatches.prayers);
                this.prayerToApply.onInitialApplication();
                this.prayerToApply.flash();

//                if (this.amount < 0 && (this.prayerToApply.ID.equals("Strength") || this.prayerToApply.ID.equals("Dexterity") ||
//                        this.prayerToApply.ID.equals("Focus"))) {
//                    AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.prayerToApply.name + TEXT[3]));
//
//
//                } else if (this.prayerToApply.type == AbstractPower.PowerType.BUFF) {
//                    AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.prayerToApply.name));
//
//                } else {
//
//
//                    AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.prayerToApply.name));
//                }


//                AbstractDungeon.onModifyPower();

        }

        tickDuration();
    }
}
