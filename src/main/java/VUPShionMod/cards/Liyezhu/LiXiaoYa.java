package VUPShionMod.cards.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.powers.Liyezhu.SwearPower;
import VUPShionMod.stances.JudgeStance;
import VUPShionMod.stances.PrayerStance;
import VUPShionMod.stances.SpiritStance;
import VUPShionMod.vfx.EisluRen.FinalFlashBlastEffect;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.GraveField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class LiXiaoYa extends AbstractLiyezhuCard {
    public static final String ID = VUPShionMod.makeID(LiXiaoYa.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/Liyezhu/LiXiaoYa.png");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 3;

    public LiXiaoYa() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 20;

        this.cardsToPreview = new JudgementOfSins();
        this.cardsToPreview.upgrade();
        this.selfRetain = true;
        this.exhaust = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, p, this.magicNumber));
        addToBot(new ChangeStanceAction(PrayerStance.STANCE_ID));
        if (this.upgraded) {
            addToBot(new MakeTempCardInHandAction(new CelestialIncarnation(), 1));
        } else {
            AbstractCard card = new JudgementOfSins();
            card.upgrade();
            addToBot(new MakeTempCardInHandAction(card, 1));

        }

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeBaseCost(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            this.cardsToPreview = new CelestialIncarnation();
        }
    }
}