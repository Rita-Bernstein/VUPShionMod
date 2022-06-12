package VUPShionMod.skins;

import VUPShionMod.patches.CharacterSelectScreenPatches;
import VUPShionMod.skins.sk.Liyezhu.AbstractSkinLiyezhu;
import VUPShionMod.skins.sk.Shion.AbstractSkinShion;
import VUPShionMod.skins.sk.WangChuan.AbstractSkinWangChuan;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

import java.util.ArrayList;

public class SkinManager {
    public ArrayList<AbstractSkinCharacter> skinCharacters = new ArrayList<>();
    public AbstractSkinCharacter currentSkinCharacter;
    public AbstractSkin currentSkin;
    public static CharacterSelectScreen screen;

    public float scale = 1.1f;
    private Color color = Color.WHITE.cpy();

    public float panel_ShowFinalX = Settings.WIDTH - 656.0f * this.scale * Settings.scale;

    public float panel_HideFinalX = Settings.WIDTH;
    public float panel_x = panel_HideFinalX;

    private float confirmButtonGlowTimer = 0.0f;

    public Texture body;
    public Texture confirmButtonGlow;

    public SkinManager() {
        if (skinCharacters.isEmpty()) {
            skinCharacters.add(new AbstractSkinShion());
            skinCharacters.add(new AbstractSkinWangChuan());
            skinCharacters.add(new AbstractSkinLiyezhu());
        }

        this.body = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/dark.png");
        this.confirmButtonGlow = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/confirmGlow.png");
    }

    public void initialize() {
        for (AbstractSkinCharacter character : skinCharacters) {
            character.initialize();
        }
    }

    public void unlockSkin(String skinId) {
        for (AbstractSkinCharacter character : skinCharacters) {
            for (AbstractSkin skin : character.skins) {
                if (skinId.equals(skin.skinId))
                    skin.onUnlock();
            }
        }
    }

    public void unlockAllSkin() {
        for (AbstractSkinCharacter character : skinCharacters) {
            for (AbstractSkin skin : character.skins) {
                    skin.onUnlock();
            }
        }
    }


    public void justSelected(CharacterOption option) {
        for (AbstractSkinCharacter c : skinCharacters) {
            if (option.name.equals(c.id)) {
                if (!c.skins.get(c.selectedCount).unlock) {
                    screen.confirmButton.hide();
                }
            }
        }
    }


    public void update(CharacterSelectScreen screen) {
        SkinManager.screen = screen;
        CharacterOption selectedOption = null;
        boolean hasChar = false;

        for (CharacterOption o : screen.options) {
            if (o.selected)
                selectedOption = o;
        }

        if (selectedOption != null) {
            for (AbstractSkinCharacter c : skinCharacters) {
                if (selectedOption.name.equals(c.id)) {
                    currentSkinCharacter = c;
                    currentSkin = currentSkinCharacter.skins.get(currentSkinCharacter.selectedCount);
                    this.panel_x = MathHelper.uiLerpSnap(this.panel_x, this.panel_ShowFinalX);
                    hasChar = true;
                    break;
                }
            }




        }

        if(!hasChar){
            currentSkin = null;
            currentSkinCharacter = null;
            this.panel_x = MathHelper.uiLerpSnap(this.panel_x, this.panel_HideFinalX);
            return;
        }

        if (currentSkinCharacter != null) {
            currentSkinCharacter.update(selectedOption);
        }
    }

    public void characterRender(CharacterSelectScreen screen, SpriteBatch sb) {
        if (currentSkin != null) {
            currentSkin.renderPortrait(sb);
        }
    }

    public void panelRender(CharacterSelectScreen screen, SpriteBatch sb) {
        sb.setColor(this.color);

        sb.draw(this.body,
                this.panel_x - 0.0f,
                Settings.HEIGHT * 0.5f - 537.0f,
                0.0F, 537.0f,
                745.0F, 1074.0f,
                this.scale * Settings.scale, this.scale * Settings.scale,
                0.0f,
                0, 0,
                754, 1074,
                false, false);


        this.confirmButtonGlowTimer += Gdx.graphics.getDeltaTime() * 4.0f;
        this.color.a = ((float) Math.cos(this.confirmButtonGlowTimer) + 1.0f) * 0.2f + 0.6f;
        sb.setColor(this.color);

        if (currentSkin != null)
            if (currentSkin.unlock) {
                sb.draw(this.confirmButtonGlow,
                        this.panel_x - 0.0f + 460.0f * this.scale * Settings.scale,
                        Settings.HEIGHT * 0.5f - 355.0f * this.scale * Settings.scale,
                        0.0F, 0.0f,
                        168.0F, 66.0f,
                        this.scale * Settings.scale, this.scale * Settings.scale,
                        0.0f,
                        0, 0,
                        168, 66,
                        false, false);
            }

        this.color.a = 1.0f;

        if (currentSkinCharacter != null) {
            currentSkinCharacter.render(sb);
        }
    }
}
