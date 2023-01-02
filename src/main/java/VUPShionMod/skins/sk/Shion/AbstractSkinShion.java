package VUPShionMod.skins.sk.Shion;

import VUPShionMod.character.Shion;
import VUPShionMod.skins.AbstractSkinCharacter;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

public class AbstractSkinShion extends AbstractSkinCharacter {

    public AbstractSkinShion() {
        super();
        if (this.skins.isEmpty()) {
            skins.add(new OriShion(0));
            skins.add(new BlueGiantShion(1));
            skins.add(new AquaShion(2));
            skins.add(new MinamiShion(3));
            skins.add(new GodShion(4));
        }
    }

    @Override
    public boolean isCharacter(CharacterOption option) {
        return option.c instanceof Shion;
    }

}
