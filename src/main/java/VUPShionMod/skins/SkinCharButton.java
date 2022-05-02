package VUPShionMod.skins;

import VUPShionMod.patches.CharacterSelectScreenPatches;
import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

public class SkinCharButton {
    public int index;
    public Texture body;
    public Texture bodyGlow;
    public Texture lockImg;
    public Texture lockCover;
    public Texture img;

    private float glowTimer = 0.0f;


    public Hitbox hb;

    public float cX;
    public float cY;
    public float current_x;
    public float current_y;
    public float outlineFix_x;
    public float outlineFix_y;
    public float outlineFix_Xscale;
    public float outlineFix_Yscale;
    public float scale;
    public Color color = Color.WHITE.cpy();

    public boolean locked = true;
    public boolean selected = false;

    public SkinCharButton(String img, int index) {
        this.body = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/charIconOutline.png");
        this.bodyGlow = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/charIconOutlineGlow.png");
        this.lockImg = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/lockIcon.png");
        this.lockCover = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/HeadLock.png");

        this.img = ImageMaster.loadImage(img);
        this.index = index;

        this.scale = 1.0f;
        this.hb = new Hitbox(260.0f * this.scale * Settings.scale, 154.0f * this.scale * Settings.scale);

    }

    public void setPos(int index) {
        if (Math.abs(this.index - index) > 3) {
            this.scale = 0.0f;
            this.current_x = 0.0f;
            this.current_y = 0.0f;
            this.color.a = 0.0f;
            return;
        }


        this.scale = MathHelper.uiLerpSnap(this.scale, (1.0f - 0.3f * Math.abs(this.index - index))
                * CharacterSelectScreenPatches.skinManager.scale);

        if (this.index - index > 0) {
            this.current_x = MathHelper.uiLerpSnap(this.current_x, 1.5f * -Math.abs(this.index - index)
                    * 120.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale);


            this.current_y = MathHelper.uiLerpSnap(this.current_y, 1.5f * (this.index - index)
                    * 40.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale);
        } else {
            this.current_x = MathHelper.uiLerpSnap(this.current_x, 1.5f * -Math.abs(this.index - index)
                    * 100.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale);


            this.current_y = MathHelper.uiLerpSnap(this.current_y, 1.5f * (this.index - index)
                    * 120.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale);
        }

        if (this.index - index == 0) {
            this.outlineFix_x = MathHelper.uiLerpSnap(this.outlineFix_x, 0.0f);
            this.outlineFix_y = MathHelper.uiLerpSnap(this.outlineFix_y, 0.0f);
            this.outlineFix_Xscale = MathHelper.uiLerpSnap(this.outlineFix_Xscale, 1.0f);
            this.outlineFix_Yscale = MathHelper.uiLerpSnap(this.outlineFix_Yscale, 1.0f);
        } else {
            this.outlineFix_x = MathHelper.uiLerpSnap(this.outlineFix_x, 8.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale);
            this.outlineFix_y = MathHelper.uiLerpSnap(this.outlineFix_y, 6.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale);
            this.outlineFix_Xscale = MathHelper.uiLerpSnap(this.outlineFix_Xscale, 0.95f);
            this.outlineFix_Yscale = MathHelper.uiLerpSnap(this.outlineFix_Yscale, 0.85f);
        }


        if (Math.abs(this.index - index) >= 2) {
            this.color.a = MathHelper.uiLerpSnap(this.color.a, 0.0f);
        } else {
            this.color.a = MathHelper.uiLerpSnap(this.color.a, 1.0f);
        }
    }

    public void update(CharacterOption option) {
        this.cX = CharacterSelectScreenPatches.skinManager.panel_x + 504.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;
        this.cY = 513.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;
        this.hb.width = 260.0f * this.scale * Settings.scale;
        this.hb.height = 154.0f * this.scale * Settings.scale;
        this.hb.move(this.cX + this.current_x, this.cY + this.current_y);


        if (CharacterSelectScreenPatches.skinManager.currentSkinCharacter != null) {
            if (Math.abs(CharacterSelectScreenPatches.skinManager.currentSkinCharacter.selectedCount - this.index) == 1) {
                if (InputHelper.justClickedLeft && this.hb.hovered) {
                    this.hb.clickStarted = true;
                    CardCrawlGame.sound.play("UI_CLICK_1");
                }

                if (this.hb.justHovered)
                    CardCrawlGame.sound.playV("UI_HOVER", 0.75f);

                this.hb.update();


                if (this.hb.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed()) {
                    this.hb.clicked = false;

                    for (AbstractSkin skin : CharacterSelectScreenPatches.skinManager.currentSkinCharacter.skins) {
                        skin.button.selected = false;
                    }

                    CharacterSelectScreenPatches.skinManager.currentSkinCharacter.selectedCount = this.index;

                    if (!this.locked)
                        CharacterSelectScreenPatches.skinManager.currentSkinCharacter.reskinCount = this.index;

                    CharacterSelectScreenPatches.skinManager.currentSkinCharacter.updateLabelString();

                    if (option != null) {
                        ReflectionHacks.setPrivate(option, CharacterOption.class, "charInfo",
                                CharacterSelectScreenPatches.skinManager.currentSkinCharacter.skins.get(
                                        CharacterSelectScreenPatches.skinManager.currentSkinCharacter.selectedCount
                                ).updateCharInfo(ReflectionHacks.getPrivate(option, CharacterOption.class, "charInfo")));
                    }

                    if(SkinManager.screen != null){
                        if(this.locked)
                            SkinManager.screen.confirmButton.hide();
                        else
                            SkinManager.screen.confirmButton.show();
                    }

                }


            }
        }

    }

