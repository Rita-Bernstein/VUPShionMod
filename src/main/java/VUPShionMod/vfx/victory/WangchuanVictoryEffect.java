package VUPShionMod.vfx.victory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import static com.megacrit.cardcrawl.core.AbstractCreature.sr;

public class WangchuanVictoryEffect extends AbstractGameEffect {

    private float speed;

    private static TextureAtlas Atlas = null;
    private static com.esotericsoftware.spine.Skeleton Skeleton;
    private static AnimationState State;
    private static AnimationStateData StateData;
    private static SkeletonData Data;

    private Texture img;

    public WangchuanVictoryEffect(float speed) {
        this.speed = speed;
        this.duration = 3.0f;

        loadanimation(this.speed);

        this.img = ImageMaster.loadImage("VUPShionMod/characters/WangChuan/victory/victory.png");
    }

    public WangchuanVictoryEffect() {
        this(1.0f);
    }

    private static void loadanimation(float timeScale) {
//        Atlas = new TextureAtlas(Gdx.files.internal("VUPShionMod/characters/Shion/victory/ShionVictory.atlas"));
//        SkeletonJson json = new SkeletonJson(Atlas);
//        json.setScale(Settings.scale / 1.0F);
//        Data = json.readSkeletonData(Gdx.files.internal("VUPShionMod/characters/Shion/victory/ShionVictory.json"));
//
//
//        Skeleton = new Skeleton(Data);
//        Skeleton.setColor(Color.WHITE);
//        StateData = new AnimationStateData(Data);
//        State = new AnimationState(StateData);
//        StateData.setDefaultMix(0.2F);
//
//        State.setTimeScale(1.0f * timeScale);
//        Skeleton.setPosition(-20.0f * Settings.scale, 10.0f * Settings.scale);
//
//        State.setAnimation(0, "animation", true);
    }


    public void update() {
//        this.duration -= Gdx.graphics.getDeltaTime();
//        if (this.duration < 0.0F) {
//            this.isDone = true;
//        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        sb.draw(this.img,
                Settings.WIDTH / 2.0f - 960.0F, Settings.HEIGHT/2.0f - 600.0F,
                960.0F, 600.0F,
                1920.0F, 1200.0F,
                Settings.scale, Settings.scale,
                0.0f,
                0, 0,
                1920, 1200,
                false, false);

//        State.update(Gdx.graphics.getDeltaTime());
//        State.apply(Skeleton);
//        Skeleton.updateWorldTransform();
//        Skeleton.setColor(Color.WHITE);
//        Skeleton.setFlip(false, false);
//
//
//        sb.end();
//        CardCrawlGame.psb.begin();
//        sr.draw(CardCrawlGame.psb, Skeleton);
//        CardCrawlGame.psb.end();
//        sb.begin();

    }

    public void dispose() {
        Atlas.dispose();

        if(img != null){
            img.dispose();
            img = null;
        }
    }
}
