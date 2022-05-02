package VUPShionMod.skins;

import VUPShionMod.patches.CharacterSelectScreenPatches;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class SkinInfoLabel {
    public Texture body;
    public Texture outline;
    public Texture longLine;


    public Hitbox hb;
    public String msg;

    public float cX;
    public float cY;
    public float scale;
    public Color color = Color.WHITE.cpy();
    public int index;

    public SkinInfoLabel(String msg, int index) {
        this.body = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/option5.png");
        this.outline = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/option2.png");
        this.longLine = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/option4.png");

        this.scale = 1.1f;
        this.hb = new Hitbox(500.0f * this.scale * Settings.scale, 80.0f * this.scale * Settings.scale);

        this.msg = msg;
        this.index = index;
    }


    public void update() {

        this.cX = CharacterSelectScreenPatches.skinManager.panel_x + (200.0f + 60.0f * this.index)
                * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;
        this.cY = Settings.HEIGHT * 0.5f + (430.0f - 80.0f * this.index)
                * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;


        this.hb.move(this.cX + 200.0f * this.scale * Settings.scale, this.cY);

        if (this.hb.justHovered)
            CardCrawlGame.sound.playV("UI_HOVER", 0.75f);

        this.hb.update();


    }

    public void render(SpriteBatch sb) {
        if (this.hb.hovered)
            sb.setColor(Color.WHITE);
        else
            sb.setColor(Color.LIGHT_GRAY);

        sb.draw(this.longLine,
                this.cX + 15.0f * this.scale * Settings.scale,
                this.cY - 15.0f * this.scale * Settings.scale,
                0.0F, 0.0f,
                2.0F, 2.0f,
                (200.0f - 30.0f * this.index) * this.scale * Settings.scale, this.scale * Settings.scale,
                0.0f,
                0, 0,
                2, 2,
                false, false);

        sb.draw(this.body,
                this.cX - 15.0f,
                this.cY - 15.0f,
                15.0F, 15.0f,
                30.0F, 30.0f,
                this.scale * Settings.scale, this.scale * Settings.scale,
                0.0f,
                0, 0,
                30, 30,
                false, false);

        sb.draw(this.outline,
                this.cX - 17.0f,
                this.cY - 17.0f,
                17.0F, 17.0f,
                34.0F, 34.0f,
                this.scale * Settings.scale, this.scale * Settings.scale,
                0.0f,
                0, 0,
                34, 34,
                false, false);


        float scaleX =  FontHelper.cardTitleFont.getData().scaleX;
        float scaleY =  FontHelper.cardTitleFont.getData().scaleY;
        FontHelper.cardTitleFont.getData().setScale(0.8F);
        FontHelper.renderFontLeft(sb, FontHelper.cardTitleFont, this.msg, this.cX + 30.0f * this.scale * Settings.scale, this.cY, this.color);
        FontHelper.cardTitleFont.getData().setScale(scaleX,scaleY);

        this.hb.render(sb);

    }

}
