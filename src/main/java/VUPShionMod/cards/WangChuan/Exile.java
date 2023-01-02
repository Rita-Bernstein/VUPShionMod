package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import VUPShionMod.vfx.Common.PortraitWindyPetalEffect;
import VUPShionMod.vfx.Shion.LargPortraitFlashInEffect;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@NoPools
public class Exile extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID(Exile.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/Exile.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 0;

    public Exile() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.exhaust = true;
        this.isEthereal = true;

        vupCardSetBanner(CardRarity.RARE, CardType.ATTACK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new AbstractAtlasGameEffect("Smoke 037 Radial Transition", Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f,
                96.0f, 54.0f, 10.0f * Settings.scale, 2, false)));
        addToBot(new VFXAction(new PortraitWindyPetalEffect("MorsLibraque"), 1.0f));
        addToBot(new InstantKillAction(m));
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }
}
