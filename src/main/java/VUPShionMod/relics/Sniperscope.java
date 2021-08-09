package VUPShionMod.relics;

import VUPShionMod.VUPShionMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class Sniperscope extends CustomRelic {
    public static final String ID = VUPShionMod.makeID("Sniperscope");
    public static final String IMG_PATH = "img/relics/Sniperscope.png";
    private static final String OUTLINE_PATH = "img/relics/outline/Sniperscope.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public Sniperscope() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.SPECIAL, LandingSound.CLINK);
        getUpdatedDescription();
    }

    @Override
    public void atBattleStart() {
        AbstractMonster weakestMonster = null;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) {
                if (weakestMonster == null) {
                    weakestMonster = m;
                    continue;
                }
                if (m.currentHealth < weakestMonster.currentHealth) {
                    weakestMonster = m;
                }
            }
        }

        addToBot(new ApplyPowerAction(weakestMonster, AbstractDungeon.player, new VulnerablePower(weakestMonster, 1, false)));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
