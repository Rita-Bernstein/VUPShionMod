package VUPShionMod.skins;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractSkinCharacter {
    public String id;

    public boolean reskinUnlock = false;
    public int reskinCount = 0;
    public String lockString;

    public AbstractSkin[] skins;

    public AbstractSkinCharacter(String id, AbstractSkin[] skins) {
        this.id = id;
        this.skins = skins;
    }

    public void InitializeReskinCount() {
        if (this.reskinCount < 0)
            this.reskinCount = 0;

        if (!reskinUnlock)
            this.reskinCount = 0;

        if(reskinCount > skins.length)
            this.reskinCount = 0;
    }

    public Boolean isOriginal() {
        return this.reskinCount <= 0;
    }

    public void checkUnlock() {

    }
}


