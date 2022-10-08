package VUPShionMod.relics.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.stances.PrayerStance;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HallowedCase extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(HallowedCase.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/HallowedCase.png";
    private static final String OUTLINE_PATH = "img/relics/outline/HallowedCase.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public HallowedCase() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.BOSS, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void atBattleStart() {
        flash();
        addToBot(new ChangeStanceAction(PrayerStance.STANCE_ID));
        addToBot(new MakeTempCardInDiscardAction(new Miracle(),2));
    }

    @Override
    public void onPlayerEndTurn() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PsychicPower(AbstractDungeon.player, 1)));
    }

    public void onEquip() { AbstractDungeon.player.energy.energyMaster++; }

    public void onUnequip() { AbstractDungeon.player.energy.energyMaster--; }


    @Override
    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        player.relics.stream()
                .filter(r -> r instanceof MartyrVessel).findFirst()
                .map(r -> player.relics.indexOf(r))
                .ifPresent(index -> instantObtain(player, index, true));

        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(MartyrVessel.ID);
    }

}
