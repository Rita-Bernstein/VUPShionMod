package VUPShionMod.relics.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.EisluRen.GainRefundChargeAction;
import VUPShionMod.actions.EisluRen.GainWingShieldChargeAction;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.ui.WingShield;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.AbstractStance;

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
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 5)));
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
    public void onChangeStance(AbstractStance prevStance, AbstractStance newStance) {
        flash();
        addToBot(new GainWingShieldChargeAction(4));
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
