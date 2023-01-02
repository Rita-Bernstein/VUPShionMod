package VUPShionMod.vfx.Monster.Boss;

import VUPShionMod.VUPShionMod;
import VUPShionMod.msic.VersusEffectData;
import VUPShionMod.util.ShionImageHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class VersusPortraitEffect extends AbstractGameEffect {
    private Texture mask;
    private Texture cover;
    private Texture pix;

    private Texture portraitP1;
    private Texture portraitP2;
    private Texture portraitShadow;
    private float portraitPosXFix = 0.0f;
    private float portraitPosYFix = 0.0f;
    private final float portraitPosYFixV = 10.0f;


    private Texture player1Icon;
    private Texture player2Icon;
    private float playerIconPosXFix = 0.0f;
    private float playerIconPosYFix = 0.0f;
    private float playerIconTimer = 0.5f;

    private Texture player1Name;
    private Texture player2Name;
    private float playerNamePosXFix = 0.0f;
    private final float playerNamePosYFix = 0.0f;
    private float playerNameTimer = 0.5f;

    private Color renderColor;

    private float coverAlphaColorTimer = 0.5f;
    private float whitePixScaleTimer = 0.0f;
    private float whitePixColorTimer = 0.5f;

    private float centerXFix = 0.0f;
    private float centerYFix = 0.0f;


    private float outAllScale = 1.0f;

    public VersusPortraitEffect(String IDp2) {
        this.duration = 3.0f;
        this.mask = ImageMaster.loadImage("VUPShionMod/img/vfx/VersusEffect/portraitMask.png");
        this.cover = ImageMaster.loadImage("VUPShionMod/img/vfx/VersusEffect/cover.png");
        this.pix = ImageMaster.loadImage("VUPShionMod/img/vfx/VersusEffect/pix.png");


        this.portraitP1 = ImageMaster.loadImage(VUPShionMod.versusEffectData.get(VersusEffectData.getPlayerID()).portrait);
        this.portraitP2 = ImageMaster.loadImage(VUPShionMod.versusEffectData.get(IDp2).portrait);

        this.portraitShadow = ImageMaster.loadImage("VUPShionMod/img/vfx/VersusEffect/portraitShadow.png");


        this.player1Icon = ImageMaster.loadImage("VUPShionMod/img/vfx/VersusEffect/player1.png");
        this.player2Icon = ImageMaster.loadImage("VUPShionMod/img/vfx/VersusEffect/player2.png");

        this.player1Name = ImageMaster.loadImage(VUPShionMod.versusEffectData.get(VersusEffectData.getPlayerID()).playerNameImg);
        this.player2Name = ImageMaster.loadImage(VUPShionMod.versusEffectData.get(IDp2).playerNameImg);

        this.renderColor = Color.WHITE.cpy();
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration <= 0) {
            this.isDone = true;
            return;
        }

        this.coverAlphaColorTimer -= Gdx.graphics.getDeltaTime();

        this.whitePixScaleTimer += Gdx.graphics.getDeltaTime() * 280.0f;

        if (this.whitePixScaleTimer >= 140.0f) {
            this.whitePixColorTimer -= Gdx.graphics.getDeltaTime();

            double angle = Math.tanh(517.0f / 135.0f);

            this.centerXFix += Gdx.graphics.getDeltaTime() * 6.0f * Settings.scale;
            this.centerYFix += Gdx.graphics.getDeltaTime() * 7.2f * Settings.scale;

            this.playerIconTimer -= Gdx.graphics.getDeltaTime();
            if (this.playerIconTimer >= 0) {
                double angle12 = Math.PI / 4 - Math.tanh(61.0f / 168.0f);
                this.playerIconPosXFix += Gdx.graphics.getDeltaTime() * 72.0f * Settings.scale * Math.sin(angle12);
                this.playerIconPosYFix += Gdx.graphics.getDeltaTime() * 72.0f * Settings.scale * Math.cos(angle12);
            }

            this.playerNameTimer -= Gdx.graphics.getDeltaTime();
            if (this.playerNameTimer >= 0) {
                this.playerNamePosXFix += Gdx.graphics.getDeltaTime() * 210.0f * Settings.scale;
//                this.playerNamePosXFix += Gdx.graphics.getDeltaTime() * 72.0f * Settings.scale;
            }


            portraitPosXFix += Gdx.graphics.getDeltaTime() * portraitPosYFixV * Settings.scale * Math.cos(angle);
            portraitPosYFix += Gdx.graphics.getDeltaTime() * portraitPosYFixV * Settings.scale * Math.sin(angle);

        }

        if (this.duration <= 0.6f) {
            this.outAllScale -= 3.0f * Gdx.graphics.getDeltaTime();
            if (outAllScale < 0.0f)
                outAllScale = 0.0f;
        }


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 771);
        renderPortrait(sb);
        renderCover(sb);
        renderOutLines(sb);
    }


    private void renderOutLines(SpriteBatch sb) {
        if (this.duration < 0.4f) {
            sb.setColor(Color.WHITE);
            sb.draw(this.pix, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() + this.centerXFix - 3.0f,
                    Settings.HEIGHT * 0.5f + this.centerYFix,
                    3.0F, 3.0f,
                    6.0F, 6.0f,
                    2.8f * Settings.scale * this.duration * 2.5f, 400.0f * Settings.scale * (0.4f - this.duration) * 2.5f,
                    0.0f,
                    0, 0,
                    6, 6,
                    false, false);


            sb.draw(this.pix, 480.0f * Settings.scale * getPosXScale() - this.centerXFix - 3.0f,
                    Settings.HEIGHT * 0.5f + this.centerYFix,
                    3.0F, 3.0f,
                    6.0F, 6.0f,
                    2.8f * Settings.scale * this.duration * 2.5f, 400.0f * Settings.scale * (0.4f - this.duration) * 2.5f,
                    0.0f,
                    0, 0,
                    6, 6,
                    false, false);
        }
    }

    private void renderPlayerIcon(SpriteBatch sb) {
        if (this.whitePixScaleTimer >= 140.0f) {
            sb.setColor(Color.WHITE);

            sb.draw(this.player2Icon, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 16.0f * Settings.scale + this.centerXFix - this.playerIconPosXFix - 100.0f,
                    Settings.HEIGHT * 0.5f + 225.0f * Settings.scale + this.centerYFix + this.playerIconPosYFix - 50.0f,
                    100.0F, 50.0f,
                    200.0F, 100.0f,
                    outAllScale * Settings.scale, Settings.scale,
                    20.0f,
                    0, 0,
                    200, 100,
                    false, false);


            sb.draw(this.player1Icon, 480.0f * Settings.scale * getPosXScale() + 16.0f * Settings.scale - this.centerXFix + this.playerIconPosXFix - 100.0f,
                    Settings.HEIGHT * 0.5f + 225.0f * Settings.scale + this.centerYFix + this.playerIconPosYFix - 50.0f,
                    100.0F, 50.0f,
                    200.0F, 100.0f,
                    outAllScale * Settings.scale, Settings.scale,
                    -20.0f,
                    0, 0,
                    200, 100,
                    false, false);
        }
    }

    private void renderPlayerName(SpriteBatch sb) {
        if (this.whitePixScaleTimer >= 140.0f) {
            sb.setColor(Color.WHITE);

            sb.draw(this.player2Name, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() + this.centerXFix + this.playerNamePosXFix * outAllScale,
                    Settings.HEIGHT * 0.5f + 230 * Settings.scale + this.centerYFix + this.playerNamePosYFix,
                    0.0f, 0.0f,
                    player2Name.getWidth(), player2Name.getHeight(),
                    Settings.scale, outAllScale * Settings.scale,
                    -90.0f,
                    0, 0,
                    player2Name.getWidth(), player2Name.getHeight(),
                    false, false);


            sb.draw(this.player1Name, 480.0f * Settings.scale * getPosXScale() - this.centerXFix - this.playerNamePosXFix * outAllScale,
                    Settings.HEIGHT * 0.5f - 230 * Settings.scale + this.centerYFix + this.playerNamePosYFix,
                    0.0F, 0.0f,
                    player2Name.getWidth(), player2Name.getHeight(),
                    Settings.scale, outAllScale * Settings.scale,
                    90.0f,
                    0, 0,
                    player2Name.getWidth(), player2Name.getHeight(),
                    false, false);
        }
    }

    private void renderPortrait(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        if (this.whitePixScaleTimer >= 140.0f) {
//颜色底板

            this.renderColor = Color.SCARLET.cpy();

            sb.setColor(this.renderColor);
            sb.draw(this.mask, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 100.0f,
                    Settings.HEIGHT * 0.5f - 260.0f,
                    100.0F, 260.0f,
                    200.0F, 520.0f,
                    outAllScale * Settings.scale, Settings.scale,
                    0.0f,
                    0, 0,
                    200, 520,
                    false, false);


            this.renderColor = Color.SKY.cpy();

            sb.setColor(this.renderColor);
            sb.draw(this.mask, 480.0f * Settings.scale * getPosXScale() - 100.0f,
                    Settings.HEIGHT * 0.5f - 260.0f,
                    100.0F, 260.0f,
                    200.0F, 520.0f,
                    outAllScale * Settings.scale, Settings.scale,
                    0.0f,
                    0, 0,
                    200, 520,
                    true, false);


            renderPlayerIcon(sb);
            renderPlayerName(sb);

//            立绘阴影
            sb.setColor(Color.WHITE);
            sb.draw(this.portraitShadow, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() + centerXFix - 120.0f,
                    Settings.HEIGHT * 0.5f + centerYFix - 280.0f,
                    120.0F, 280.0f,
                    240.0F, 560.0f,
                    outAllScale * Settings.scale, Settings.scale,
                    0.0f,
                    0, 0,
                    240, 560,
                    false, false);


            sb.draw(this.portraitShadow, 480.0f * Settings.scale * getPosXScale() - centerXFix - 120.0f,
                    Settings.HEIGHT * 0.5f + centerYFix - 280.0f,
                    120.0F, 280.0f,
                    240.0F, 560.0f,
                    outAllScale * Settings.scale, Settings.scale,
                    0.0f,
                    0, 0,
                    240, 560,
                    true, false);


            this.renderColor = Color.WHITE.cpy();
            sb.setColor(Color.WHITE);

            //立绘
            ShionImageHelper.renderMaskedImage(sb, Color.WHITE,
                    sb1 -> sb1.draw(this.portraitP2, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() + portraitPosXFix - portraitP2.getWidth() * 0.5f,
                            Settings.HEIGHT * 0.5f - portraitPosYFix - portraitP2.getHeight() * 0.5f,
                            portraitP2.getWidth() * 0.5f, portraitP2.getHeight() * 0.5f,
                            portraitP2.getWidth(), portraitP2.getHeight(),
                            Settings.scale, 1.0f * Settings.scale,
                            0.0f,
                            0, 0,
                            portraitP2.getWidth(), portraitP2.getHeight(),
                            false, false),

                    sb2 -> sb2.draw(this.mask, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() + centerXFix - 100.0f,
                            Settings.HEIGHT * 0.5f + centerYFix - 260.0f,
                            100.0F, 260.0f,
                            200.0F, 520.0f,
                            outAllScale * Settings.scale, Settings.scale,
                            0.0f,
                            0, 0,
                            200, 520,
                            false, false));

            ShionImageHelper.renderMaskedImage(sb, Color.WHITE,
                    sb1 -> sb1.draw(this.portraitP1, 480.0f * Settings.scale * getPosXScale() - portraitPosXFix - portraitP1.getWidth() * 0.5f,
                            Settings.HEIGHT * 0.5f - portraitPosYFix - portraitP1.getHeight() * 0.5f,
                            portraitP1.getWidth() * 0.5f, portraitP1.getHeight() * 0.5f,
                            portraitP1.getWidth(), portraitP1.getHeight(),
                            1.0f * Settings.scale, 1.0f * Settings.scale,
                            0.0f,
                            0, 0,
                            portraitP1.getWidth(), portraitP1.getHeight(),
                            true, false),

                    sb2 -> sb2.draw(this.mask, 480.0f * Settings.scale * getPosXScale() - centerXFix - 100.0f,
                            Settings.HEIGHT * 0.5f + centerYFix - 260.0f,
                            100.0F, 260.0f,
                            200.0F, 520.0f,
                            outAllScale * Settings.scale, Settings.scale,
                            0.0f,
                            0, 0,
                            200, 520,
                            true, false));

        }


//        白点遮挡
        if (this.whitePixColorTimer >= 0) {
            this.renderColor.a = this.whitePixColorTimer * 2;
            sb.setColor(this.renderColor);

            ShionImageHelper.renderMaskedImage(sb, this.renderColor,
                    sb1 -> sb1.draw(this.pix, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() + this.centerXFix - 3.0f - 81.0f * Settings.scale,
                            Settings.HEIGHT * 0.5f + this.centerYFix - 3.0f - 248.0f * Settings.scale,
                            3.0F, 3.0f,
                            6.0F, 6.0f,
                            600.0f * Settings.scale, whitePixScaleTimer * 1.5f * Settings.scale,
                            -23.0f,
                            0, 0,
                            6, 6,
                            false, false),

                    sb2 -> sb2.draw(this.mask, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() + centerXFix - 100.0f,
                            Settings.HEIGHT * 0.5f + this.centerYFix - 260.0f,
                            100.0F, 260.0f,
                            200.0F, 520.0f,
                            Settings.scale, Settings.scale,
                            0.0f,
                            0, 0,
                            200, 520,
                            false, false));


            ShionImageHelper.renderMaskedImage(sb, this.renderColor,
                    sb1 -> sb1.draw(this.pix, 480.0f * Settings.scale * getPosXScale() - 3.0f - this.centerXFix + 81.0f * Settings.scale,
                            Settings.HEIGHT * 0.5f + this.centerYFix - 3.0f - 248.0f * Settings.scale,
                            3.0F, 3.0f,
                            6.0F, 6.0f,
                            600.0f * Settings.scale, whitePixScaleTimer * 1.5f * Settings.scale,
                            23.0f,
                            0, 0,
                            6, 6,
                            false, false),

                    sb2 -> sb2.draw(this.mask, 480.0f * Settings.scale * getPosXScale() - centerXFix - 100.0f,
                            Settings.HEIGHT * 0.5f + this.centerYFix - 260.0f,
                            100.0F, 260.0f,
                            200.0F, 520.0f,
                            Settings.scale, Settings.scale,
                            0.0f,
                            0, 0,
                            200, 520,
                            true, false));
        }

    }

    private void renderCover(SpriteBatch sb) {
//        始终存在的外框
        sb.setColor(Color.WHITE);


        Texture tmp = ShionImageHelper.getScreenTexture(sb,
                sb1 -> sb1.draw(this.cover, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() + centerXFix - 100.0f,
                        Settings.HEIGHT * 0.5f + centerYFix - 260.0f,
                        100.0F, 260.0f,
                        200.0F, 520.0f,
                        outAllScale * Settings.scale, Settings.scale,
                        0.0f,
                        0, 0,
                        200, 520,
                        false, false));

        ShionImageHelper.renderScreenTexture(sb, tmp);
        ShionImageHelper.renderScreenTexture(sb, tmp, true);


        if (this.coverAlphaColorTimer >= 0) {
//        白色渐隐
            this.renderColor.a = this.coverAlphaColorTimer * 2;

            Texture tmp2 = ShionImageHelper.getScreenAlphaTexture(sb, this.renderColor,
                    spriteBatch -> spriteBatch.draw(this.cover, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() + centerXFix - 100.0f,
                            Settings.HEIGHT * 0.5f + centerYFix - 260.0f,
                            100.0F, 260.0f,
                            200.0F, 520.0f,
                            Settings.scale, Settings.scale,
                            0.0f,
                            0, 0,
                            200, 520,
                            false, false));

            ShionImageHelper.renderScreenTexture(sb, tmp2);
            ShionImageHelper.renderScreenTexture(sb, tmp2, true);
        }
        sb.setColor(Color.WHITE);
    }

    private float getPosXScale() {
        return 1.0f;
    }

    @Override
    public void dispose() {
        if (mask != null) {
            this.mask.dispose();
            this.mask = null;
        }

        if (this.cover != null) {
            this.cover.dispose();
            this.cover = null;
        }


        if (this.pix != null) {
            this.pix.dispose();
            this.pix = null;
        }


        if (this.portraitP1 != null) {
            this.portraitP1.dispose();
            this.portraitP1 = null;
        }

        if (this.portraitP2 != null) {
            this.portraitP2.dispose();
            this.portraitP2 = null;
        }

        if (this.portraitShadow != null) {
            this.portraitShadow.dispose();
            this.portraitShadow = null;
        }

        if (this.player1Icon != null) {
            this.player1Icon.dispose();
            this.player1Icon = null;
        }

        if (this.player2Icon != null) {
            this.player2Icon.dispose();
            this.player2Icon = null;
        }

        if (this.player1Name != null) {
            this.player1Name.dispose();
            this.player1Name = null;
        }

        if (this.player2Name != null) {
            this.player2Name.dispose();
            this.player2Name = null;
        }
    }
}
