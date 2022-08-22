package VUPShionMod.relics.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.GainRefundChargeAction;
import VUPShionMod.actions.EisluRen.GainWingShieldChargeAction;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.ui.WingShield;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ShieldHRzy2 extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(ShieldHRzy2.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/ShieldHRzy2.png";
    private static final String OUTLINE_PATH = "img/relics/outline/ShieldHRzy2.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public ShieldHRzy2() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void atBattleStart() {
        WingShield.getWingShield().upgradeMax();
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
    public void onLoseShieldCharge(int amount) {
        flash();
        addToBot(new GainRefundChargeAction(amount));
    }

    @Override
    public void atTurnStart() {
        if (GameActionManager.turn > 1) {
            flash();
            addToBot(new GainWingShieldChargeAction(2));
        }
    }

    @Override
    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        player.relics.stream()
                .filter(r -> r instanceof ShieldHRzy1).findFirst()
                .map(r -> player.relics.indexOf(r))
                .ifPresent(index -> instantObtain(player, index, true));

        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(ShieldHRzy1.ID);
    }

}