package VUPShionMod.skins.sk.WangChuan;

import VUPShionMod.skins.AbstractSkinCharacter;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class AbstractSkinWangChuan extends AbstractSkinCharacter {
    public static final String ID = CardCrawlGame.languagePack.getCharacterString("VUPShionMod:WangChuan").NAMES[0];

    public AbstractSkinWangChuan() {
        super(ID);
        if(this.skins.isEmpty()) {
            skins.add(new OriWangChuan());
            skins.add(new PurityWangChuan());
            skins.add(new AquaWangChuan());
            skins.add(new ChinaWangChuan());
        }

    }
}
