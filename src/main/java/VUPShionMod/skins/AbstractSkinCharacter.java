package VUPShionMod.skins;

import VUPShionMod.patches.CharacterSelectScreenPatches;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

import java.util.ArrayList;

public abstract class AbstractSkinCharacter {
    public int reskinCount = 0;
    public int selectedCount = 0;
    public int lastSelectedCount = 0;

    public ArrayList<AbstractSkin> skins = new ArrayList<>();
    public ArrayList<SkinInfoLabel> labels = new ArrayList<>();

    public AbstractSkinCharacter() {
    }

    public void InitializeReskinCount() {
        if (this.reskinCount < 0)
            this.reskinCount = 0;

        if (reskinCount > skins.size()) {
            this.reskinCount = 0;
        }
    }

    public boolean isCharacter(CharacterOption option){
        return false;
    }


    public void update(CharacterOption option) {
        for (SkinInfoLabel label : this.labels) {
            label.update();
        }


        for (AbstractSkin skin : this.skins) {
            skin.button.update(option);
            skin.button.setPos(this.selectedCount, this.skins.size());
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
       this.lastSelectedCount =  this.selectedCount = this.reskinCount;

        if (this.labels.isEmpty()) {
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
        for (AbstractSkin skin : this.skins) {
            skin.initialize();
            skin.button.locked = !skin.unlock;

            boolean display = false;
            int distance = 0;

            if (Math.abs(skin.button.index - selectedCount) < 3) {
                display = true;
                distance = skin.button.index - selectedCount;
            }

            if (!display && this.skins.size() >= 5 && selectedCount < 2 && selectedCount + this.skins.size() - skin.button.index < 3) {
                display = true;
                distance = -Math.abs(this.skins.size() - skin.button.index + selectedCount);
            }

            if (!display && this.skins.size() >= 5 && selectedCount > this.skins.size() - 3 && this.skins.size() - selectedCount + skin.button.index < 3) {
                display = true;
                distance = Math.abs(this.skins.size() - selectedCount + skin.button.index);
            }


            if (!display) {
                skin.button.scale = 0.0f;
                skin.button.current_x = 0.0f;
                skin.button.current_y = 0.0f;
                skin.button.color.a = 0.0f;
                continue;
            }


            skin.button.scale = (1.0f - 0.3f * Math.abs(distance))
                    * CharacterSelectScreenPatches.skinManager.scale;


            if (skin.button.index - selectedCount > 0) {
                skin.button.current_x = 1.5f * -Math.abs(distance)
                        * 120.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;


                skin.button.current_y = 1.5f * (distance)
                        * 40.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;
            } else {
                skin.button.current_x = 1.5f * -Math.abs(distance)
                        * 100.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;


                skin.button.current_y = 1.5f * (distance)
                        * 120.0f * CharacterSelectScreenPatches.skinManager.scale * Settings.scale;
            }


            if (distance == 0) {
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

            if (Math.abs(distance) >= 2) {
                skin.button.color.a = 0.0f;
            } else {
                skin.button.color.a = 1.0f;
            }
        }
    }
}


