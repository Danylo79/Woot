package ipsis.woot.network;

import io.netty.buffer.ByteBuf;
import ipsis.woot.Woot;
import ipsis.woot.factory.FactoryUIInfo;
import ipsis.woot.factory.FactoryUpgrade;
import ipsis.woot.factory.blocks.ControllerBlock;
import ipsis.woot.factory.blocks.ControllerTileEntity;
import ipsis.woot.oss.NetworkTools;
import ipsis.woot.util.FakeMob;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

/**
 * Server -> Client
 */
public class HeartStaticDataReply {

    public FactoryUIInfo info;

    public HeartStaticDataReply() { }

    public HeartStaticDataReply(FactoryUIInfo factoryUIInfo) {
        this.info = factoryUIInfo;
    }

    public static HeartStaticDataReply fromBytes(ByteBuf buf) {
        FactoryUIInfo factoryUIInfo = new FactoryUIInfo();
        factoryUIInfo.recipeEffort = buf.readInt();
        factoryUIInfo.recipeTicks = buf.readInt();
        factoryUIInfo.recipeCostPerTick = buf.readInt();
        factoryUIInfo.effortStored = buf.readInt();
        factoryUIInfo.mobCount = buf.readInt();

        int numUpgrades = buf.readInt();
        for (int i = 0; i < numUpgrades; i++)
            factoryUIInfo.upgrades.add(FactoryUpgrade.getUpgrade(buf.readInt()));

        int numDrops = buf.readInt();
        for (int i = 0; i < numDrops; i++)
            factoryUIInfo.drops.add(NetworkTools.readItemStack(buf));

        int numMobs = buf.readInt();
        for (int i = 0; i < numMobs; i++) {
            ItemStack controller = NetworkTools.readItemStack(buf);
            factoryUIInfo.mobs.add(controller);
            FactoryUIInfo.Mob mob = new FactoryUIInfo.Mob(controller);
            int numItemIng = buf.readInt();
            for (int j = 0; j < numItemIng; j++)
                mob.itemIngredients.add(NetworkTools.readItemStack(buf));
            int numFluidIng = buf.readInt();
            for (int j = 0; j < numItemIng; j++) {
            }
            factoryUIInfo.mobInfo.add(mob);
        }

        return new HeartStaticDataReply(factoryUIInfo);
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(info.recipeEffort);
        buf.writeInt(info.recipeTicks);
        buf.writeInt(info.recipeCostPerTick);
        buf.writeInt(info.effortStored);
        buf.writeInt(info.mobCount);

        buf.writeInt(info.upgrades.size());
        for (FactoryUpgrade upgrade : info.upgrades)
            buf.writeInt(upgrade.ordinal());

        buf.writeInt(info.drops.size());
        for (ItemStack itemStack : info.drops)
            NetworkTools.writeItemStack(buf, itemStack);

        boolean ingTest = true;
        buf.writeInt(info.mobs.size());
        for (ItemStack itemStack : info.mobs) {
            NetworkTools.writeItemStack(buf, itemStack);
            if (ingTest) {
                buf.writeInt(1); // number of item ingredients
                NetworkTools.writeItemStack(buf, new ItemStack(Items.EMERALD, 4));
                buf.writeInt(0); // number of fluid ingredients
                ingTest = false;
            } else {
                buf.writeInt(0);
                buf.writeInt(0);
            }

        }
    }
}
