package ipsis.woot.util;

import ipsis.woot.reference.Reference;
import ipsis.woot.util.WootMobName;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WootMobNameBuilder {

    public static @Nonnull
    WootMobName create(@Nonnull EntityLiving entityLiving) {

        WootMobName wootMobName = new WootMobName();

        ResourceLocation resourceLocation = EntityList.getKey(entityLiving);
        if (resourceLocation != null)
            wootMobName = new WootMobName(resourceLocation.toString(), "none");

        return wootMobName;
    }

    public static @Nonnull WootMobName create(String name) {

        WootMobName wootMobName = new WootMobName();

        Pattern pattern = Pattern.compile("(\\w*):(\\w*):(.*)");
        Matcher matcher = pattern.matcher(name);

        if (matcher.find() && matcher.groupCount() == 3 && matcher.group(1).equalsIgnoreCase(Reference.MOD_ID))
            wootMobName = new WootMobName(matcher.group(3), matcher.group(2));

        return wootMobName;
    }

    public static @Nonnull WootMobName createFromConfigString(String name) {

        WootMobName wootMobName = create(name);
        if (!wootMobName.isValid()) {
            if (EntityList.isRegistered(new ResourceLocation(name)))
                wootMobName = new WootMobName(name, "none");
        }

        return wootMobName;
    }

    private final static String NBT_WOOT_MOB_NAME_KEY = "wootMobNameKey";
    private final static String NBT_WOOT_MOB_NAME_TAG = "wootMobNameTag";
    public static void writeToNBT(WootMobName wootMobName, NBTTagCompound tagCompound) {

        if (!wootMobName.isValid())
            return;

        if (tagCompound == null)
            tagCompound = new NBTTagCompound();

        tagCompound.setString(NBT_WOOT_MOB_NAME_KEY, wootMobName.getEntityKey());
        tagCompound.setString(NBT_WOOT_MOB_NAME_TAG, wootMobName.getTag());
    }

    public static @Nonnull WootMobName create(NBTTagCompound tagCompound) {

        if (tagCompound == null ||
            !tagCompound.hasKey(NBT_WOOT_MOB_NAME_KEY) ||
            !tagCompound.hasKey(NBT_WOOT_MOB_NAME_TAG))
            return new WootMobName();

        return new WootMobName(tagCompound.getString(NBT_WOOT_MOB_NAME_KEY), tagCompound.getString(NBT_WOOT_MOB_NAME_TAG));
    }
}