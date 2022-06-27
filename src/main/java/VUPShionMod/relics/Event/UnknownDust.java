package VUPShionMod.relics.Event;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cutscenes.CGlayout;
import VUPShionMod.monsters.Story.Ouroboros;
import VUPShionMod.monsters.Story.PlagaAMundoMinion;
import VUPShionMod.monsters.Story.TimePortal;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.util.SaveHelper;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;

import java.util.ArrayList;

public class UnknownDust extends AbstractShionRelic implements OnPlayerDeathRelic {
    public static final String ID = VUPShionMod.makeID(UnknownDust.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/UnknownDust.png";
    private static final String OUTLINE_PATH = "img/relics/outline/UnknownDust.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));
    private static final RelicStrings relicString = CardCrawlGame.languagePack.getRelicStrings(ID);

    public boolean triggered = false;
    public boolean effectApplied = false;
    public boolean saveCannotLose = false;

    private CGlayout cg;

    public UnknownDust() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
        this.cg = new CGlayout("Liyezhu_TE");
        this.cg.switchTimerMax = 4.0f;

        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.hasRelic(AbyssalCrux.ID)) {
                this.cg.dispose();
                this.cg = new CGlayout("Liyezhu_BE");
                this.cg.switchTimerMax = 4.0f;
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void setDescriptionAfterLoading() {
        this.description = getUpdatedDescription();
        this.tips.clear();
        if (this.triggered)
            this.tips.add(new PowerTip(relicString.DESCRIPTIONS[3], this.description));
        else
            this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    public void triggerRelic() {
        if (!triggered) {
            triggered = true;

            ArrayList<AbstractGameAction> actionToRemove = new ArrayList<>();
            for (AbstractGameAction action : AbstractDungeon.actionManager.actions) {
                if (action.target != null)
                    if (action.actionType == AbstractGameAction.ActionType.DAMAGE && action.target.isPlayer) {
                        actionToRemove.add(action);
                    }
            }

            AbstractDungeon.actionManager.actions.removeAll(actionToRemove);

            AbstractDungeon.actionManager.monsterQueue.clear();

            AbstractDungeon.isScreenUp = true;
            GameCursor.hidden = true;
            AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NO_INTERACT;

            if (AbstractDungeon.player.hasRelic(AbyssalCrux.ID)) {
                AnastasiaNecklace.replaceBackground();
            }
        }
    }

    private void endGame() {
        SaveAndContinue.deleteSave(AbstractDungeon.player);
        if (AbstractDungeon.unlocks.isEmpty() || Settings.isDemo) {
            CardCrawlGame.playCreditsBgm = true;
            CardCrawlGame.startOverButShowCredits();
        } else {
            AbstractDungeon.unlocks.clear();
            Settings.isTrial = false;
            Settings.isDailyRun = false;
            Settings.isEndless = false;
            CardCrawlGame.trial = null;

            if (Settings.isDailyRun) {
                CardCrawlGame.startOver();
            } else {
                CardCrawlGame.playCreditsBgm = true;
                CardCrawlGame.startOverButShowCredits();
            }
        }
    }

    public void applyEffect() {
        if (!AbstractDungeon.player.hasRelic(AbyssalCrux.ID)) {
            SaveHelper.liyezhuRelic = true;
            SaveHelper.saveSettings();
            endGame();
        } else {
            CardCrawlGame.music.playTempBGM("VUPShionMod:Boss_Phase2");

            AbstractDungeon.player.increaseMaxHp(5000 - AbstractDungeon.player.maxHealth, true);
            AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
            for (AbstractRelic relic : AbstractDungeon.player.relics) {
                if (relic instanceof AbyssalCrux) {
                    ((AbyssalCrux) relic).upgrade();
                }
            }
        }

    }

    @Override
    public void onVictory() {
        if (AbstractDungeon.player.hasRelic(AbyssalCrux.ID))
            endGame();
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {
        if (!triggered && AnastasiaNecklace.eventRelicCanTrigger()) {
            for (AbstractRelic relic : AbstractDungeon.player.relics) {
                if (relic instanceof AbyssalCrux) {
                    ((AbyssalCrux) relic).dontHeal = true;
                }
            }

            CardCrawlGame.music.justFadeOutTempBGM();
            triggerRelic();
            AbstractDungeon.player.hideHealthBar();
            this.saveCannotLose = (AbstractDungeon.getCurrRoom()).cannotLose;
            (AbstractDungeon.getCurrRoom()).cannotLose = true;
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (this.triggered) {
            cg.render(sb);
        }
    }

    @Override
    public void update() {
        super.update();
        if (this.triggered) {
            cg.update();
        }


        if (cg.isDone && !effectApplied) {
            effectApplied = true;
            AbstractDungeon.isScreenUp = false;
            GameCursor.hidden = false;
            AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
            AbstractDungeon.player.showHealthBar();
            (AbstractDungeon.getCurrRoom()).cannotLose = this.saveCannotLose;
            applyEffect();
        }
    }


    public void renderAbove(SpriteBatch sb) {
        if (this.triggered)
            cg.renderAbove(sb);
    }


}
