package VUPShionMod.relics.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.GainWingShieldChargeAction;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.ui.WingShield;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ShieldHRzy1 extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(ShieldHRzy1.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/ShieldHRzy1.png";
    private static final String OUTLINE_PATH = "img/relics/outline/ShieldHRzy1.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public ShieldHRzy1() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onUnequip() {
        WingShield.getWingShield().reset();
    }

    @Override
    public int onLoseHpLast(int damageAmount) {
        if (AbstractDungeon.player != null && AbstractDungeon.currMapNode != null &&
                (AbstractDungeon.getCurrRoom()).phase != AbstractRoom.RoomPhase.COMBAT) {
            flash();
            return damageAmount / 2;
        }

        return super.onLoseHpLast(damageAmount);
    }


    @Override
    public void atTurnStart() {
        if(GameActionManager.turn >1) {
            flash();
            addToBot(new GainWingShieldChargeAction(1));
        }
    }
}
