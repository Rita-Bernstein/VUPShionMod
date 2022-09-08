package VUPShionMod.relics.Liyezhu;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Liyezhu.AddSansAction;
import VUPShionMod.patches.CardTagsEnum;
import VUPShionMod.powers.Liyezhu.PsychicPower;
import VUPShionMod.relics.AbstractShionRelic;
import VUPShionMod.stances.JudgeStance;
import VUPShionMod.stances.PrayerStance;
import VUPShionMod.stances.SpiritStance;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Hymn extends AbstractShionRelic {
    public static final String ID = VUPShionMod.makeID(Hymn.class.getSimpleName());
    public static final String IMG_PATH = "img/relics/Hymn.png";
    private static final String OUTLINE_PATH = "img/relics/outline/Hymn.png";
    private static final Texture IMG = new Texture(VUPShionMod.assetPath(IMG_PATH));
    private static final Texture OUTLINE_IMG = new Texture(VUPShionMod.assetPath(OUTLINE_PATH));

    public Hymn() {
        super(ID, IMG, OUTLINE_IMG, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (targetCard.hasTag(CardTagsEnum.Prayer_CARD)) {
            if (AbstractDungeon.player.stance.ID.equals(PrayerStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(SpiritStance.STANCE_ID)) {

                addToBot(new AddSansAction(1));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PsychicPower(AbstractDungeon.player, 1)));
                addToBot(new MakeTempCardInHandAction(new Miracle(), 1));
            }

            if (AbstractDungeon.player.stance.ID.equals(JudgeStance.STANCE_ID) || AbstractDungeon.player.stance.ID.equals(SpiritStance.STANCE_ID)) {
                if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                        addToBot(new DamageAction(monster, new DamageInfo(AbstractDungeon.player, 3 + (int) (monster.maxHealth * 0.05f), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE, true));
                    }
                }
            }
        }
    }

}
