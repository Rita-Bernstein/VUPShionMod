package VUPShionMod.cards.ShionCard.minami;

import VUPShionMod.VUPShionMod;
import VUPShionMod.actions.Shion.TriggerDimensionSplitterAction;
import VUPShionMod.cards.ShionCard.AbstractShionMinamiCard;
import VUPShionMod.patches.CharacterSelectScreenPatches;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class LockIndication extends AbstractShionMinamiCard  {
    public static final String ID = VUPShionMod.makeID(LockIndication.class.getSimpleName());
    public static final String IMG = VUPShionMod.assetPath("img/cards/ShionCard/minami/minami03.png");
    private static final int COST = 1;
    public static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public LockIndication() {
        super(ID, IMG, COST, TYPE, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = MathUtils.random(1);
        switch (count) {
            case 0:
                addToBot(new SFXAction("SHION_10"));
                break;
            case 1:
                addToBot(new SFXAction("SHION_11"));
                break;
        }
        addToBot(new TriggerDimensionSplitterAction());
    }

    public AbstractCard makeCopy() {
        return new LockIndication();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) {
        return CharacterSelectScreenPatches.skinManager.skinCharacters.get(0).reskinCount == 0;
    }
}
