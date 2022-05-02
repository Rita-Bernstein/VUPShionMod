package VUPShionMod.skins.sk.Liyezhu;

import VUPShionMod.skins.AbstractSkinCharacter;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class AbstractSkinLiyezhu extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("VUPShionMod:Liyezhu").NAMES[0];

    public AbstractSkinLiyezhu() {
        super(ID);
        if(this.skins.isEmpty()) {
            this.skins.add(new OriLiyezhu());
        }
    }


}
