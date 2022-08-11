package VUPShionMod.relics.Event;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TurnTriggerAllFinFunnelAction;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.List;

public class WhiteSeal extends AbstractShionRelic implements ClickableRelic {
    public static final String ID = VUPShionMod.makeID(WhiteSeal.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/WhiteSeal.png";
    private static final String OUTLINE_PATH = "img/relics/outline/WhiteSeal.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public WhiteSeal() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
        setDescriptionAfterLoading();
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void setDescriptionAfterLoading() {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2]));

        this.initializeTips();
    }

    @Override
    public void atBattleStart() {
        this.grayscale = false;
    }

    @Override
    public void onRightClick() {
        if ((AbstractDungeon.getCurrRoom()).monsters != null && !(AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead() &&
                !AbstractDungeon.actionManager.turnHasEnded &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            if (!this.grayscale) {
                this.grayscale = true;
                flash();
                addToBot(new TalkAction(true,DESCRIPTIONS[3], 1.2F, 1.2F));
                addToBot(new DrawCardAction(1));
                addToBot(new GainEnergyAction(1));
                addToBot(new HealAction(AbstractDungeon.player,AbstractDungeon.player,5));
                addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new WeakPower(AbstractDungeon.player,1,false)));
            }
    }

}
