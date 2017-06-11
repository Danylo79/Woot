package ipsis.woot.tileentity.ng.spawning;

import ipsis.woot.manager.EnumEnchantKey;
import ipsis.woot.oss.LogHelper;
import ipsis.woot.tileentity.ng.WootMob;
import ipsis.woot.tileentity.ng.WootMobName;
import ipsis.woot.util.FakePlayerPool;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class EntitySpawner {

    private List<AbstractMobEntity> mobEntityList = new ArrayList<>();

    public EntitySpawner() {

        mobEntityList.add(new MobEntityMagmaCube());
        mobEntityList.add(new MobEntitySlime());
        mobEntityList.add(new MobEntityChargedCreeper());
    }

    private void applyCustomConfig(Entity entity, WootMobName wootMobName, World world) {

        for (AbstractMobEntity abstractMobEntity : mobEntityList)
            abstractMobEntity.runSetup(entity, wootMobName, world);
    }

    private @Nullable Entity createEntity(WootMobName wootMobName, World world) {

        ResourceLocation resourceLocation = wootMobName.getResourceLocation();
        Entity entity = EntityList.createEntityByIDFromName(resourceLocation, world);
        if (entity != null)
            applyCustomConfig(entity, wootMobName, world);

        return entity;
    }

    private @Nullable Entity spawnEntity(WootMobName wootMobName, World world, BlockPos pos) {

        Entity entity = createEntity(wootMobName, world);
        if (entity != null) {

            /**
             * Random loot drop needs a non-zero recentlyHit value
             */
            ((EntityLivingBase) entity).recentlyHit = 100;

            entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
        }

        return entity;
    }

    public void spawn(WootMobName wootMobName, EnumEnchantKey key, World world, BlockPos pos) {

        Entity entity = spawnEntity(wootMobName, world, pos);
        if (entity == null)
            return;

        FakePlayer fakePlayer = FakePlayerPool.getFakePlayer((WorldServer)world, key);
        if (fakePlayer == null)
            return;

        /**
         * BUG0022 - Need to set the attackingPlayer or the 1.9 loot tables will not
         * give us all the drops, as some are conditional on killed_by_player
         */
        ((EntityLivingBase)entity).attackingPlayer = fakePlayer;
        ((EntityLivingBase) entity).onDeath(DamageSource.causePlayerDamage(fakePlayer));
    }
}