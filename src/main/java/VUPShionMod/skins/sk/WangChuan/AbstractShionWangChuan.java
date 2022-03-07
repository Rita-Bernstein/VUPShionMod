package VUPShionMod.skins.sk.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.AbstractPlayerEnum;
import VUPShionMod.skins.AbstractSkin;
import VUPShionMod.skins.AbstractSkinCharacter;
import VUPShionMod.skins.sk.Shion.BlueGiantShion;
import VUPShionMod.skins.sk.Shion.OriShion;
import VUPShionMod.vfx.ReskinUnlockedTextEffect;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AbstractShionWangChuan extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("VUPShionMod:WangChuan").NAMES[0];
    public static final AbstractSkin[] SKINS = new AbstractSkin[]{
            new OriWangChuan(),
            new PurityWangChuan()
    };

    public AbstractShionWangChuan() {
        super(ID, SKINS);
        this.lockString = CardCrawlGame.languagePack.getUIString(VUPShionMod.makeID("SkinPannel")).EXTRA_TEXT[0];
    }

    @Override
    public void checkUnlock() {
        if (AbstractDungeon.player.chosenClass == AbstractPlayerEnum.WangChuan && !this.reskinUnlock) {
            AbstractDungeon.topLevelEffects.add(new ReskinUnlockedTextEffect(1));
            this.reskinUnlock = true;
        }
    }
}
