package VUPShionMod.cards.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TriggerAllFinFunnelAction;
import VUPShionMod.cards.AbstractMinamiCard;
import VUPShionMod.character.Shion;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.ReleaseFormMinamiPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FinFunnelActive extends AbstractMinamiCard {
    public static final String ID = VUPShionMod.makeID("FinFunnelActive");
    public static final String IMG = VUPShionMod.assetPath("img/cards/minami/minami01.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public FinFunnelActive() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(CardTagsEnum.TRIGGER_FIN_FUNNEL);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player instanceof Shion) {
            addToBot(new TriggerAllFinFunnelAction(m));

            if (p.hasPower(ReleaseFormMinamiPower.POWER_ID)) {
                for (int i = 0; i < p.getPower(ReleaseFormMinamiPower.POWER_ID).amount; i++)
                    addToBot(new TriggerAllFinFunnelAction(m));
            }

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
