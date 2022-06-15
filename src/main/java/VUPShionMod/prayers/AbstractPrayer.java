package VUPShionMod.prayers;


import VUPShionMod.actions.Liyezhu.RemoveSpecificPrayerAction;
import VUPShionMod.vfx.Liyezhu.FlashPrayerEffect;
import VUPShionMod.vfx.Liyezhu.GainPrayerEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractPrayer implements Comparable<AbstractPrayer>, Disposable {
    public static TextureAtlas atlas;
    public TextureAtlas.AtlasRegion region48;
    public TextureAtlas.AtlasRegion region128;

    private static final int RAW_W = 32;
    public Texture img;

    public Hitbox hb = new Hitbox(48.0f * Settings.scale, 48.0f * Settings.scale);

    public float x;
    public float y;

    private Color renderColor = Color.WHITE.cpy();
    private ArrayList<AbstractGameEffect> effect = new ArrayList();
    protected float fontScale = 1.0F;

    public AbstractCreature owner = AbstractDungeon.player;
    public AbstractCreature target = AbstractDungeon.player;
    public int turns = 1;
    public int amount = -1;

    public String name;
    public String description;
    public String ID;

    protected boolean removing = false;


    public static String[] DESCRIPTIONS;

    public AbstractPrayer() {
    }

    public static void initialize() {
        atlas = new TextureAtlas(Gdx.files.internal("powers/powers.atlas"));
    }


    public void updateDescription() {
    }

    public void use() {
    }

    public void atStartOfTurn() {
        reduceTurn(1);
    }


    public void onInitialApplication() {

    }

//    public void atEndOfTurn() {
//    }

//

    public void update() {
        this.updateFlash();
        this.updateFontScale();
        this.updateColor();
        this.hb.update();

        if (this.hb.hovered) {
            TipHelper.renderGenericTip(this.x + 96.0F * Settings.scale, this.y + 64.0F * Settings.scale, this.name, this.description);
        }


    }

    public void updateParticles() {
    }

    public void render(SpriteBatch sb) {
        this.renderIcons(sb, this.x, this.y, this.renderColor);
        this.renderAmount(sb, this.x, this.y, this.renderColor);
        this.hb.render(sb);

    }


    //    工具方法

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        this.hb.move(x, y);
    }

    public void reduceTurn(int turn) {
        if (turn > 0) {
            this.turns -= turn;
            use();
            if (this.turns <= 0) {
                addToBot(new RemoveSpecificPrayerAction(this));
            }
        } else
            System.out.println("减个负数你是啥意思");

    }

    public void triggerPrayer(){
        use();
        addToBot(new RemoveSpecificPrayerAction(this));
    }

    public AbstractPrayer makeCopy(){
        try{
            return this.getClass().newInstance();
        }catch(InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("BaseMod failed to auto-generate makeCopy for card: " + ID);
        }
    }

    protected void loadRegion(String fileName) {
        this.region48 = atlas.findRegion("48/" + fileName);
        this.region128 = atlas.findRegion("128/" + fileName);
    }

    public String toString() {
        return "[" + this.name + "]: " + this.description;
    }

    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public void playApplyPowerSfx() {
        int roll = MathUtils.random(0, 2);
        if (roll == 0) {
            CardCrawlGame.sound.play("BUFF_1");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("BUFF_2");
        } else {
            CardCrawlGame.sound.play("BUFF_3");
        }
    }


    public int compareTo(AbstractPrayer other) {
        return this.turns - other.turns;
    }


    public void flash() {
        this.effect.add(new GainPrayerEffect(this));
        AbstractDungeon.effectList.add(new FlashPrayerEffect(this));
    }

//    渲染相关

    private void updateFontScale() {
        if (this.fontScale != 1.0F) {
            this.fontScale = MathUtils.lerp(this.fontScale, 1.0F, Gdx.graphics.getDeltaTime() * 10.0F);
            if (this.fontScale - 1.0F < 0.05F) {
                this.fontScale = 1.0F;
            }
        }

    }

    private void updateFlash() {
        Iterator i = this.effect.iterator();

        while (i.hasNext()) {
            AbstractGameEffect e = (AbstractGameEffect) i.next();
            e.update();
            if (e.isDone) {
                i.remove();
            }
        }

    }

    private void updateColor() {
        if (this.renderColor.a != 1.0F) {
            this.renderColor.a = MathHelper.fadeLerpSnap(this.renderColor.a, 1.0F);
        }
    }


    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        if (this.img != null) {
            sb.setColor(c);
            sb.draw(this.img, x - 12.0F, y - 12.0F, 16.0F, 16.0F, 32.0F, 32.0F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 0, 0, 32, 32, false, false);
        } else {
            sb.setColor(c);
            if (Settings.isMobile) {
                sb.draw(this.region48, x - this.region48.packedWidth / 2.0F, y - this.region48.packedHeight / 2.0F, this.region48.packedWidth / 2.0F, this.region48.packedHeight / 2.0F, this.region48.packedWidth, this.region48.packedHeight, Settings.scale * 1.17F, Settings.scale * 1.17F, 0.0F);
            } else {
                sb.draw(this.region48, x - this.region48.packedWidth / 2.0F, y - this.region48.packedHeight / 2.0F, this.region48.packedWidth / 2.0F, this.region48.packedHeight / 2.0F, this.region48.packedWidth, this.region48.packedHeight, Settings.scale, Settings.scale, 0.0F);
            }
        }

        for (AbstractGameEffect effect : this.effect) {
            effect.render(sb);
        }
    }

    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        if (this.amount > 0) {
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount), x, y, this.fontScale, c);
        }
    }

    @Override
    public void dispose() {
        img.dispose();
    }
}
