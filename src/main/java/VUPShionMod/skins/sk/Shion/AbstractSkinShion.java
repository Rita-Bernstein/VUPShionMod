package VUPShionMod.skins.sk.Shion;

import VUPShionMod.skins.AbstractSkinCharacter;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class AbstractSkinShion extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("VUPShionMod:Shion").NAMES[0];

    public AbstractSkinShion() {
        super(ID);
        if(this.skins.isEmpty()) {
            skins.add(new OriShion());
            skins.add(new BlueGiantShion());
            skins.add(new AquaShion());
        }
    }


}
