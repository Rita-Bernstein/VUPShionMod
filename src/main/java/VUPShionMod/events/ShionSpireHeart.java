package VUPShionMod.events;

import VUPShionMod.VUPShionMod;
import VUPShionMod.util.ShionNpc;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.GameOverScreen;

public class ShionSpireHeart extends AbstractEvent {
    public static final String ID = VUPShionMod.makeID(ShionSpireHeart.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    private final Color fadeColor;
    private AnimatedNpc npc;

    private final int damageDealt;

    private int currentScreen = 0;


    public ShionSpireHeart() {
        this.fadeColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        this.npc = new ShionNpc();

        this.body = DESCRIPTIONS[0];
        this.roomEventText.clear();
        this.roomEventText.addDialogOption(OPTIONS[0]);
        this.hasDialog = true;
        this.hasFocus = true;

        GameOverScreen.resetScoreChecks();
        this.damageDealt = GameOverScreen.calcScore(true);
        CardCrawlGame.publisherIntegration.incrementStat("test_stat", this.damageDealt);


        CardCrawlGame.publisherIntegration.incrementStat(Settings.isBeta ?
                AbstractDungeon.player.getWinStreakKey() + "_BETA" : AbstractDungeon.player.getWinStreakKey(), 1);


        CardCrawlGame.playerPref.putInteger("DMG_DEALT", this.damageDealt + CardCrawlGame.playerPref.getInteger("DMG_DEALT", 0));

    }


    private void goToFinalAct() {
        this.fadeColor.a = 0.0F;
        AbstractDungeon.screen = AbstractDungeon.CurrentScreen.DOOR_UNLOCK;
        CardCrawlGame.mainMenuScreen.doorUnlockScreen.open(true);
    }

    @Override
    public void update() {
        super.update();

        this.fadeColor.a = MathHelper.slowColorLerpSnap(this.fadeColor.a, 1.0F);
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        if (currentScreen < 2) {
            this.currentScreen++;
            this.roomEventText.updateBodyText(currentScreen == 1 ?
                    String.format(DESCRIPTIONS[currentScreen], this.damageDealt)
                    : DESCRIPTIONS[currentScreen]);
            this.roomEventText.updateDialogOption(0, OPTIONS[0]);
            return;
        }
        if (currentScreen < 10) {
            if (Settings.isFinalActAvailable && Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey) {
                this.roomEventText.updateBodyText(DESCRIPTIONS[3]);
                this.roomEventText.updateDialogOption(0, OPTIONS[1]);
                this.currentScreen = 11;
            } else {
                this.roomEventText.updateBodyText(DESCRIPTIONS[4]);
                this.roomEventText.updateDialogOption(0, OPTIONS[2]);
                this.currentScreen = 12;
            }

            return;
        }

        if (currentScreen == 11) {
            this.roomEventText.clear();
            this.hasFocus = false;
            this.roomEventText.hide();
            goToFinalAct();
            return;
        }


        if (currentScreen == 12) {
            AbstractDungeon.player.isDying = true;
            this.hasFocus = false;
            this.roomEventText.hide();
            AbstractDungeon.player.isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(null);
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.fadeColor);
        if (this.npc != null) {
            this.npc.render(sb);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (this.npc != null) {
            this.npc.dispose();
            this.npc = null;
        }
    }
}
