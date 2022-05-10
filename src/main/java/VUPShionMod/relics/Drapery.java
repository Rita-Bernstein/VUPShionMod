package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerFinFunnelAction;
import VUPShionMod.actions.Shion.TurnTriggerAllFinFunnelAction;
import VUPShionMod.finfunnels.AbstractFinFunnel;
import VUPShionMod.finfunnels.GravityFinFunnel;
import VUPShionMod.finfunnels.PursuitFinFunnel;
import VUPShionMod.patches.AbstractPlayerPatches;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.List;

public class Drapery extends AbstractShionRelic implements ClickableRelic {
    public static final String ID = VUPShionMod.makeID("Drapery");
    public static final String IMG_PATH = "img/relics/Drapery.png";
    private static final String OUTLINE_PATH = "img/relics/outline/Drapery.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public Drapery() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + this.DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new MakeTempCardInDrawPileAction(new Dazed(), 2, true, true));

        this.grayscale = false;
    }

    @Override
    public void atTurnStart() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FrailPower(AbstractDungeon.player, 1, false)));
    }

    @Override
    public void onRightClick() {
        if ((AbstractDungeon.getCurrRoom()).monsters != null && !(AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead() &&
                !AbstractDungeon.actionManager.turnHasEnded &&
                (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)
            if (!this.grayscale) {
                this.grayscale = true;
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new IntangiblePlayerPower(AbstractDungeon.player, 1)));
                for (int i = 0; i < 2; i++)
                    addToBot(new TurnTriggerAllFinFunnelAction(true));

                for (int i = 0; i < 2; i++)
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            List<AbstractFinFunnel> funnelList = AbstractPlayerPatches.AddFields.finFunnelList.get(AbstractDungeon.player);
                            funnelList.get(AbstractDungeon.miscRng.random(funnelList.size() - 1)).upgradeLevel(1);
                            isDone = true;
                        }
                    });
                addToBot(new PressEndTurnButtonAction());

            }
    }
}
