package VUPShionMod.util;

import VUPShionMod.VUPShionMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class SansMeter {
    private Texture bg;
    private Texture meter;
    private Texture light;

    public int amount = 100;
    private Hitbox hb;

    private float cX = 0.0f;
    private float cY = 0.0f;
    public float current_x = 0.0f;
    public float current_y = 0.0f;
    private float scale = 0.23f;
    private float lightTimer = 0.0f;

    private Color color = Color.WHITE.cpy();

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("SansMeter"));
    public static final String[] TEXT = uiStrings.TEXT;
    public String name = TEXT[0];
    public String description = TEXT[1];

    public SansMeter() {
        this.bg = ImageMaster.loadImage("VUPShionMod/img/ui/SanMeter/BG.png");
        this.meter = ImageMaster.loadImage("VUPShionMod/img/ui/SanMeter/Meter.png");
        this.light = ImageMaster.loadImage("VUPShionMod/img/ui/SanMeter/MeterTop.png");

        this.hb = new Hitbox(792.0f * this.scale * Settings.scale, 1158.0f * this.scale * Settings.scale);

        updateDescription();
    }

    public void updatePos(EnergyPanel _instance) {
        this.cX = _instance.current_x;
        this.cY = _instance.current_y + 100.0f * Settings.scale;
        this.hb.move(this.cX, this.cY + 579.0f * this.scale * Settings.scale);
    }

    public void updateDescription() {
        this.description = TEXT[1] + TEXT[2] + TEXT[3];
    }

    public void update() {
        this.hb.update();
    }


    public void render(SpriteBatch sb) {
        sb.setColor(this.color);

        sb.draw(this.bg,
                this.cX + this.current_x - 396.0f,
                this.cY + this.current_y,
                396.0F, 0.0f,
                792.0F, 1158.0F,
                this.scale * Settings.scale, this.scale * Settings.scale,
                0.0f,
                0, 0,
                792, 1158,
                false, false);

        sb.draw(this.meter,
                this.cX + this.current_x - 396.0f,
                this.cY + this.current_y - 0.0f + 89.0f * Settings.scale * this.scale,
                396.0F, 0.0f,
                792.0F, 914 * this.amount * 0.01f,
                this.scale * Settings.scale, this.scale * Settings.scale,
                0.0f,
                0, (int) Math.floor((914 * (100 - this.amount)) * 0.01f),
                792, (int) Math.floor((914 * this.amount) * 0.01f),
                false, false);


        this.lightTimer += Gdx.graphics.getDeltaTime() * 4.0f;
        this.color.a = ((float) Math.cos(this.lightTimer) + 1.0f) * 0.2f + 0.6f;
        sb.setColor(this.color);

        sb.draw(this.light,
                this.cX + this.current_x - 396.0f,
                this.cY + this.current_y - 64.0f + (89.0f + 914.0f * this.amount * 0.01f) * Settings.scale * this.scale,
                396.0F, 64.0f,
                792.0F, 128.0f,
                this.amount < 30 ? this.scale * Settings.scale * (this.amount * 0.03f + 0.1f) : this.scale * Settings.scale, this.scale * Settings.scale,
                0.0f,
                0, 0,
                792, 128,
                false, false);


        this.color.a = 1.0f;
        sb.setColor(this.color);

        String energyMsg = this.amount + "";
        AbstractDungeon.player.getEnergyNumFont().getData().setScale(8.0f * this.scale);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), energyMsg,
                this.cX + this.current_x,
                this.cY + this.current_y + 670.0f * Settings.scale * this.scale,
                this.color);

        this.hb.render(sb);

        if (this.hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(this.hb.cX + this.hb.width * 0.5f,
                    this.hb.cY + this.hb.height * 0.3f, name, description);
        }

    }


}
