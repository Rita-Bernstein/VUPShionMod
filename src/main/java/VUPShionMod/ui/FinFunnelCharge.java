package VUPShionMod.ui;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.FinFunnelMinionAction;
import VUPShionMod.patches.EnergyPanelPatches;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;


public class FinFunnelCharge implements Disposable {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID(FinFunnelCharge.class.getSimpleName()));
    public static final String[] TEXT = uiStrings.TEXT;
    public String name = TEXT[0];
    public String description = TEXT[1];

    private Texture body;
    private Texture light;
    private Texture max;
    private int count = 0;

    private final Color color = Color.WHITE.cpy();

    public float cX = 0.0f;
    public float cY = 0.0f;
    public float scale = 0.55f;

    private final Hitbox hb;

    private float lightTimer = 0.0f;


    public FinFunnelCharge() {
        this.body = ImageMaster.loadImage("VUPShionMod/img/ui/FinFunnelCharge/Body.png");
        this.light = ImageMaster.loadImage("VUPShionMod/img/ui/FinFunnelCharge/Light.png");
        this.max = ImageMaster.loadImage("VUPShionMod/img/ui/FinFunnelCharge/Max.png");

        this.hb = new Hitbox(380.0f * Settings.scale * this.scale, 380.0f * Settings.scale * this.scale);

        updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(TEXT[1], this.count) + TEXT[2];
    }

    public void updatePos(EnergyPanel _instance) {
        this.cX = _instance.current_x;
        this.cY = _instance.current_y + 250.0f * Settings.scale;
        this.hb.move(this.cX, this.cY - 80.0f * Settings.scale);
    }

    public void update() {

        this.hb.update();
    }

    public void render(SpriteBatch sb) {
        this.color.a = 1.0f;
        sb.setColor(this.color);

        if (this.body != null) {
            for (int i = 0; i < 7; i++) {
                sb.draw(this.body, this.cX - this.body.getWidth() / 2.0f, this.cY - this.body.getHeight(),
                        this.body.getWidth() / 2.0f, this.body.getHeight(), this.body.getWidth(), this.body.getHeight(),
                        this.scale * Settings.scale, this.scale * Settings.scale,
                        -45.0f + i * 15.0f,
                        0, 0, this.body.getWidth(), this.body.getHeight(), false, false);
            }
        }


        if (this.light != null) {
            int actual = this.count;
            if (actual > 14) actual = 14;

            if (actual > 1)
                for (int i = 0; i < actual / 2; i++) {
                    sb.draw(this.light, this.cX - this.light.getWidth() / 2.0f, this.cY - this.light.getHeight(),
                            this.light.getWidth() / 2.0f, this.light.getHeight(), this.light.getWidth(), this.light.getHeight(),
                            this.scale * Settings.scale, this.scale * Settings.scale,
                            -45.0f + i * 15.0f,
                            0, 0, this.light.getWidth(), this.light.getHeight(), false, false);
                }

            if (actual % 2 > 0) {
                this.lightTimer += Gdx.graphics.getDeltaTime() * 4.0f;
                this.color.a = ((float) Math.cos(this.lightTimer) + 1.0f) * 0.2f + 0.6f;
                sb.setColor(this.color);

                sb.draw(this.light, this.cX - this.light.getWidth() / 2.0f, this.cY - this.light.getHeight(),
                        this.light.getWidth() / 2.0f, this.light.getHeight(), this.light.getWidth(), this.light.getHeight(),
                        this.scale * Settings.scale, this.scale * Settings.scale,
                        -45.0f + (actual / 2) * 15.0f,
                        0, 0, this.light.getWidth(), this.light.getHeight(), false, false);
            }

        }

        if (this.max != null && this.count >= 15) {
            if (this.count >= 16) {
                this.color.a = 1.0f;
            } else {
                this.lightTimer += Gdx.graphics.getDeltaTime() * 4.0f;
                this.color.a = ((float) Math.cos(this.lightTimer) + 1.0f) * 0.2f + 0.6f;
            }

            sb.setColor(this.color);
            sb.draw(this.max, this.cX - this.max.getWidth() / 2.0f, this.cY - this.max.getHeight() + 180.0f * Settings.scale * this.scale,
                    this.max.getWidth() / 2.0f, this.max.getHeight(), this.max.getWidth(), this.max.getHeight(),
                    this.scale * Settings.scale, this.scale * Settings.scale,
                    0.0f,
                    0, 0, this.max.getWidth(), this.max.getHeight(), false, false);

        }

        this.color.a = 1.0f;
        sb.setColor(this.color);

        this.hb.render(sb);

        if (this.hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(this.hb.cX + this.hb.width * 0.5f,
                    0.75f * Settings.HEIGHT, name, description);
        }

    }

    public void addCharge(int amount) {
        if (amount < 0)
            System.out.println("浮游炮充能增加负数");
        this.count += amount;

        updateDescription();

        if (this.count >= 16) {
            int times = this.count / 16;

            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())
                for (int i = 0; i < times; i++)
                    addToTop(new FinFunnelMinionAction(
                            AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.miscRng)));
        }
    }

    public void loseCharge(int amount) {
        this.count -= amount;
        if (this.count < 0)
            this.count = 0;

        updateDescription();
    }

    public static FinFunnelCharge getFinFunnelCharge() {
        return EnergyPanelPatches.PatchEnergyPanelField.finFunnelCharger.get(AbstractDungeon.overlayMenu.energyPanel);
    }

    public void resetCount() {
        this.count = 0;
    }


    @Override
    public void dispose() {
        if (this.body != null) {
            this.body.dispose();
            this.body = null;
        }
        if (this.light != null) {
            this.light.dispose();
            this.light = null;
        }
        if (this.max != null) {
            this.max.dispose();
            this.max = null;
        }
    }

    public void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }


}
