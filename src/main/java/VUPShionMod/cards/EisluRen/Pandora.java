package VUPShionMod.cards.EisluRen;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Common.ApplyPowerToAllEnemyAction;
import VUPShionMod.actions.Common.XActionAction;
import VUPShionMod.actions.EisluRen.LoseWingShieldAction;
import VUPShionMod.actions.Shion.FinFunnelMinionAction;
import VUPShionMod.cards.WangChuan.BombardaMagica;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Shion.PursuitPower;
import VUPShionMod.skins.SkinManager;
import VUPShionMod.ui.WingShield;
import VUPShionMod.vfx.Shion.FinFunnelMinionEffect;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Pandora extends AbstractEisluRenCard {
    public static final String ID = VUPShionMod.makeID(Pandora.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/EisluRen/Pandora.png");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = -1;

    public Pandora() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 8;
        this.secondaryM = this.baseSecondaryM = 4;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!hasTag(CardTagsEnum.NoWingShieldCharge))
        addToBot(new LoseWingShieldAction(this.secondaryM));

        for (int i = 0; i < 8; i++) {
            AbstractDungeon.effectList.add(new FinFunnelMinionEffect(null, SkinManager.getSkinCharacter(0).reskinCount,i, true));
        }


        Consumer<Integer> actionConsumer = effect -> {
            Supplier<AbstractPower> powerToApply = () -> new PursuitPower(null, AbstractDungeon.player, upgraded ? effect + 3 : effect + 2);
            for (int i = 0; i < effect; i++)
                addToTop(new ApplyPowerToAllEnemyAction(powerToApply));
        };
        addToBot(new XActionAction(actionConsumer, this.freeToPlayOnce, this.energyOnUse));
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!hasTag(CardTagsEnum.NoWingShieldCharge))
        if (WingShield.getWingShield().getCount() < this.secondaryM) {
            cantUseMessage = CardCrawlGame.languagePack.getUIString("VUPShionMod:WingShield").TEXT[2];
            return false;
        }

        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeSecondM(-2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
