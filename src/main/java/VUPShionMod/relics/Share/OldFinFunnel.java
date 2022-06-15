package VUPShionMod.relics.Share;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.relics.AbstractShionRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import java.util.ArrayList;

public class OldFinFunnel extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(OldFinFunnel.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/OldFinFunnel.png";
    private static final String OUTLINE_PATH = "img/relics/outline/OldFinFunnel.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));


    public OldFinFunnel() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        flash();
        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.relicRng);
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
        addToBot(new VFXAction(new BorderFlashEffect(Color.RED)));
        if (m != null)
            addToBot(new VFXAction(new SmallLaserEffect(m.hb.cX, m.hb.cY, this.hb.cX, this.hb.cY), 0.3F));

        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 4));

        addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, 4,
                DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));

    }


    @Override
    public boolean canSpawn() {
        return EnergyPanelPatches.isShionModChar();
    }
}
