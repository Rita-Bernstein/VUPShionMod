package VUPShionMod.skins.sk.Shion;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.skins.AbstractSkin;
import VUPShionMod.skins.AbstractSkinCharacter;
import VUPShionMod.vfx.ReskinUnlockedTextEffect;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AbstractSkinShion extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("VUPShionMod:Shion").NAMES[0];
    public static final AbstractSkin[] SKINS = new AbstractSkin[]{
            new OriShion(),
            new BlueGiantShion(),
            new AquaShion()
    };

    public AbstractSkinShion() {
        super(ID, SKINS);
        this.lockString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("SkinPannel")).EXTRA_TEXT[0];
    }

    @Override
    public void checkUnlock() {
        if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.VUP_Shion && !this.reskinUnlock) {
            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(0));
            this.reskinUnlock = true;
        }
    }
}
