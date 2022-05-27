package VUPShionMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public abstract class AbstractShionRelic extends CustomRelic {
    public boolean upgraded = false;

    public AbstractShionRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) {
        super(id, texture, outline, tier, sfx);
    }

    public int onLoseSan(int amount) {
        return amount;
    }

    public void upgrade() {
    }

    public void onInflictDamage(DamageInfo info, int damageAmount, AbstractCreature target) {
    }
}
