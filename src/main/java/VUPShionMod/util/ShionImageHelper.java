package VUPShionMod.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import java.util.function.Consumer;

public class ShionImageHelper {
    public static FrameBuffer buffer;
    public static FrameBuffer maskBuffer;
    private static final ShaderProgram alphaMaskShader;
    private static final ShaderProgram alphaShader;



    static {
        buffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
        maskBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);

        alphaMaskShader = new ShaderProgram(
                Gdx.files.internal("VUPShionMod/shader/alphaMask.vs").readString(),
                Gdx.files.internal("VUPShionMod/shader/alphaMask.fs").readString());

        alphaShader = new ShaderProgram(
                Gdx.files.internal("VUPShionMod/shader/alphaMask.vs").readString(),
                Gdx.files.internal("VUPShionMod/shader/alpha.fs").readString());


        if (!alphaMaskShader.isCompiled()) {
            throw new RuntimeException(alphaMaskShader.getLog());
        }

        if (!alphaShader.isCompiled()) {
            throw new RuntimeException(alphaShader.getLog());
        }
    }

    public static Texture getScreenMaskTexture(SpriteBatch sb, Consumer<SpriteBatch> textureToDraw, boolean whiteScreen) {
        Texture textureFinal = null;

        if (textureToDraw != null) {
            sb.end();
            maskBuffer.begin();

            if (whiteScreen)
                Gdx.gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
            else
                Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);

            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            Gdx.gl.glColorMask(true, true, true, true);
            sb.begin();
            sb.setColor(Color.WHITE);

            textureToDraw.accept(sb);

            sb.end();
            maskBuffer.end();

            textureFinal = maskBuffer.getColorBufferTexture();
            sb.begin();
        }

        return textureFinal;
    }


    public static Texture getScreenTexture(SpriteBatch sb, Consumer<SpriteBatch> textureToDraw) {
        Texture textureFinal = null;

        if (textureToDraw != null) {
            sb.end();
            buffer.begin();

            Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);

            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            Gdx.gl.glColorMask(true, true, true, true);
            sb.begin();
            sb.setColor(Color.WHITE);

            textureToDraw.accept(sb);

            sb.end();
            buffer.end();

            textureFinal = buffer.getColorBufferTexture();
            sb.begin();
        }

        return textureFinal;
    }


    public static Texture getScreenAlphaTexture(SpriteBatch sb, Color oriColor, Consumer<SpriteBatch> textureToDraw) {
        Texture textureFinal = null;

        if (textureToDraw != null) {
            sb.end();
            buffer.begin();

            Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            Gdx.gl.glColorMask(true, true, true, true);

            sb.setShader(alphaShader);
            sb.begin();
            sb.setColor(Color.WHITE);

            textureToDraw.accept(sb);
            sb.flush();
            sb.end();
            buffer.end();
            sb.setShader(null);

            textureFinal = buffer.getColorBufferTexture();
            sb.begin();
        }

        sb.setColor(oriColor);

        return textureFinal;
    }

    public static void renderScreenTexture(SpriteBatch sb, Texture oriImage) {
        renderScreenTexture(sb, oriImage, false);
    }

    public static void renderScreenTexture(SpriteBatch sb, Texture oriImage, boolean flipX) {
        sb.draw(oriImage,
                0,
                0,
                0.0F, 0.0f,
                Settings.WIDTH, Settings.HEIGHT,
                1.0f, 1.0f,
                0.0f,
                0, 0,
                Settings.WIDTH, Settings.HEIGHT,
                flipX, true);
    }


    public static void renderMaskedImage(SpriteBatch sb, Color renderColor, Consumer<SpriteBatch> oriTexture, Consumer<SpriteBatch> mask) {
        renderMaskedImage(sb, renderColor, oriTexture, mask, false);
    }

    public static void renderMaskedImage(SpriteBatch sb, Color renderColor, Consumer<SpriteBatch> oriTexture, Consumer<SpriteBatch> mask, boolean renderOutOfMask) {
        Texture oriImage = ShionImageHelper.getScreenTexture(sb, oriTexture);
        Texture maskImage = ShionImageHelper.getScreenMaskTexture(sb, mask, renderOutOfMask);

        sb.end();
        sb.setShader(alphaMaskShader);
        maskImage.bind(1);
        oriImage.bind(0);
        sb.begin();
        sb.setColor(renderColor);
        alphaMaskShader.setUniformi("u_mask", 1);

        sb.draw(oriImage,
                0,
                0,
                0.0F, 0.0f,
                Settings.WIDTH, Settings.HEIGHT,
                1.0f, 1.0f,
                0.0f,
                0, 0,
                Settings.WIDTH, Settings.HEIGHT,
                false, true);

        sb.flush();
        sb.end();
        sb.setShader(null);
        sb.begin();
    }

}
