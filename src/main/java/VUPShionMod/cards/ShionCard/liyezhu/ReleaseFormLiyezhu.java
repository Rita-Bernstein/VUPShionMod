package VUPShionMod.cards.ShionCard.liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.GainHyperdimensionalLinksAction;
import VUPShionMod.cards.ShionCard.AbstractShionLiyezhuCard;
import VUPShionMod.character.Shion;
import VUPShionMod.powers.Shion.ReleaseFormLiyezhuBPower;
import VUPShionMod.powers.Shion.ReleaseFormLiyezhuCPower;
import VUPShionMod.powers.Shion.ReleaseFormLiyezhuPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.vfx.Common.AbstractSpineEffect;
import VUPShionMod.vfx.Common.PortraitWindyPetalEffect;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.function.Consumer;

public class ReleaseFormLiyezhu extends AbstractShionLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(ReleaseFormLiyezhu.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/liyezhu/lyz09.png");
    private static final int COST = 3;
    public static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public ReleaseFormLiyezhu() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.secondaryM = this.baseSecondaryM = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainHyperdimensionalLinksAction(this.magicNumber));
        addToBot(new SFXAction("SHION_" + (32 + MathUtils.random(1))));
        addToBot(new VFXAction(new PortraitWindyPetalEffect("Liyezhu"),1.0f));

        Consumer<AnimationState> stateConsumer = state -> {
            state.setAnimation(0, "FZ_Open", false);
            state.addAnimation(0, "FZ_idle", true, 0.1f);
        };

        addToBot(new VFXAction(new AbstractSpineEffect(true,
                "VUPShionMod/img/vfx/Spine/Stance_Lan_TX/Stance_Lan_TX", p.hb.cX, p.hb.y, 0.2f,
                1.0f, -0.1f, stateConsumer)));

        addToBot(new ApplyPowerAction(p, p, new ReleaseFormLiyezhuPower(p, this.magicNumber), 0));
        addToBot(new ApplyPowerAction(p, p, new ReleaseFormLiyezhuCPower(p, this.secondaryM), 0));
        addToBot(new ApplyPowerAction(p, p, new ReleaseFormLiyezhuBPower(p, 1), 0));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSecondM(9);
        }
    }


}
