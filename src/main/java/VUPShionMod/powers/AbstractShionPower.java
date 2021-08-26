package VUPShionMod.powers;

import VUPShionMod.VUPShionMod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AbstractShionPower extends AbstractPower {
    public void setImage(String bigImageName, String smallImageName) {
        String path = VUPShionMod.assetPath("img/powers/");

        String path128 = path + bigImageName;
        String path48 = path + smallImageName;

        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
    }

    public void setTinyImage(String imgName) {
        String path = VUPShionMod.assetPath("img/powers/");
        this.img = ImageMaster.loadImage(path + imgName);
    }

    public void onReducePower(AbstractPower power, AbstractCreature target, AbstractCreature source){

    }
}