    public void render(SpriteBatch sb) {
        float alpha = this.color.a;

        if (CharacterSelectScreenPatches.skinManager.currentSkinCharacter != null) {
            if (CharacterSelectScreenPatches.skinManager.currentSkinCharacter.selectedCount - this.index == 0
                    || (this.hb.hovered && Math.abs(CharacterSelectScreenPatches.skinManager.currentSkinCharacter.selectedCount - this.index) == 1)) {
                this.color.r = 1.0f;
                this.color.g = 1.0f;
                this.color.b = 1.0f;
            } else {
                this.color.r = Color.LIGHT_GRAY.r;
                this.color.g = Color.LIGHT_GRAY.g;
                this.color.b = Color.LIGHT_GRAY.b;

            }
        }

        sb.setColor(this.color);
        sb.draw(this.img,
                this.cX + this.current_x - 127.0f,
                this.cY + this.current_y - 75.0f,
                127.0F, 75.0f,
                254.0F, 150.0f,
                this.scale * Settings.scale * this.outlineFix_Xscale, this.scale * Settings.scale * this.outlineFix_Yscale,
                0.0f,
                0, 0,
                254, 150,
                false, false);

        if (this.locked) {
            this.color.a *= 0.5;
            sb.setColor(this.color);
            sb.draw(this.lockCover,
                    this.cX + this.current_x - 127.0f,
                    this.cY + this.current_y - 75.0f,
                    127.0F, 75.0f,
                    254.0F, 150.0f,
                    this.scale * Settings.scale * this.outlineFix_Xscale, this.scale * Settings.scale * this.outlineFix_Yscale,
                    0.0f,
                    0, 0,
                    254, 150,
                    false, false);

            this.color.a = alpha;
        }

        sb.draw(this.body,
                this.cX + this.current_x - this.outlineFix_x - 130.0f,
                this.cY + this.current_y - this.outlineFix_y - 77.0f,
                130.0F, 77.0f,
                260.0F, 154.0f,
                this.scale * Settings.scale, this.scale * Settings.scale,
                0.0f,
                0, 0,
                260, 154,
                false, false);

        sb.draw(this.body,
                this.cX + this.current_x + this.outlineFix_x - 130.0f,
                this.cY + this.current_y + this.outlineFix_y - 77.0f,
                130.0F, 77.0f,
                260.0F, 154.0f,
                this.scale * Settings.scale, this.scale * Settings.scale,
                0.0f,
                0, 0,
                260, 154,
                false, false);


        if (CharacterSelectScreenPatches.skinManager.currentSkinCharacter != null) {
            if (CharacterSelectScreenPatches.skinManager.currentSkinCharacter.selectedCount - this.index == 0) {
                this.glowTimer += Gdx.graphics.getDeltaTime() * 4.0f;
                this.color.a = ((float) Math.cos(this.glowTimer) + 1.0f) * 0.2f + 0.6f;
                sb.setColor(this.color);
                sb.draw(this.bodyGlow,
                        this.cX + this.current_x - 224.0f,
                        this.cY + this.current_y - 192.0f,
                        224.0F, 192.0f,
                        448.0F, 384.0f,
                        this.scale * Settings.scale * this.outlineFix_Xscale, this.scale * Settings.scale * this.outlineFix_Yscale,
                        0.0f,
                        0, 0,
                        448, 384,
                        false, false);
                this.color.a = alpha;
                sb.setColor(this.color);
            }
        }

        if (this.locked) {
            sb.draw(this.lockImg,
                    this.cX + this.current_x - 50.0f,
                    this.cY + this.current_y - 65.0f,
                    50.0F, 65.0f,
                    100.0F, 130.0f,
                    this.scale * Settings.scale * this.outlineFix_Xscale, this.scale * Settings.scale * this.outlineFix_Yscale,
                    0.0f,
                    0, 0,
                    100, 130,
                    false, false);
        }


        this.color.a = alpha;
        sb.setColor(Color.WHITE);

        this.hb.render(sb);

    }

}
