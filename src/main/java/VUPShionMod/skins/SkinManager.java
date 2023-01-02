package VUPShionMod.skins;

import VUPShionMod.patches.CharacterSelectScreenPatches;
import VUPShionMod.skins.sk.EisluRen.AbstractSkinEisluRen;
import VUPShionMod.skins.sk.Liyezhu.AbstractSkinLiyezhu;
import VUPShionMod.skins.sk.Shion.AbstractSkinShion;
import VUPShionMod.skins.sk.WangChuan.AbstractSkinWangChuan;
import VUPShionMod.util.ShionImageHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

import java.util.ArrayList;

public class SkinManager {
    public ArrayList<AbstractSkinCharacter> skinCharacters = new ArrayList<>();
    public AbstractSkinCharacter currentSkinCharacter;
    public AbstractSkin currentSkin;

    public float scale;
    private final Color color = Color.WHITE.cpy();

    public float panel_ShowFinalX;

    public float panel_HideFinalX;
    public float panel_x;

    private float confirmButtonGlowTimer = 0.0f;

    public Texture dark;
    public Texture darkMask;
    public Texture confirmButtonGlow;
    public Texture vein;
    public Texture cover1;
    public Texture cover2;
    public Texture coverGlow;
    public Texture coverMultiplier;

    private float coverGlowFixY = Settings.HEIGHT * 0.75f;
    private float coverGlowFixY2 = Settings.HEIGHT;

