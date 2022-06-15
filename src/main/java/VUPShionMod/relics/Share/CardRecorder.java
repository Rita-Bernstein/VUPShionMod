package VUPShionMod.relics.Share;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

public class CardRecorder extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(CardRecorder.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/CardRecorder.png";
    private static final String OUTLINE_PATH = "img/relics/outline/CardRecorder.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    private boolean firstTurn = true;

    public CardRecorder() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
        this.firstTurn = true;
    }

    public void atTurnStartPostDraw() {
        if (this.counter >= 6 && !this.firstTurn) {
            addToBot(new DrawCardAction(AbstractDungeon.player, 3));
        } else {
            this.firstTurn = false;
        }
        this.counter = 0;
        stopPulse();
    }


    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        this.counter++;
        if (this.counter >= 6) {
            beginLongPulse();
        }
    }


    public void onVictory() {
        this.counter = -1;
        stopPulse();
    }


    @Override
    public boolean canSpawn() {
        return EnergyPanelPatches.isShionModChar();
    }
}
