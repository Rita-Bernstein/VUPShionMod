package VUPShionMod.cards.Codex;

import VUPShionMod.cards.ShionCard.AbstractVUPShionCard;
import VUPShionMod.cards.WangChuan.AbstractWCCard;
import VUPShionMod.interfaces.BranchCard;
import VUPShionMod.patches.CardColorEnum;
import VUPShionMod.patches.CardTagsEnum;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractCodexCard extends AbstractWCCard implements BranchCard {
    public AbstractCodexCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target) {
        this(id, img, cost, type, rarity, target, 0);
    }

    public AbstractCodexCard(String id, String img, int cost, CardType type, CardRarity rarity, CardTarget target, int upgrades) {
        super(id, img, cost, type, rarity, target);
        this.tags.add(CardTagsEnum.Codex_CARD);
        this.color = CardColorEnum.Codex_LIME;
        this.timesUpgraded = upgrades;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (this.timesUpgraded) {
            case 2:
                useEffect3(p, m);
                break;
            case 1:
                if (chosenBranch() == 0)
                    useEffect1(p, m);
                else
                    useEffect2(p, m);
                break;
            default:
                useEffect(p, m);
        }
    }

    @Override
    public boolean canUpgrade() {
        return timesUpgraded <= 1;
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        return new ArrayList<UpgradeBranch>() {{
            add(() -> {
                if (timesUpgraded <= 1)
                upgradeEffect1();
            });
            add(() -> {
                if (timesUpgraded <= 1)
                upgradeEffect2();
            });
        }};
    }

    protected void upgradeEffect1() {
    }

    protected void upgradeEffect2() {
    }

    protected void useEffect(AbstractPlayer p, AbstractMonster m) {
    }

    protected void useEffect1(AbstractPlayer p, AbstractMonster m) {
    }

    protected void useEffect2(AbstractPlayer p, AbstractMonster m) {
    }

    protected void useEffect3(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upgrade() {
        BranchCard.super.upgrade();
        System.out.println("您生了吗++++++++++++++"+timesUpgraded);
        if (timesUpgraded <= 1) {
            this.upgraded = true;
            System.out.println("分支正在升级++++++++++++++"+timesUpgraded);
            if (timesUpgraded < 1) {
                this.name = EXTENDED_DESCRIPTION[chosenBranch()];
                this.rawDescription = EXTENDED_DESCRIPTION[chosenBranch() + 2];
            } else {
                this.name = EXTENDED_DESCRIPTION[4];
                this.rawDescription = UPGRADE_DESCRIPTION;
            }
            initializeTitle();
            initializeDescription();
            timesUpgraded++;

        }

        System.out.println("分支升级完成++++++++++++++"+timesUpgraded);
    }
}