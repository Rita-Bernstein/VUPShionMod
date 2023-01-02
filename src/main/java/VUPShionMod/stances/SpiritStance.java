package VUPShionMod.stances;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.GainShieldAction;
import VUPShionMod.actions.Liyezhu.DuelSinAction;
import VUPShionMod.character.Liyezhu;
import VUPShionMod.patches.AbstractPlayerEnum;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.CombustPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;

public class SpiritStance extends AbstractVUPShionStance {
    public static final String STANCE_ID = VUPShionMod.makeID(SpiritStance.class.getSimpleName());
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);


    private static long sfxId = -1L;

    public SpiritStance() {
        this.ID = STANCE_ID;// 21
        this.name = stanceString.NAME;
        updateDescription();
    }

    @Override
    public void updateAnimation() {
        if (!(AbstractDungeon.player.chosenClass == AbstractPlayerEnum.Liyezhu)) {
//            if (!Settings.DISABLE_EFFECTS) {
//
//                this.particleTimer -= Gdx.graphics.getDeltaTime();
//                if (this.particleTimer < 0.0F) {
//                    this.particleTimer = 0.04F;
//                    AbstractDungeon.effectsQueue.add(new DefensiveModeStanceParticleEffect(new Color(1.0F, 0.9F, 0.7F, 0.0F)));
//                }
//            }
//
//
//            this.particleTimer2 -= Gdx.graphics.getDeltaTime();
//            if (this.particleTimer2 < 0.0F) {
//                this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
//                AbstractDungeon.effectsQueue.add(new StanceAuraEffect("DefensiveMode"));
//            }
        }
    }


    @Override
    public void onEnterStance() {
        if (sfxId != -1L) {
            stopIdleSfx();
        }

        CardCrawlGame.sound.play("STANCE_ENTER_DIVINITY");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.PINK, true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Divinity"));

    }


    @Override
    public void onExitStance() {
        stopIdleSfx();

    }

    public void stopIdleSfx() {
        if (sfxId != -1L) {
            sfxId = -1L;
        }
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            AbstractDungeon.actionManager.addToBottom(new DuelSinAction());
        }
    }

    @Override
    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
        addToTop(new GainShieldAction(AbstractDungeon.player, AbstractDungeon.player.lastDamageTaken / 2));
    }

    @Override
    public void onVictory() {
        AbstractDungeon.player.increaseMaxHp(5, true);
    }


    @Override
    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
    }
}
