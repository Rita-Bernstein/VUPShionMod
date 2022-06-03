package VUPShionMod.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;

import java.util.ArrayList;

public class CGlayout implements Disposable {
    private Color bgColor;
    private Color screenColor;

    private float darkenTimer = 1.0F;
    private float fadeTimer = 1.0F;
    private float switchTimer = 1.0F;
    public float switchTimerMax = 2.0f;

    private int currentScene = 0;

    private ArrayList<CutscenePanel> panels = new ArrayList<>();

    private Texture bgImg;
    public boolean isDone = false;

    public CGlayout(String name) {
        this.bgColor = Color.WHITE.cpy();
        this.screenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
//        this.bgImg = ImageMaster.loadImage("images/scenes/redBg.jpg");
        String lang;

        if (Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
            lang = "zhs";
        } else if (Settings.language == Settings.GameLanguage.JPN) {
            lang = "jpn";
        } else {
            lang = "eng";
        }


        switch (name) {
            case "Liyezhu_BE":
                this.panels.add(new CutscenePanel("VUPShionMod/img/cg/Liyezhu/" + lang + "/CG04.png"));
                break;
            case "Liyezhu_TE":
                this.panels.add(new CutscenePanel("VUPShionMod/img/cg/Liyezhu/" + lang + "/CG01.png"));
                this.panels.add(new CutscenePanel("VUPShionMod/img/cg/Liyezhu/" + lang + "/CG02.png"));
                this.panels.add(new CutscenePanel("VUPShionMod/img/cg/Liyezhu/" + lang + "/CG03.png"));
                break;
            case "WangChuan":
                for (int i = 1; i < 6; i++) {
                    this.panels.add(new CutscenePanel("VUPShionMod/img/cg/WangChuan/" + lang + "/CG0" + i + ".png"));
                }
                break;
            case "StoryBE":
                for (int i = 1; i < 4; i++) {
                    this.panels.add(new CutscenePanel("VUPShionMod/img/cg/StoryBE/" + lang + "/CG0" + i + ".png"));
                }
                break;
            case "StoryTE":
                for (int i = 1; i < 3; i++) {
                    this.panels.add(new CutscenePanel("VUPShionMod/img/cg/StoryTE/" + lang + "/CG0" + i + ".png"));
                }
                break;
            default:
                for (int i = 1; i < 10; i++) {
                    this.panels.add(new CutscenePanel("VUPShionMod/img/cg/Shion/" + lang + "/CG0" + i + ".png"));
                }
                break;
        }
    }


    public void update() {
        updateFadeOut();
        updateFadeIn();

        for (CutscenePanel p : this.panels) {
            p.update();
        }

        updateIfDone();
        updateSceneChange();
    }


    private void updateIfDone() {
        if (this.isDone) {
            this.bgColor.a -= Gdx.graphics.getDeltaTime();

            for (CutscenePanel p : this.panels) {
                if (!p.finished) {
                    return;
                }
            }

            dispose();
            this.bgColor.a = 0.0F;
        }
    }


    private void updateSceneChange() {
        this.switchTimer -= Gdx.graphics.getDeltaTime();

        if ((InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) && this.switchTimer > 1.0F)
            this.switchTimer = 1.0F;

        if (this.switchTimer < 0.0F) {
            for (CutscenePanel p : this.panels) {
                if (!p.activated) {
                    p.activate();
                    this.switchTimer = this.switchTimerMax;
                    return;
                }
            }

            for (CutscenePanel p : this.panels)
                p.fadeOut();

            this.isDone = true;
            dispose();

        }
    }

    private void updateFadeIn() {
        if (this.darkenTimer == 0.0F) {
            this.fadeTimer -= Gdx.graphics.getDeltaTime();

            if (this.fadeTimer < 0.0F)
                this.fadeTimer = 0.0F;

            this.screenColor.a = this.fadeTimer;
        }
    }

    private void updateFadeOut() {
        if (this.darkenTimer != 0.0F) {
            this.darkenTimer -= Gdx.graphics.getDeltaTime();

            if (this.darkenTimer < 0.0F) {
                this.darkenTimer = 0.0F;
                this.fadeTimer = 1.0F;
                this.switchTimer = 1.0F;
            }

            this.screenColor.a = 1.0F - this.darkenTimer;
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void renderAbove(SpriteBatch sb) {
        if (!this.isDone) {
            sb.setColor(Color.BLACK);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }

        if (this.bgImg != null) {
            sb.setColor(this.bgColor);
            renderImg(sb, this.bgImg);
        }


        renderPanels(sb);

        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.setColor(Color.WHITE);
    }

    private void renderPanels(SpriteBatch sb) {
        for (CutscenePanel p : this.panels) {
            p.render(sb);
        }
    }

    private void renderImg(SpriteBatch sb, Texture img) {
        if (Settings.isSixteenByTen) {
            sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        } else {
            sb.draw(img, 0.0F, -50.0F * Settings.scale, Settings.WIDTH, Settings.HEIGHT + 110.0F * Settings.scale);
        }
    }

    @Override
    public void dispose() {
        for (CutscenePanel p : this.panels)
            p.dispose();

        if (this.bgImg != null) {
            this.bgImg.dispose();
            this.bgImg = null;
        }
    }
}
