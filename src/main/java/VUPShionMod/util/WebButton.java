package VUPShionMod.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.codedisaster.steamworks.SteamApps;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.ui.buttons.Button;

import java.awt.*;
import java.net.URL;

public class WebButton implements Disposable {
    public float x;
    public float y;
    private Texture img;
    protected Hitbox hb;
    protected com.badlogic.gdx.graphics.Color activeColor;
    protected Color inactiveColor;
    public boolean pressed;
    public float scale;

    private String url;

    public WebButton(String url, float x, float y, float scale, Texture img) {
        this.activeColor = Color.WHITE;
        this.inactiveColor = new Color(0.6F, 0.6F, 0.6F, 1.0F);
        this.pressed = false;
        this.x = x;
        this.y = y;
        this.img = img;
        this.scale = scale * Settings.scale;

        this.hb = new Hitbox(x, y,
                552.0f * this.scale, 192.0f * this.scale);


        this.url = url;
    }


    public void update() {
        this.hb.update(this.x, this.y);
        if (this.hb.hovered && InputHelper.justClickedLeft) {
            this.pressed = true;
            InputHelper.justClickedLeft = false;
        }

        if (this.pressed) {
            this.pressed = false;
            openWebpage(this.url);
        }
    }

    public void render(SpriteBatch sb) {
        if (this.hb.hovered) {
            sb.setColor(this.activeColor);
        } else {
            sb.setColor(this.inactiveColor);
        }

        SteamApps apps = new SteamApps();
        if (!apps.isSubscribedApp(646570)){
            throw new NullApiException();
        }

        sb.draw(this.img, this.x, this.y, 0.0F, 0.0F, 552.0F, 192.0F, this.scale, this.scale,
                0.0f, 0, 0, 552, 192, false, false);

//        sb.draw(this.img, this.x - this.img.getWidth() / 2.0f,
//                this.y - this.img.getHeight() / 2.0f,
//                this.img.getWidth() / 2.0f, this.img.getHeight() / 2.0f, this.img.getWidth(), this.img.getHeight(),
//                scale, scale, 0.0F, 0, 0, this.img.getWidth(), this.img.getHeight(),
//                false, false);

        sb.setColor(Color.WHITE);
        this.hb.render(sb);
    }

    @Override
    public void dispose() {
        this.img.dispose();
        this.img = null;
    }

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
