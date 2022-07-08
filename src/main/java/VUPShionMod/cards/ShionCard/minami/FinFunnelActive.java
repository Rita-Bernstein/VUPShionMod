package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerAllFinFunnelPassiveAction;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import VUPShionMod.character.Shion;
import VUPShionMod.finfunnels.FinFunnelManager;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Shion.ReleaseFormMinamiPower;
import VUPShionMod.vfx.Atlas.AbstractAtlasGameEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FinFunnelActive extends AbstractShionMinamiCard {
    public static final String ID = VUPShionMod.makeID(FinFunnelActive.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami01.png");
    private static final int COST = 0;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public FinFunnelActive() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(CardTagsEnum.TRIGGER_FIN_FUNNEL);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new AbstractAtlasGameEffect("Energy 008 Impact Radial", p.hb.cX, p.hb.cY,
                125.0f, 125.0f, 3.0f * Settings.scale, 2,false)));

        if (!FinFunnelManager.getFinFunnelList().isEmpty()) {
            addToBot(new TriggerAllFinFunnelPassiveAction(m));
        }

    }

    public AbstractCard makeCopy() {
        return new FinFunnelActive();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.exhaust = false;
        }
    }
}
