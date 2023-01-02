package VUPShionMod.skins.sk.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.character.Liyezhu;
import VUPShionMod.character.WangChuan;
import VUPShionMod.skins.AbstractSkinCharacter;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

public class AbstractSkinWangChuan extends AbstractSkinCharacter {
    public AbstractSkinWangChuan() {
        super();
        if (this.skins.isEmpty()) {
            skins.add(new OriWangChuan(0));
            skins.add(new PurityWangChuan(1));
            skins.add(new AquaWangChuan(2));
            skins.add(new ChinaWangChuan(3));
        }

    }

    @Override
    public boolean isCharacter(CharacterOption option) {
        return option.c instanceof WangChuan;
    }
}
