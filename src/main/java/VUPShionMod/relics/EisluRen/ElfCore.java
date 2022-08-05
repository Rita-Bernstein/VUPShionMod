package VUPShionMod.relics.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.cards.Codex.LignumConstans;
import VUPShionMod.cards.Codex.LignumNimium;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.ui.WingShield;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class ElfCore extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(ElfCore.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/ElfCore.png";
    private static final String OUTLINE_PATH = "img/relics/outline/ElfCore.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public ElfCore() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }


    @Override
    public void onEquip() {
        for (RewardItem reward : AbstractDungeon.combatRewardScreen.rewards) {
            if (reward.cards != null) {
                for (AbstractCard c : reward.cards) {
                    onPreviewObtainCard(c);
                }
            }
        }
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (c instanceof LignumNimium || c instanceof LignumConstans) {
            c.upgrade();
        }
    }

    @Override
    public void onPreviewObtainCard(AbstractCard c) {
        onObtainCard(c);
    }


}
