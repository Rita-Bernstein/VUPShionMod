package VUPShionMod.vfx.Monster.Boss;

import VUPShionMod.VUPShionMod;
import VUPShionMod.msic.VersusEffectData;
import VUPShionMod.util.ShionImageHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class VersusLineEffect extends AbstractGameEffect {
    private Texture pix;
    private Texture circle;
    private Texture circleMask;
    private Texture circleBlur;
    private Texture vsTitle;
    private Texture vsTitleName;

    private float circleBlurTimer = 1.0f;
    private float circleBlurScale = 0.0f;
    private float circleMaskTimer = 1.5f;
    private float circleMaskScale = 0.0f;

    private Texture playerClassIcon1;
    private Texture playerClassIcon2;

    private float playerClassIconScale = 4.0f;
    private final Color playerClassIconColor = Color.WHITE.cpy();

    private float playerClassIconBlurScale = 1.0f;
    private final Color playerClassIconBlurColor = Color.WHITE.cpy();
    private boolean triggerPlayerClassShake = false;

    private float playerClassIconLineScale = 1.0f;
    private float playerClassIconOutScale = 1.0f;

    private static final float time = 0.8f;
    private static final float muti = 2 / time;

    private float time12 = time;
    private float time34 = time;
    private float time56 = time;

    private final Color renderColor = Color.WHITE.cpy();

    public VersusLineEffect(String IDp2) {
        this.duration = 3.0f;
        this.pix = ImageMaster.loadImage("VUPShionMod/img/vfx/VersusEffect/pix.png");
        this.circle = ImageMaster.loadImage("VUPShionMod/img/vfx/VersusEffect/circle.png");
        this.circleMask = ImageMaster.loadImage("VUPShionMod/img/vfx/VersusEffect/circleMask.png");
        this.circleBlur = ImageMaster.loadImage("VUPShionMod/img/vfx/VersusEffect/circleBlur.png");

        this.vsTitle = ImageMaster.loadImage("VUPShionMod/img/vfx/VersusEffect/vsTitle.png");

        this.vsTitleName = ImageMaster.loadImage(VUPShionMod.versusEffectData.get(IDp2).vsTitleName);

        this.playerClassIcon1 = ImageMaster.loadImage(VUPShionMod.versusEffectData.get(VersusEffectData.getPlayerID()).playerClassIcon);
        this.playerClassIcon2 = ImageMaster.loadImage(VUPShionMod.versusEffectData.get(IDp2).playerClassIcon);


        playerClassIconColor.a = 0.0f;
        playerClassIconBlurColor.a = 0.0f;
    }

    @Override
    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration <= 0) {
            this.isDone = true;
            return;
        }

        circleBlurTimer -= Gdx.graphics.getDeltaTime();
        if (circleBlurTimer >= 0.0f) {
            circleBlurScale += 20.0f * Gdx.graphics.getDeltaTime();
        }


        circleMaskTimer -= Gdx.graphics.getDeltaTime();
        if (circleMaskTimer >= 0.0f) {
            circleMaskScale += 15.0f * Gdx.graphics.getDeltaTime();
        }

        if (this.duration <= 2.2f) {
            playerClassIconLineScale -= 2.0f * Gdx.graphics.getDeltaTime();
            if (this.playerClassIconLineScale < 0.0f)
                this.playerClassIconLineScale = 0.0f;
        }

        if (this.duration <= 2.0f) {
            playerClassIconColor.a += 5.0f * Gdx.graphics.getDeltaTime();

            if (playerClassIconColor.a > 1.0f) {
                playerClassIconColor.a = 1.0f;
            }

            playerClassIconScale -= 10.0f * Gdx.graphics.getDeltaTime();
            if (playerClassIconScale < 1.0f)
                playerClassIconScale = 1.0f;
        }

        if (this.duration <= 1.8f) {
            if (!this.triggerPlayerClassShake) {
                CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, true);
                playerClassIconBlurColor.a = 0.7f;
                this.triggerPlayerClassShake = true;
            }

            playerClassIconBlurScale += 5.0f * Gdx.graphics.getDeltaTime();
            if (playerClassIconBlurScale > 2.0f) {
                playerClassIconBlurScale = 2.0f;
            }

            playerClassIconBlurColor.a -= 0.7f * Gdx.graphics.getDeltaTime();
            if (playerClassIconBlurColor.a < 0.0f) {
                playerClassIconBlurColor.a = 0.0f;
            }
        }

        if (this.duration <= 0.4f) {
            playerClassIconOutScale -= 3.0f * Gdx.graphics.getDeltaTime();
            if (this.playerClassIconOutScale <= 0.0f) {
                playerClassIconOutScale = 0.0f;
            }
        }


        if (this.duration <= 1.8f) {
            this.time12 -= Gdx.graphics.getDeltaTime();
        }

        if (this.duration <= 1.5f) {
            this.time34 -= Gdx.graphics.getDeltaTime();
        }

        if (this.duration <= 1.2f) {
            this.time56 -= Gdx.graphics.getDeltaTime();
        }

    }


    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.setBlendFunction(770, 771);

        renderOpen(sb);
        renderLines(sb);

    }


    private void renderOpen(SpriteBatch sb) {

        ShionImageHelper.renderMaskedImage(sb, Color.WHITE, sb1 -> {
                    sb1.setColor(Color.WHITE);
                    sb1.draw(this.vsTitle, Settings.WIDTH * 0.5f - 100.0f,
                            Settings.HEIGHT * 0.5f - 100.0f,
                            100.0F, 100.0F,
                            200.0F, 200.0F,
                            Settings.scale, playerClassIconOutScale * Settings.scale,
                            0.0f,
                            0, 0,
                            200, 200,
                            false, false);

                    sb1.draw(this.vsTitleName, Settings.WIDTH * 0.5f - vsTitleName.getWidth() * 0.5f,
                            Settings.HEIGHT * 0.5f - vsTitleName.getHeight(),
                            vsTitleName.getWidth() * 0.5f, vsTitleName.getHeight(),
                            vsTitleName.getWidth(), vsTitleName.getHeight(),
                            Settings.scale, playerClassIconOutScale * Settings.scale,
                            0.0f,
                            0, 0,
                            vsTitleName.getWidth(), vsTitleName.getHeight(),
                            false, false);

                    sb1.draw(this.circle, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 150.0f,
                            Settings.HEIGHT * 0.5f - 150.0f,
                            150.0F, 150.0F,
                            300.0F, 300.0F,
                            Settings.scale, playerClassIconOutScale * Settings.scale,
                            0.0f,
                            0, 0,
                            300, 300,
                            false, false);


                    sb1.draw(this.circle, 480.0f * Settings.scale * getPosXScale() - 150.0f,
                            Settings.HEIGHT * 0.5f - 150.0f,
                            150.0F, 150.0F,
                            300.0F, 300.0F,
                            Settings.scale, playerClassIconOutScale * Settings.scale,
                            0.0f,
                            0, 0,
                            300, 300,
                            false, false);


                    sb1.draw(this.pix, Settings.WIDTH * 0.5f + 100.0f * Settings.scale,
                            Settings.HEIGHT * 0.5f - 3.0f,
                            0.0F, 3.0f,
                            6.0F, 6.0f,
                            40.0f * Settings.scale * playerClassIconLineScale, 1.5f * Settings.scale,
                            0.0f,
                            0, 0,
                            6, 6,
                            false, false);


                    sb1.draw(this.pix, Settings.WIDTH * 0.5f - 100.0f * Settings.scale - 6.0f,
                            Settings.HEIGHT * 0.5f - 3.0f,
                            6.0F, 3.0f,
                            6.0F, 6.0f,
                            40.0f * Settings.scale * playerClassIconLineScale, 1.5f * Settings.scale,
                            0.0f,
                            0, 0,
                            6, 6,
                            false, false);

                    sb1.setColor(this.playerClassIconBlurColor);
                    sb1.draw(this.playerClassIcon2, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - playerClassIcon2.getWidth() * 0.5f,
                            Settings.HEIGHT * 0.5f - playerClassIcon2.getHeight() * 0.5f,
                            playerClassIcon2.getWidth() * 0.5f, playerClassIcon2.getHeight() * 0.5f,
                            playerClassIcon2.getWidth(), playerClassIcon2.getHeight(),
                            playerClassIconBlurScale * Settings.scale, playerClassIconBlurScale * Settings.scale,
                            0.0f,
                            0, 0,
                            playerClassIcon2.getWidth(), playerClassIcon2.getHeight(),
                            false, false);


                    sb1.draw(this.playerClassIcon1, 480.0f * Settings.scale * getPosXScale() - playerClassIcon1.getWidth() * 0.5f,
                            Settings.HEIGHT * 0.5f - playerClassIcon1.getHeight() * 0.5f,
                            playerClassIcon1.getWidth() * 0.5f, playerClassIcon1.getHeight() * 0.5f,
                            playerClassIcon1.getWidth(), playerClassIcon1.getHeight(),
                            playerClassIconBlurScale * Settings.scale, playerClassIconBlurScale * Settings.scale,
                            0.0f,
                            0, 0,
                            playerClassIcon1.getWidth(), playerClassIcon1.getHeight(),
                            false, false);


                    sb1.setColor(this.playerClassIconColor);
                    sb1.draw(this.playerClassIcon2, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - playerClassIcon2.getWidth() * 0.5f,
                            Settings.HEIGHT * 0.5f - playerClassIcon1.getHeight() * 0.5f,
                            playerClassIcon2.getWidth() * 0.5f, playerClassIcon2.getHeight() * 0.5f,
                            playerClassIcon2.getWidth(), playerClassIcon2.getHeight(),
                            playerClassIconScale * Settings.scale, playerClassIconOutScale * playerClassIconScale * Settings.scale,
                            0.0f,
                            0, 0,
                            playerClassIcon2.getWidth(), playerClassIcon2.getHeight(),
                            false, false);


                    sb1.draw(this.playerClassIcon1, 480.0f * Settings.scale * getPosXScale() - playerClassIcon1.getWidth() * 0.5f,
                            Settings.HEIGHT * 0.5f - playerClassIcon1.getHeight() * 0.5f,
                            playerClassIcon1.getWidth() * 0.5f, playerClassIcon1.getHeight() * 0.5f,
                            playerClassIcon1.getWidth(), playerClassIcon1.getHeight(),
                            playerClassIconScale * Settings.scale, playerClassIconOutScale * playerClassIconScale * Settings.scale,
                            0.0f,
                            0, 0,
                            playerClassIcon1.getWidth(), playerClassIcon1.getHeight(),
                            false, false);

                    sb1.setColor(Color.WHITE);
                },
                sb2 -> sb2.draw(this.circleMask, Settings.WIDTH * 0.5f - 150.0f,
                        Settings.HEIGHT * 0.5f - 150.0f,
                        150.0F, 150.0F,
                        300.0F, 300.0F,
                        circleMaskScale * 0.5f * Settings.scale, circleMaskScale * 0.5f * Settings.scale,
                        0.0f,
                        0, 0,
                        300, 300,
                        false, false));


        this.renderColor.a = 0.8f;
        sb.setColor(this.renderColor);
        sb.draw(this.circleBlur, Settings.WIDTH * 0.5f - 150.0f,
                Settings.HEIGHT * 0.5f - 150.0f,
                150.0F, 150.0F,
                300.0F, 300.0F,
                circleBlurScale * Settings.scale, circleBlurScale * Settings.scale,
                0.0f,
                0, 0,
                300, 300,
                false, false);


        if (this.duration <= 0.4f) {
            sb.draw(this.pix, Settings.WIDTH * 0.5f - 3.0f,
                    Settings.HEIGHT * 0.5f - 3.0f,
                    3.0F, 3.0f,
                    6.0F, 6.0f,
                    400.0f * Settings.scale * (0.4f - this.duration) * 2.5f, 2.8f * Settings.scale * this.duration * 2.5f,
                    0.0f,
                    0, 0,
                    6, 6,
                    false, false);
        }

    }


    private void renderLines(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.setBlendFunction(770, 771);
//P2----------------------------------------
//P2----------------------------------------
//P2----------------------------------------

        Texture tmp = ShionImageHelper.getScreenTexture(sb, spriteBatch -> {
            //        line 1 左上斜边 - 右上到左下
            double angle12 = Math.tanh(61.0f / 168.0f);


            spriteBatch.draw(this.pix, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 3.0f - 16.0f * Settings.scale
                            + (float) (2200.0f * (this.time12 - time / 2) * muti * Settings.scale * Math.cos(angle12)),
                    Settings.HEIGHT * 0.5f + 225.0f * Settings.scale - 3.0f
                            + (float) (2200.0f * (this.time12 - time / 2) * muti * Settings.scale * Math.sin(angle12)),
                    3.0F, 3.0f,
                    6.0F, 6.0f,
                    198.0f * Settings.scale, 1.5f * Settings.scale,
                    20.0f,
                    0, 0,
                    6, 6,
                    false, false);


//            line 2 右下斜边 - 左下到右上
            spriteBatch.draw(this.pix, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 3.0f + 16.0f * Settings.scale
                            - (float) (2200.0f * (this.time12 - time / 2) * muti * Settings.scale * Math.cos(angle12)),
                    Settings.HEIGHT * 0.5f - 225.0f * Settings.scale - 3.0f
                            - (float) (2200.0f * (this.time12 - time / 2) * muti * Settings.scale * Math.sin(angle12)),
                    3.0F, 3.0f,
                    6.0F, 6.0f,
                    198.0f * Settings.scale, 1.5f * Settings.scale,
                    20.0f,
                    0, 0,
                    6, 6,
                    false, false);


            if (this.time12 < time * 0.5f && this.duration >= 0.8f) {
                spriteBatch.draw(this.pix, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 3.0f - 16.0f * Settings.scale,
                        Settings.HEIGHT * 0.5f + 225.0f * Settings.scale - 3.0f,
                        3.0F, 3.0f,
                        6.0F, 6.0f,
                        29.0f * Settings.scale, 1.5f * Settings.scale,
                        20.0f,
                        0, 0,
                        6, 6,
                        false, false);

                spriteBatch.draw(this.pix, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 3.0f + 16.0f * Settings.scale,
                        Settings.HEIGHT * 0.5f - 225.0f * Settings.scale - 3.0f,
                        3.0F, 3.0f,
                        6.0F, 6.0f,
                        29.0f * Settings.scale, 1.5f * Settings.scale,
                        20.0f,
                        0, 0,
                        6, 6,
                        false, false);
            }


//        line 3 左侧 - 上到下


            spriteBatch.draw(this.pix, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 3.0f - 94.0f * Settings.scale,
                    Settings.HEIGHT * 0.5f - 3.0f - 23.0f * Settings.scale
                            + 2200.0f * (this.time34 - time / 2) * muti * Settings.scale,
                    3.0F, 3.0f,
                    6.0F, 6.0f,
                    1.5f * Settings.scale, 200.0f * Settings.scale,
                    0.0f,
                    0, 0,
                    6, 6,
                    false, false);


//            line 4 右侧 - 下到上
            spriteBatch.draw(this.pix, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 3.0f + 94.0f * Settings.scale,
                    Settings.HEIGHT * 0.5f - 3.0f + 23.0f * Settings.scale
                            - 2200.0f * (this.time34 - time / 2) * muti * Settings.scale,
                    3.0F, 3.0f,
                    6.0F, 6.0f,
                    1.5f * Settings.scale, 200.0f * Settings.scale,
                    0.0f,
                    0, 0,
                    6, 6,
                    false, false);


            if (this.time34 <= time * 0.5f && this.duration >= 0.8f) {
                spriteBatch.draw(this.pix, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 3.0f - 94.0f * Settings.scale,
                        Settings.HEIGHT * 0.5f - 3.0f - 23.0f * Settings.scale,
                        3.0F, 3.0f,
                        6.0F, 6.0f,
                        1.5f * Settings.scale, 74.0f * Settings.scale,
                        0.0f,
                        0, 0,
                        6, 6,
                        false, false);


                spriteBatch.draw(this.pix, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 3.0f + 94.0f * Settings.scale,
                        Settings.HEIGHT * 0.5f - 3.0f + 23.0f * Settings.scale,
                        3.0F, 3.0f,
                        6.0F, 6.0f,
                        1.5f * Settings.scale, 74.0f * Settings.scale,
                        0.0f,
                        0, 0,
                        6, 6,
                        false, false);
            }


//        line 5 右上 - 左上到右下
            double angle56 = Math.tanh(3.0f / 7.0f);


            spriteBatch.draw(this.pix, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 3.0f + 81.0f * Settings.scale
                            - (float) (2200.0f * (this.time56 - time / 2) * muti * Settings.scale * Math.cos(angle56)),
                    Settings.HEIGHT * 0.5f - 3.0f + 248.0f * Settings.scale
                            + (float) (2200.0f * (this.time56 - time / 2) * muti * Settings.scale * Math.sin(angle56)),
                    3.0F, 3.0f,
                    6.0F, 6.0f,
                    200.0f * Settings.scale, 1.5f * Settings.scale,
                    -23.0f,
                    0, 0,
                    6, 6,
                    false, false);


//            line 6 左下斜边 - 右下到左上
            spriteBatch.draw(this.pix, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 3.0f - 81.0f * Settings.scale
                            + (float) (2200.0f * (this.time56 - time / 2) * muti * Settings.scale * Math.cos(angle56)),
                    Settings.HEIGHT * 0.5f - 3.0f - 248.0f * Settings.scale
                            - (float) (2200.0f * (this.time56 - time / 2) * muti * Settings.scale * Math.sin(angle56)),
                    3.0F, 3.0f,
                    6.0F, 6.0f,
                    200.0f * Settings.scale, 1.5f * Settings.scale,
                    -23.0f,
                    0, 0,
                    6, 6,
                    false, false);


            if (this.time56 <= time * 0.5f && this.duration >= 0.8f) {
                spriteBatch.draw(this.pix, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 3.0f + 81.0f * Settings.scale,
                        Settings.HEIGHT * 0.5f - 3.0f + 248.0f * Settings.scale,
                        3.0F, 3.0f,
                        6.0F, 6.0f,
                        6.0f * Settings.scale, 1.5f * Settings.scale,
                        -23.0f,
                        0, 0,
                        7, 6,
                        false, false);


                spriteBatch.draw(this.pix, Settings.WIDTH - 480.0f * Settings.scale * getPosXScale() - 3.0f - 81.0f * Settings.scale,
                        Settings.HEIGHT * 0.5f - 3.0f - 248.0f * Settings.scale,
                        3.0F, 3.0f,
                        6.0F, 6.0f,
                        6.0f * Settings.scale, 1.5f * Settings.scale,
                        -23.0f,
                        0, 0,
                        6, 6,
                        false, false);
            }

        });

        ShionImageHelper.renderScreenTexture(sb, tmp);
        ShionImageHelper.renderScreenTexture(sb, tmp, true);
    }


    private float getPosXScale() {
        return 1.0f;
    }

    @Override
    public void dispose() {
        if (pix != null) {
            this.pix.dispose();
            this.pix = null;
        }

        if (circle != null) {
            this.circle.dispose();
            this.circle = null;
        }

        if (circleMask != null) {
            this.circleMask.dispose();
            this.circleMask = null;
        }

        if (circleBlur != null) {
            this.circleBlur.dispose();
            this.circleBlur = null;
        }

        if (vsTitle != null) {
            this.vsTitle.dispose();
            this.vsTitle = null;
        }

        if (vsTitleName != null) {
            this.vsTitleName.dispose();
            this.vsTitleName = null;
        }

        if (playerClassIcon1 != null) {
            this.playerClassIcon1.dispose();
            this.playerClassIcon1 = null;
        }

        if (playerClassIcon2 != null) {
            this.playerClassIcon2.dispose();
            this.playerClassIcon2 = null;
        }
    }
}
