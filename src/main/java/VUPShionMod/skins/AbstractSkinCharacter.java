package VUPShionMod.skins;

import VUPShionMod.patches.CharacterSelectScreenPatches;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;

import java.util.ArrayList;
import java.util.Set;

public abstract class AbstractSkinCharacter {
    public String id;
    public int reskinCount = 0;
    public int selectedCount = 0;

    public ArrayList<AbstractSkin> skins = new ArrayList<>();
    public ArrayList<SkinInfoLabel> labels = new ArrayList<>();


    public AbstractSkinCharacter(String id) {
        this.id = id;
    }

    public void InitializeReskinCount() {
        if (this.reskinCount < 0)
            this.reskinCount = 0;

        if (reskinCount > skins.size()) {
            this.reskinCount = 0;
        }
    }


    public void update(CharacterOption option) {
        for (SkinInfoLabel label : this.labels) {
            label.update();
        }



        for (AbstractSkin skin : this.skins) {
            skin.button.update(option);
            skin.button.setPos(this.selectedCount);
        }
    }

    public void render(SpriteBatch sb) {
        for (SkinInfoLabel label : this.labels) {
            label.render(sb);
        }

        for (AbstractSkin skin : this.skins) {
            skin.button.render(sb);
        }
    }

    public void updateLabelString() {
        this.labels.get(0).msg = skins.get(selectedCount).unlockString;
        this.labels.get(1).msg = skins.get(selectedCount).name;
        this.labels.get(2).msg = skins.get(selectedCount).flavorText;
        this.labels.get(3).msg = skins.get(selectedCount).level;
    }


    public void initialize() {
        this.selectedCount = this.reskinCount;

        if(this.labels.isEmpty()) {
            this.labels.add(new SkinInfoLabel(skins.get(selectedCount).unlockString, 0));
            this.labels.add(new SkinInfoLabel(skins.get(selectedCount).name, 1));
            this.labels.add(new SkinInfoLabel(skins.get(selectedCount).flavorText, 2));
            this.labels.add(new SkinInfoLabel(skins.get(selectedCount).level, 3));
        }

        for (int i = 0; i < this.labels.size(); i++) {
            this.labels.get(i).cX = CharacterSelectScreenPatches.skinManager.panel_x + (200.0f + 60.0f * i) * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;
            this.labels.get(i).cY = Settings.HEIGHT * 0.5f + (430.0f - 80.0f * i) * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;
        }


//        皮肤按钮
        this.skins.get(0).unlock = true;

        for (AbstractSkin skin : this.skins) {
            skin.button.locked = !skin.unlock;

            if (Math.abs(skin.button.index - selectedCount) > 3) {
                skin.button.scale = 0.0f;
                skin.button.current_x = 0.0f;
                skin.button.current_y = 0.0f;
            } else {
                skin.button.scale = (1.0f - 0.3f * Math.abs(skin.button.index - selectedCount))
                        * CharacterSelectScreenPatches.skinManager.scale ;


                if (skin.button.index - selectedCount > 0) {
                    skin.button.current_x = 1.5f * -Math.abs(skin.button.index - selectedCount)
                            * 120.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;


                    skin.button.current_y = 1.5f * (skin.button.index - selectedCount)
                            * 40.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;
                } else {
                    skin.button.current_x = 1.5f * -Math.abs(skin.button.index - selectedCount)
                            * 100.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;


                    skin.button.current_y = 1.5f * (skin.button.index - selectedCount)
                            * 120.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;
                }
            }

            if (skin.button.index - selectedCount == 0) {
                skin.button.outlineFix_x = 0.0f;
                skin.button.outlineFix_y = 0.0f;
                skin.button.outlineFix_Xscale = 1.0f;
                skin.button.outlineFix_Yscale = 1.0f;
            } else {
                skin.button.outlineFix_x = 8.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;
                skin.button.outlineFix_y = 6.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;
                skin.button.outlineFix_Xscale = 0.95f;
                skin.button.outlineFix_Yscale = 0.85f;
            }

            if (Math.abs(skin.button.index - selectedCount) >= 2) {
                skin.button.color.a = 0.0f;
            } else {
                skin.button.color.a = 1.0f;
            }
        }
    }
}


