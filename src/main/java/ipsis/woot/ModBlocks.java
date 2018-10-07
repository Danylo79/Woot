package ipsis.woot;

import ipsis.woot.blocks.BlockHeart;
import ipsis.woot.blocks.BlockLayout;
import ipsis.woot.blocks.BlockController;
import ipsis.woot.blocks.BlockStructure;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    @GameRegistry.ObjectHolder("woot:layout")
    public static final BlockLayout layoutBlock = null;

    @GameRegistry.ObjectHolder("woot:heart")
    public static final BlockHeart heartBlock = null;

    @GameRegistry.ObjectHolder("woot:controller")
    public static final BlockController controllerBlock = null;

    @GameRegistry.ObjectHolder("woot:bone_structure")
    public static final BlockStructure boneStructureBlock = null;
    @GameRegistry.ObjectHolder("woot:flesh_structure")
    public static final BlockStructure fleshStructureBlock = null;
    @GameRegistry.ObjectHolder("woot:blaze_structure")
    public static final BlockStructure blazeStructureBlock = null;
    @GameRegistry.ObjectHolder("woot:ender_structure")
    public static final BlockStructure enderStructureBlock = null;

}
