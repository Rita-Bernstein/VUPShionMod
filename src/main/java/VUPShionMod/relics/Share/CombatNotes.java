package VUPShionMod.relics.Share;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class CombatNotes extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(CombatNotes.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/CombatNotes.png";
    private static final String OUTLINE_PATH = "img/relics/outline/CombatNotes.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    private boolean isEliteOrBoss = false;

    public CombatNotes() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
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

    }

    @Override
    public void atTurnStartPostDraw() {
        if(!this.isEliteOrBoss){
            flash();
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        this.isEliteOrBoss = false;
    }


    @Override
    public boolean canSpawn() {
        return EnergyPanelPatches.isShionModChar();
    }
}
