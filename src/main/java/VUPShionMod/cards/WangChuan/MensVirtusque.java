package VUPShionMod.cards.WangChuan;

import VUPShionMod.VUPShionMod;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Wangchuan.MagiamObruorPower;
import VUPShionMod.powers.Wangchuan.MensVirtusquePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;

public class MensVirtusque extends AbstractWCCard {
    public static final String ID = VUPShionMod.makeID("MensVirtusque");
    public static final String IMG = VUPShionMod.assetPath("img/cards/wangchuan/wc40.png");
    private static final CardType TYPE = CardType.POWER;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 2;

    public MensVirtusque() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
        vupCardSetBanner(CardRarity.RARE,CardType.POWER);
        this.tags.add(CardTagsEnum.MagiamObruor_CARD);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MensVirtusquePower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new MagiamObruorPower(p, 3)));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractCard> possibleCards = new ArrayList<AbstractCard>();
                AbstractCard theCard = null;
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c.canUpgrade())
                        possibleCards.add(c);
                }

                if (!possibleCards.isEmpty()) {
                    theCard = possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
                    theCard.upgrade();
                    AbstractDungeon.player.bottledCardUpgradeCheck(theCard);
                }


                if (theCard != null) {
                    AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(theCard.makeStatEquivalentCopy()));
                    addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                }
                isDone = true;
            }
        });
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
//            upgradeMagicNumber(1);

            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
