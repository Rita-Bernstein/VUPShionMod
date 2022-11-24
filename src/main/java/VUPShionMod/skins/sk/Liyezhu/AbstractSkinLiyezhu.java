package VUPShionMod.skins.sk.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.character.EisluRen;
import VUPShionMod.character.Liyezhu;
import VUPShionMod.skins.AbstractSkinCharacter;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

public class AbstractSkinLiyezhu extends AbstractSkinCharacter {

    public AbstractSkinLiyezhu() {
        super();
        if(this.skins.isEmpty()) {
            this.skins.add(new OriLiyezhu(0));
        }
    }

    @Override
    public boolean isCharacter(CharacterOption option) {
        return option.c instanceof Liyezhu;
    }
}
