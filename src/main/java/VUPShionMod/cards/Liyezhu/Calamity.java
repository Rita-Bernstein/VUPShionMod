package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.EnergyPanelPatches;
import VUPShionMod.powers.Liyezhu.Calamity2Power;
import VUPShionMod.powers.Liyezhu.CalamityPower;
import VUPShionMod.powers.Common.LoseHPPower;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BerserkPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

@NoPools
public class Calamity extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(Calamity.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/Calamity.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public Calamity() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.isInnate = true;
        vupCardSetBanner(CardRarity.RARE, CardType.POWER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 5)));
        addToBot(new ApplyPowerAction(p, p, new BerserkPower(p, 2)));
        addToBot(new ApplyPowerAction(p, p, new LoseHPPower(p, 20)));
        addToBot(new ApplyPowerAction(p, p, new CalamityPower(p, 2)));
        addToBot(new ApplyPowerAction(p, p, new Calamity2Power(p)));
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (EnergyPanelPatches.PatchEnergyPanelField.canUseSans.get(AbstractDungeon.overlayMenu.energyPanel)) {
                    EnergyPanelPatches.PatchEnergyPanelField.sans.get(AbstractDungeon.overlayMenu.energyPanel).loseSan(10);
                }
                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(0);
        }
    }
}