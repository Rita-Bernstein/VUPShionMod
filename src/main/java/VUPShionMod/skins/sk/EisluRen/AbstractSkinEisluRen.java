package VUPShionMod.skins.sk.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.character.EisluRen;
import VUPShionMod.skins.AbstractSkinCharacter;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

public class AbstractSkinEisluRen extends AbstractSkinCharacter {

    public AbstractSkinEisluRen() {
        super();
        if (this.skins.isEmpty()) {
            this.skins.add(new OriEisluRen(0));
        }
    }

    @Override
    public boolean isCharacter(CharacterOption option) {
        return option.c instanceof EisluRen;
    }
}
