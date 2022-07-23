package VUPShionMod.skins.sk.EisluRen;

import VUPShionMod.skins.AbstractSkinCharacter;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class AbstractSkinEisluRen extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("VUPShionMod:EisluRen").NAMES[0];

    public AbstractSkinEisluRen() {
        super(ID);
        if (this.skins.isEmpty()) {
            this.skins.add(new OriEisluRen());
        }
    }


}
