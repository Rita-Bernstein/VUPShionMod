package VUPShionMod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FontHelperPatches {
    public static BitmapFont energyNumFontShion;
    public static BitmapFont energyNumFontShionBack;

    @SpirePatch(
            clz = FontHelper.class,
            method = "initialize"
    )
    public static class PatchUseEnergy {
        @SpirePostfixPatch
        public static void Postfix(FileHandle ___fontFile, FreeTypeFontGenerator.FreeTypeFontParameter ___param) {
            ___fontFile = Gdx.files.internal("VUPShionMod/fonts/Let_s_go_Digital_Regular.ttf");
            ___param.borderStraight = true;
            ___param.borderWidth = 4.0F * Settings.scale;
            ___param.borderColor = new Color(0.36F, 0.27F, 0.0F, 1.0F);


            try {
                FreeTypeFontGenerator g = new FreeTypeFontGenerator(___fontFile);
                float size = 36.0f;

                if (Settings.BIG_TEXT_MODE) {
                    size *= 1.2f;
                }

                Method prepFont = FontHelper.class.getDeclaredMethod("prepFont", FreeTypeFontGenerator.class, float.class, boolean.class);
                prepFont.setAccessible(true);
                energyNumFontShion = (BitmapFont) prepFont.invoke(FontHelper.class.getName(), g, size, true);

                ___param.borderColor = new Color(0.6F, 0.486F, 0.419F, 1.0F);

                energyNumFontShionBack = (BitmapFont) prepFont.invoke(FontHelper.class.getName(), g, size, true);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
