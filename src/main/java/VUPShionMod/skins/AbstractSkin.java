package VUPShionMod.skins;

import VUPShionMod.util.SaveHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.BurningBlood;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.core.AbstractCreature.sr;

public abstract class AbstractSkin {
    public String skinId;
    public Texture portrait_IMG;


    public TextureAtlas portraitAtlas = null;
    public Skeleton portraitSkeleton;
    public AnimationState portraitState;
    public AnimationStateData portraitStateData;
    public SkeletonData portraitData;

    public String portraitAtlasPath = null;

    public String name;
    public String flavorText = null;
    public String level;
    public String unlockString;

    public boolean unlock = false;

    public String SHOULDER1;
    public String SHOULDER2;
    public String CORPSE;

    public String atlasURL;
    public String jsonURL;
    public float renderScale;

    public SkinCharButton button;

    public AbstractSkin(String skinId, int index) {
        this.skinId = skinId;
        this.button = new SkinCharButton("VUPShionMod/img/ui/Skin/head/" + skinId + ".png", index);
    }

    public void onUnlock() {
        this.unlock = true;
        this.button.locked = false;
        SaveHelper.saveSkins();
    }

    public void initialize() {

    }


    public void loadAnimation(float scale) {
        if (portraitAtlas == null)
            portraitAtlas = new TextureAtlas(Gdx.files.internal(portraitAtlasPath + ".atlas"));
        SkeletonJson json = new SkeletonJson(portraitAtlas);
        json.setScale(Settings.scale * scale);
        portraitData = json.readSkeletonData(Gdx.files.internal(portraitAtlasPath + ".json"));

        portraitSkeleton = new Skeleton(portraitData);
        portraitSkeleton.setColor(Color.WHITE);
        portraitStateData = new AnimationStateData(portraitData);
        portraitState = new AnimationState(portraitStateData);
        portraitStateData.setDefaultMix(0.2F);

        portraitState.setTimeScale(1.0f);
    }


    public void setAnimation() {
        SaveHelper.loadSettings();
    }


    public void renderPortrait(SpriteBatch sb) {
        if (this.portraitAtlasPath != null) {
            portraitState.update(Gdx.graphics.getDeltaTime());
            portraitState.apply(portraitSkeleton);
            portraitSkeleton.updateWorldTransform();
            setPos();
            portraitSkeleton.setColor(Color.WHITE.cpy());
            portraitSkeleton.setFlip(false, false);


            skeletonRender(sb);
        } else {
            {
                if (Settings.isSixteenByTen) {
                    sb.draw(this.portrait_IMG, Settings.WIDTH / 2.0F - 960.0F, Settings.HEIGHT / 2.0F - 600.0F, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1200, false, false);

                } else if (Settings.isFourByThree) {
                    sb.draw(this.portrait_IMG, Settings.WIDTH / 2.0F - 960.0F, Settings.HEIGHT / 2.0F - 600.0F + 0.0f, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.yScale, Settings.yScale, 0.0F, 0, 0, 1920, 1200, false, false);

                } else if (Settings.isLetterbox) {
                    sb.draw(this.portrait_IMG, Settings.WIDTH / 2.0F - 960.0F, Settings.HEIGHT / 2.0F - 600.0F + 0.0f, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.xScale, Settings.xScale, 0.0F, 0, 0, 1920, 1200, false, false);

                } else {
                    sb.draw(this.portrait_IMG, Settings.WIDTH / 2.0F - 960.0F, Settings.HEIGHT / 2.0F - 600.0F + 0.0f, 960.0F, 600.0F, 1920.0F, 1200.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1200, false, false);
                }
            }
        }
    }

    public void setPos() {
    }

    public void safeSwitch() {
    }

    public void skeletonRender(SpriteBatch sb) {
        sb.end();
        CardCrawlGame.psb.begin();
        sr.draw(CardCrawlGame.psb, portraitSkeleton);
        CardCrawlGame.psb.end();
        sb.begin();
    }


    public void dispose() {
        if (this.portraitAtlas != null) {
            this.portraitAtlas.dispose();
            this.portraitAtlas = null;
        }

        if (portrait_IMG != null) {
            portrait_IMG.dispose();
            portrait_IMG = null;
        }
    }


    public CharSelectInfo updateCharInfo(CharSelectInfo info) {
        return info;
    }

    public ArrayList<String> getStartingRelic() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(BurningBlood.ID);
        return retVal;
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike_Red.ID);
        return retVal;
    }

    public void postCreateStartingDeck(CardGroup cardGroup) {

    }

    public void unlock() {
        this.unlock = true;
        this.button.locked = false;
    }

}


