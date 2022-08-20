package VUPShionMod.ui;

import VUPShionMod.VUPShionMod;
import VUPShionMod.util.SaveHelper;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuPanelButton;

public class ShionMainMenuPanelButton extends MainMenuPanelButton {
    private PanelClickResult result;

    private static Texture trainingModeMain;
    private static Texture trainingModeHighlight;
    private static Texture trainingModeLock;
    private static Texture trainingMode;

    private float uiScale = 1.0f;
    private float offSet_y = 0.0f;


    @SpireEnum
    public static MainMenuPanelButton.PanelClickResult Play_ShionBase;


    public ShionMainMenuPanelButton(PanelClickResult setResult, PanelColor setColor, float x, float y, int index) {
        super(setResult, setColor, x, y);
        this.result = setResult;

        String lang;

        if (Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
            lang = "zhs";
        } else {
            lang = "eng";
        }

        if (trainingModeMain == null)
            trainingModeMain = ImageMaster.loadImage("VUPShionMod/img/ui/MainMenuButton/MainMenuPanelButton/common/TrainingModeMain.png");

        if (trainingModeHighlight == null)
            trainingModeHighlight = ImageMaster.loadImage("VUPShionMod/img/ui/MainMenuButton/MainMenuPanelButton/common/TrainingModeHighlight.png");

        if (trainingModeLock == null)
            trainingModeLock = ImageMaster.loadImage("VUPShionMod/img/ui/MainMenuButton/MainMenuPanelButton/" + lang + "/TrainingModeLock.png");

        if (trainingMode == null)
            trainingMode = ImageMaster.loadImage("VUPShionMod/img/ui/MainMenuButton/MainMenuPanelButton/" + lang + "/TrainingMode.png");

        this.hb.resize(265.0f * this.uiScale * Settings.scale, 920.0f * this.uiScale * Settings.scale);
        this.hb.move(x, y);

//        setLabel();
    }

    private void setLabel() {
        PanelClickResult result = ReflectionHacks.getPrivate(this, MainMenuPanelButton.class, "result");
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("ShionMainMenuPanelButton"));


        if (result == ShionMainMenuPanelButton.Play_ShionBase) {
            ReflectionHacks.setPrivate(this, MainMenuPanelButton.class, "header", uiStrings.TEXT[0]);
            if (this.pColor == PanelColor.GRAY) {
                ReflectionHacks.setPrivate(this, MainMenuPanelButton.class, "description", uiStrings.TEXT[2]);
                ReflectionHacks.setPrivate(this, MainMenuPanelButton.class, "panelImg", ImageMaster.MENU_PANEL_BG_GRAY);
            } else {
                ReflectionHacks.setPrivate(this, MainMenuPanelButton.class, "description", uiStrings.TEXT[1]);
                ReflectionHacks.setPrivate(this, MainMenuPanelButton.class, "panelImg", ImageMaster.MENU_PANEL_BG_RED);
            }

            ReflectionHacks.setPrivate(this, MainMenuPanelButton.class, "portraitImg", ImageMaster.P_LOOP);
            return;
        }

    }

    @SpireOverride
    protected void buttonEffect() {
        PanelClickResult result = ReflectionHacks.getPrivate(this, MainMenuPanelButton.class, "result");

        if (result == ShionMainMenuPanelButton.Play_ShionBase) {
            SaveHelper.isTrainingMod = true;
            SaveHelper.saveSettings();
            CardCrawlGame.mainMenuScreen.charSelectScreen.open(false);
            return;
        }

        SpireSuper.call();
    }


    @Override
    public void update() {
        super.update();

        this.offSet_y = ReflectionHacks.getPrivate(this, MainMenuPanelButton.class, "yMod");
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(ReflectionHacks.getPrivate(this, MainMenuPanelButton.class, "wColor"));
        sb.setBlendFunction(770, 771);

        if (this.result == ShionMainMenuPanelButton.Play_ShionBase) {
            if (this.hb.hovered) {
                sb.draw(trainingModeHighlight, this.hb.cX - trainingModeHighlight.getWidth() / 2.0f,
                        this.hb.cY - trainingModeHighlight.getHeight() / 2.0f + offSet_y,
                        trainingModeHighlight.getWidth() / 2.0f, trainingModeHighlight.getHeight() / 2.0f, trainingModeHighlight.getWidth(), trainingModeHighlight.getHeight(),
                        this.uiScale * Settings.scale, this.uiScale * Settings.scale, 0.0F, 0, 0, trainingModeHighlight.getWidth(), trainingModeHighlight.getHeight(), false, false);
            } else {
                sb.draw(trainingModeMain, this.hb.cX - trainingModeMain.getWidth() / 2.0f,
                        this.hb.cY - trainingModeMain.getHeight() / 2.0f + offSet_y,
                        trainingModeMain.getWidth() / 2.0f, trainingModeMain.getHeight() / 2.0f, trainingModeMain.getWidth(), trainingModeMain.getHeight(),
                        this.uiScale * Settings.scale, this.uiScale * Settings.scale, 0.0F, 0, 0, trainingModeMain.getWidth(), trainingModeMain.getHeight(), false, false);
            }

            if (this.pColor == PanelColor.GRAY) {
                sb.draw(trainingModeLock, this.hb.cX - trainingModeLock.getWidth() / 2.0f,
                        this.hb.cY - trainingModeLock.getHeight() / 2.0f + offSet_y,
                        trainingModeLock.getWidth() / 2.0f, trainingModeLock.getHeight() / 2.0f, trainingModeLock.getWidth(), trainingModeLock.getHeight(),
                        this.uiScale * Settings.scale, this.uiScale * Settings.scale, 0.0F, 0, 0, trainingModeLock.getWidth(), trainingModeLock.getHeight(), false, false);
            } else {
                sb.draw(trainingMode, this.hb.cX - trainingMode.getWidth() / 2.0f,
                        this.hb.cY - trainingMode.getHeight() / 2.0f + offSet_y,
                        trainingMode.getWidth() / 2.0f, trainingMode.getHeight() / 2.0f, trainingMode.getWidth(), trainingMode.getHeight(),
                        this.uiScale * Settings.scale, this.uiScale * Settings.scale, 0.0F, 0, 0, trainingMode.getWidth(), trainingMode.getHeight(), false, false);
            }
        }
        this.hb.render(sb);
    }
}
