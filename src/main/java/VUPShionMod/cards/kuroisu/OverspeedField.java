package VUPShionMod.cards.kuroisu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.TriggerAllFinFunnelAction;
import VUPShionMod.actions.TriggerDimensionSplitterAction;
import VUPShionMod.cards.AbstractVUPShionCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.powers.BadgeOfTimePower;
import VUPShionMod.powers.DenergizePower;
import VUPShionMod.relics.DimensionSplitterAria;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class OverspeedField extends AbstractVUPShionCard {
    public static final String ID = VUPShionMod.makeID("OverspeedField");
    public static final String IMG = VUPShionMod.assetPath("img/cards/kuroisu/kuroisu14.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardColor COLOR = CardColorEnum.VUP_Shion_LIME;

    private static final int COST = 3;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public OverspeedField() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new TriggerAllFinFunnelAction());
        addToBot(new TriggerDimensionSplitterAction());
        addToBot(new DrawCardAction(p, this.magicNumber));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    c.setCostForTurn(0);
                }
            }
        });
        addToBot(new ApplyPowerAction(p, p, new DenergizePower(p, 2)));
    }

    public AbstractCard makeCopy() {
        return new OverspeedField();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}
