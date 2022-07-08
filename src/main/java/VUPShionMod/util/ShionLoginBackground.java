package VUPShionMod.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.codedisaster.steamworks.SteamApps;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.scenes.TitleBackground;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

import java.util.ArrayList;

public class ShionLoginBackground extends TitleBackground implements Disposable {
    private Texture bg_account = ImageMaster.loadImage("VUPShionMod/img/ui/mainBg.png");
    private float display_Cx = Settings.WIDTH / 2.0f;
    private float display_Cy = Settings.HEIGHT / 2.0F;
    private float loginScale = 0.8f;

    private ArrayList<WebButton> buttons = new ArrayList<>();

    private float fix_y;
    private float scale = 0.36f;

    public ShionLoginBackground() {
        this.fix_y = 230.0f * this.scale * Settings.scale;

        if (this.buttons.isEmpty()) {
            buttons.add(new WebButton("https://afdian.net/@AnastasiaShion",
                    Settings.WIDTH - 552.0f * this.scale * Settings.scale - 40.0f * Settings.scale,
                    Settings.HEIGHT - 24.0f * Settings.scale - 192.0f * this.scale * Settings.scale, this.scale,
                    ImageMaster.loadImage("VUPShionMod/img/ui/MainMenuButton/AfaDian.png")));

            buttons.add(new WebButton("https://www.patreon.com/AnastasiaShion",
                    Settings.WIDTH - 552.0f * this.scale * Settings.scale - 40.0f * Settings.scale,
                    Settings.HEIGHT - 24.0f * Settings.scale - 192.0f * this.scale * Settings.scale - fix_y, this.scale,
                    ImageMaster.loadImage("VUPShionMod/img/ui/MainMenuButton/Patreon.png")));
        }


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        sb.draw(bg_account, display_Cx - 1198.0F,
                display_Cy - 600.0F - 0.0f * Settings.scale,
                1198.0F, 600.0F,
                2397.0f, 1200.0f,
                Settings.renderScale * loginScale, Settings.renderScale * loginScale,
                0, 0, 0, 2397, 1200, false, false);

        if (!this.buttons.isEmpty()) {
            for (WebButton button : this.buttons) {
                button.render(sb);
            }
        }
    }

    @Override
    public void update() {
        super.update();

        if (CardCrawlGame.mainMenuScreen != null) {
            if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.MAIN_MENU) {
                if (!this.buttons.isEmpty()) {
                    for (WebButton button : this.buttons) {
                        button.update();
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {
        bg_account.dispose();

        if (!this.buttons.isEmpty()) {
            for (WebButton button : this.buttons) {
                button.dispose();
            }

            this.buttons.clear();
        }
    }


}
