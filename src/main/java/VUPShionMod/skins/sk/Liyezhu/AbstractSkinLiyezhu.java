package VUPShionMod.skins.sk.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.skins.AbstractSkin;
import VUPShionMod.skins.AbstractSkinCharacter;
import VUPShionMod.vfx.ReskinUnlockedTextEffect;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AbstractSkinLiyezhu extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("VUPShionMod:Liyezhu").NAMES[0];
    public static final AbstractSkin[] SKINS = new AbstractSkin[]{
            new OriLiyezhu()
    };

    public AbstractSkinLiyezhu() {
        super(ID, SKINS);
        this.lockString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("SkinPannel")).EXTRA_TEXT[0];
    }

    @Override
    public void checkUnlock() {
        if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.Liyezhu && !this.reskinUnlock) {
//            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(2));
//            this.reskinUnlock = true;
        }
    }
}