    public SkinManager() {
        this.scale = Settings.isSixteenByTen ? 1.20f : 1.1f;

        this.panel_ShowFinalX = Settings.WIDTH - 656.0f * this.scale * Settings.scale;

        this.panel_HideFinalX = Settings.WIDTH;
        this.panel_x = panel_HideFinalX;

        if (skinCharacters.isEmpty()) {
            skinCharacters.add(new AbstractSkinShion());
            skinCharacters.add(new AbstractSkinWangChuan());
            skinCharacters.add(new AbstractSkinLiyezhu());
            skinCharacters.add(new AbstractSkinEisluRen());
        }

        this.dark = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/dark.png");
        this.darkMask = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/darkMask.png");

        this.cover2 = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/cover2.png");
        this.confirmButtonGlow = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/confirmGlow.png");


        this.vein = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/vein.png");

        this.cover1 = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/cover1.png");
        this.coverGlow = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/coverGlow.png");
        this.coverMultiplier = ImageMaster.loadImage("VUPShionMod/img/ui/Skin/coverMultiplier.png");
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
        if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.CHAR_SELECT)
            for (AbstractSkinCharacter c : skinCharacters) {
                if (c.isCharacter(option)) {
                    if (!c.skins.get(c.selectedCount).unlock) {
                        CardCrawlGame.mainMenuScreen.charSelectScreen.confirmButton.hide();
                    }
                }
            }
    }


    public void update() {
        coverGlowFixY += Gdx.graphics.getDeltaTime() * 420.0f * Settings.scale;
        if (coverGlowFixY >= Settings.HEIGHT * 2.0f) {
            coverGlowFixY = 0.0f;
        }

        coverGlowFixY2 += Gdx.graphics.getDeltaTime() * 330.0f * Settings.scale;
        if (coverGlowFixY2 >= Settings.HEIGHT + 300.0f * this.scale * Settings.yScale) {
            coverGlowFixY2 = 0.0f;
        }

        CharacterOption selectedOption = null;
        boolean hasChar = false;

        if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.CHAR_SELECT)
            for (CharacterOption o : CardCrawlGame.mainMenuScreen.charSelectScreen.options) {
                if (o.selected)
                    selectedOption = o;
            }

        if (selectedOption != null) {
            for (AbstractSkinCharacter c : skinCharacters) {
                if (c.isCharacter(selectedOption)) {
                    currentSkinCharacter = c;
                    currentSkin = currentSkinCharacter.skins.get(currentSkinCharacter.selectedCount);
                    this.panel_x = MathHelper.uiLerpSnap(this.panel_x, this.panel_ShowFinalX);
                    hasChar = true;
                    break;
                }
            }
        }

        if (!hasChar) {
            currentSkin = null;
            currentSkinCharacter = null;
            this.panel_x = MathHelper.uiLerpSnap(this.panel_x, this.panel_HideFinalX);
            return;
        }

        if (currentSkinCharacter != null) {
            currentSkinCharacter.update(selectedOption);
        }
    }

    public void characterRender(SpriteBatch sb) {
        if (currentSkin != null) {
            currentSkin.renderPortrait(sb);
        }
    }


    public void panelRender(SpriteBatch sb) {
        this.color.a = 1.0f;
        ShionImageHelper.renderMaskedImage(sb, this.color,

                sb1 -> sb1.draw(this.dark,
                        this.panel_x - 0.0f,
                        Settings.HEIGHT * 0.5f - 500.0f,
                        0.0F, 500.0f,
                        752.0F, 1000.0f,
                        this.scale * Settings.xScale, this.scale * Settings.yScale,
                        0.0f,
                        0, 0,
                        752, 1000,
                        false, false),

                sb2 -> sb2.draw(this.darkMask,
                        this.panel_x + 290.0f * this.scale * Settings.scale,
                        186.0f * Settings.scale - 42.0f,
                        0.0F, 42.0f,
                        372.0F, 84.0f,
                        this.scale * Settings.scale, this.scale * Settings.scale,
                        0.0f,
                        0, 0,
                        372, 84,
                        false, false), true);


        sb.draw(this.cover2,
                this.panel_x + 290.0f * this.scale * Settings.scale,
                186.0f * Settings.scale - 42.0f,
                0.0F, 42.0f,
                372.0F, 84.0f,
                this.scale * Settings.scale, this.scale * Settings.scale,
                0.0f,
                0, 0,
                372, 84,
                false, false);


        sb.draw(this.cover1,
                this.panel_x - 0.0f,
                Settings.HEIGHT * 0.5f - 510.0f,
                0.0F, 510.0f,
                752.0F, 1020.0f,
                this.scale * Settings.xScale, this.scale * Settings.yScale,
                0.0f,
                0, 0,
                752, 1020,
                false, false);


        ShionImageHelper.renderMaskedImage(sb, this.color,
                sb1 -> {
//            大
                    sb1.draw(this.coverGlow,
                            this.panel_x - 0.0f,
                            Settings.HEIGHT - coverGlowFixY,
                            0.0F, 0.0f,
                            800.0F, 300.0f,
                            this.scale * Settings.xScale, this.scale * Settings.yScale,
                            0.0f,
                            0, 300,
                            800, 300,
                            false, false);

//小辉光
                    sb1.draw(this.coverGlow,
                            this.panel_x - 0.0f,
                            Settings.HEIGHT - coverGlowFixY2,
                            0.0F, 0.0f,
                            800.0F, 300.0f,
                            this.scale * Settings.xScale, this.scale * Settings.yScale,
                            0.0f,
                            0, 0,
                            800, 300,
                            false, false);
                },

                sb2 -> sb2.draw(this.coverMultiplier,
                        this.panel_x - 0.0f,
                        Settings.HEIGHT * 0.5f - 510.0f,
                        0.0F, 510.0f,
                        752.0F, 1020.0f,
                        this.scale * Settings.xScale, this.scale * Settings.yScale,
                        0.0f,
                        0, 0,
                        752, 1020,
                        false, false)
        );


        sb.draw(this.vein,
                this.panel_x - 0.0f,
                Settings.HEIGHT * 0.5f - 510.0f,
                0.0F, 510.0f,
                752.0F, 1020.0f,
                this.scale * Settings.xScale, this.scale * Settings.yScale,
                0.0f,
                0, 0,
                752, 1020,
                false, false);


        this.confirmButtonGlowTimer += Gdx.graphics.getDeltaTime() * 4.0f;
        this.color.a = ((float) Math.cos(this.confirmButtonGlowTimer) + 1.0f) * 0.2f + 0.6f;
        sb.setColor(this.color);

        if (currentSkin != null)
            if (currentSkin.unlock) {
                sb.draw(this.confirmButtonGlow,
                        this.panel_x + 290.0f * this.scale * Settings.scale,
                        186.0f * Settings.scale - 42.0f,
                        0.0F, 42.0f,
                        372.0F, 84.0f,
                        this.scale * Settings.scale, this.scale * Settings.scale,
                        0.0f,
                        0, 0,
                        372, 84,
                        false, false);
            }

        this.color.a = 1.0f;

        if (currentSkinCharacter != null) {
            currentSkinCharacter.render(sb);
        }
    }

    public static AbstractSkinCharacter getSkinCharacter(int charIndex) {
        return CharacterSelectScreenPatches.skinManager.skinCharacters.get(charIndex);
    }

    public static AbstractSkin getSkin(int charIndex) {
        return CharacterSelectScreenPatches.skinManager.skinCharacters.get(charIndex).skins.get(CharacterSelectScreenPatches.skinManager.skinCharacters.get(charIndex).reskinCount);
    }

    public static AbstractSkin getSkin(int charIndex, int skinIndex) {
        return CharacterSelectScreenPatches.skinManager.skinCharacters.get(charIndex).skins.get(skinIndex);
    }

    public static AbstractSkin getSkin(String skinId) {
        AbstractSkin skin = null;
        for (AbstractSkinCharacter c : CharacterSelectScreenPatches.skinManager.skinCharacters) {
            for (AbstractSkin sk : c.skins) {
                if (sk.skinId.equals(skinId)) {
                    skin = sk;
                }
            }

        }

        return skin;
    }


}
