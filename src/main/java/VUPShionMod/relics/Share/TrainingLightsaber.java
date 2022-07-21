package VUPShionMod.relics.Share;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TrainingLightsaber extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(TrainingLightsaber.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/TrainingLightsaber.png";
    private static final String OUTLINE_PATH = "img/relics/outline/TrainingLightsaber.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    private boolean isEliteOrBoss = false;

    public TrainingLightsaber() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.COMMON, LandingSound.CLINK);
        this.counter = 1;
        setDescriptionAfterLoading();
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], "" + (-3));
    }


    private void setDescriptionAfterLoading() {
        this.description = String.format(DESCRIPTIONS[0], this.counter - 4 > 0 ? "+" + (this.counter - 4) : "" + (this.counter - 4));
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }


    @Override
    public void atPreBattle() {
        super.atPreBattle();

        this.isEliteOrBoss = (AbstractDungeon.getCurrRoom()).eliteTrigger;
        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters)
            if (m.type == AbstractMonster.EnemyType.BOSS) {
                isEliteOrBoss = true;
                break;
            }

        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new StrengthPower(AbstractDungeon.player, this.counter - 4)));

    }

    @Override
    public void onVictory() {
        super.onVictory();

        if (this.isEliteOrBoss) {
            this.counter++;
            setDescriptionAfterLoading();
        }


        this.isEliteOrBoss = false;
    }


    @Override
    public boolean canSpawn() {
        return EnergyPanelPatches.isShionModChar();
    }
}
