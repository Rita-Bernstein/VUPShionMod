package VUPShionMod.vfx.Atlas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;

public class AbstractAtlasGameEffect extends AbstractGameEffect {
    private Info info;
    public TextureAtlas atlas;
    private Animation curAnimation;
    public float scale = 1.0F;

    private float frameTimer = 0.0F;
    public float xPosition;
    public float yPosition;
    public boolean flipX = false;
    public boolean flipY = false;

    public AbstractAtlasGameEffect(boolean replace, String id, float x, float y, float originalX, float originalY, float scale, int delay, boolean loop,boolean flipX) {
        this.atlas = new TextureAtlas(Gdx.files.internal("VUPShionMod/img/vfx/Atlas/" + id + ".atlas"));

        this.xPosition = x;
        this.yPosition = y;
        this.info = new Info();
        this.info.fps = 60;
        this.info.id = id;

        Animation animation = new Animation();
        animation.xPosition = x;
        animation.yPosition = y;

        ArrayList<LayerAnimation> layerAnimationList = new ArrayList<>();

        LayerAnimation layerAnimation = new LayerAnimation();
        layerAnimation.spriteSheetId = id;
        layerAnimation.loop = loop;
        layerAnimation.atlas = atlas;
        layerAnimation.flipX = flipX;
        layerAnimation.flipY = false;

        ArrayList<Frame> frames = new ArrayList<>();
        for (int i = 0; i < atlas.getRegions().size; i++) {
            Frame frame = new Frame();
            frame.originalX = originalX;
            frame.originalY = originalY;
            frame.tint = Color.WHITE.cpy();
            frame.rotation = 0;
            frame.xScale = scale;
            frame.yScale = scale;
            frame.delay = delay;
            frames.add(frame);
        }

        layerAnimation.frames = frames;
        layerAnimationList.add(layerAnimation);

        animation.layerAnimations = layerAnimationList;
        this.curAnimation = animation;
    }


    public AbstractAtlasGameEffect(String id, float x, float y, float originalX, float originalY, float scale, int delay, boolean loop,boolean flipX) {
        this(false, id, x, y, originalX, originalY, scale, delay, loop,flipX);
    }

    public AbstractAtlasGameEffect(String id, float x, float y, float originalX, float originalY, float scale, int delay, boolean loop) {
        this(false, id, x, y, originalX, originalY, scale, delay, loop,false);
    }

    public AbstractAtlasGameEffect(String id, float x, float y, float originalX, float originalY, float scale, int delay) {
        this(false, id, x, y, originalX, originalY, scale, delay, false,false);
    }

    public AbstractAtlasGameEffect(String id, float x, float y, float originalX, float originalY, float scale, boolean loop) {
        this(false, id, x, y, originalX, originalY, scale, 3, loop,false);
    }

    public AbstractAtlasGameEffect(String id, float x, float y, float originalX, float originalY, float scale) {
        this(false, id, x, y, originalX, originalY, scale, 3, false,false);
    }

    public AbstractAtlasGameEffect(String id, float x, float y, float originalX, float originalY) {
        this(false, id, x, y, originalX, originalY, 2.1f * Settings.scale, 3, false,false);
    }

    public AbstractAtlasGameEffect(String id, float x, float y, float scale) {
        this(false, id, x, y, 0.0f, 0.0f, scale, 3, false,false);
    }

    public void update() {
        frameTimer += Gdx.graphics.getDeltaTime();

        if (frameTimer >= 1.0F / (float) this.info.fps) {
            if (curAnimation != null)
                for (LayerAnimation layerAnimation : curAnimation.layerAnimations) {
                    if (layerAnimation.loop || !layerAnimation.isDone)
                        layerAnimation.currDelay++;
                }
            frameTimer = 0;
        }

        curAnimation.xPosition = xPosition;
        curAnimation.yPosition = yPosition;
        curAnimation.update();

        if (isCurAnimationDone()) {
            this.isDone = true;
            dispose();
        }
    }

    public void render(SpriteBatch sb) {
        if (curAnimation != null) curAnimation.render(sb);
    }


    @Override
    public void dispose() {
        if(this.atlas != null) {
            atlas.dispose();
            atlas = null;
        }
    }

    public boolean isCurAnimationDone() {
        if (curAnimation != null) {
            return curAnimation.isDone;
        }
        return true;
    }

    public void resetAnimation() {
        if (curAnimation != null) {
            for (LayerAnimation layerAnimation : curAnimation.layerAnimations) {
                layerAnimation.currDelay = 0;
                layerAnimation.currFrameIndex = 0;
                layerAnimation.currFrame = layerAnimation.frames.get(0).makeCopy();
                layerAnimation.isDone = false;
            }
            curAnimation.isDone = false;
        }
        this.frameTimer = 0.0f;
        this.isDone = false;
    }

    public boolean isCurAnimation(Animation animation) {
        return curAnimation == animation;
    }

    public static class Info {
        int fps;
        String id;
    }
}
