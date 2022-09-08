package VUPShionMod.stances;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.DuelSinAction;
import VUPShionMod.character.Liyezhu;
import VUPShionMod.patches.AbstractPlayerEnum;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.CombustPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;

public class JudgeStance extends AbstractVUPShionStance {
    public static final String STANCE_ID = VUPShionMod.makeID(JudgeStance.class.getSimpleName());
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);


    private static long sfxId = -1L;

    public JudgeStance() {
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

//        if (!(AbstractDungeon.player.chosenClass == AbstractPlayerEnum.Liyezhu)) {
//            sfxId = CardCrawlGame.sound.playAndLoop(GuardianMod.makeID("STANCE_LOOP_Defensive_Mode"));
//        }

//        AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player,
//                new IntenseZoomEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, false), 0.05F, true));
//        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLDENROD, true));


        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new CombustPower(AbstractDungeon.player, 1, 5), 5));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new StrengthPower(AbstractDungeon.player, 1)));
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        if(card.type == AbstractCard.CardType.ATTACK){
            AbstractDungeon.actionManager.addToBottom(new DuelSinAction());
        }
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
    public void updateDescription() {
        this.description = stanceString.DESCRIPTION[0];
    }
}
